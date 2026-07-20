package by.mchs.e112.auth.service;

import by.mchs.e112.auth.domain.UserProfile;
import by.mchs.e112.auth.dto.RoleAssignRequest;
import by.mchs.e112.auth.dto.UserCreateRequest;
import by.mchs.e112.auth.dto.UserResponse;
import by.mchs.e112.auth.dto.UserUpdateRequest;
import by.mchs.e112.auth.exception.ConflictException;
import by.mchs.e112.auth.exception.NotFoundException;
import by.mchs.e112.auth.kafka.AuditEventPublisher;
import by.mchs.e112.auth.mapper.UserMapper;
import by.mchs.e112.auth.repository.UserProfileRepository;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KeycloakUserService implements UserService {

    private final Keycloak keycloak;
    private final UserProfileRepository repository;
    private final UserMapper mapper;
    private final AuditEventPublisher auditPublisher;
    private final String realm;

    public KeycloakUserService(Keycloak keycloak,
                               UserProfileRepository repository,
                               UserMapper mapper,
                               AuditEventPublisher auditPublisher,
                               @Value("${app.keycloak.realm}") String realm) {
        this.keycloak = keycloak;
        this.repository = repository;
        this.mapper = mapper;
        this.auditPublisher = auditPublisher;
        this.realm = realm;
    }

    @Override
    @Transactional
    public UserResponse create(UserCreateRequest request, String actor) {
        if (repository.existsByUsername(request.username())) {
            throw new ConflictException("Пользователь с логином '%s' уже существует".formatted(request.username()));
        }
        RealmResource realmResource = keycloak.realm(realm);

        UserRepresentation representation = new UserRepresentation();
        representation.setUsername(request.username());
        representation.setFirstName(request.firstName());
        representation.setLastName(request.lastName());
        representation.setEmail(request.email());
        representation.setEnabled(true);
        representation.setEmailVerified(true);

        String keycloakId;
        try (Response response = realmResource.users().create(representation)) {
            if (response.getStatus() == 409) {
                throw new ConflictException("Пользователь '%s' уже существует в Keycloak".formatted(request.username()));
            }
            if (response.getStatus() != 201) {
                throw new IllegalStateException("Keycloak вернул статус " + response.getStatus());
            }
            String location = response.getLocation().getPath();
            keycloakId = location.substring(location.lastIndexOf('/') + 1);
        }

        UserResource userResource = realmResource.users().get(keycloakId);
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.password());
        credential.setTemporary(false);
        userResource.resetPassword(credential);
        applyRoles(realmResource, userResource, request.roles());

        UserProfile profile = new UserProfile(UUID.randomUUID(), keycloakId, request.username(),
            request.firstName(), request.lastName(), request.email(), request.phone(),
            request.employeeNumber(), request.department());
        repository.save(profile);

        auditPublisher.publish("USER_CREATED", profile.getId().toString(), actor,
            Map.of("username", request.username(), "roles", String.join(",", request.roles())));
        return mapper.toResponse(profile, request.roles());
    }

    @Override
    @Transactional
    public UserResponse update(UUID id, UserUpdateRequest request, String actor) {
        UserProfile profile = findProfile(id);
        profile.update(request.firstName(), request.lastName(), request.email(),
            request.phone(), request.employeeNumber(), request.department());

        UserResource userResource = keycloak.realm(realm).users().get(profile.getKeycloakId());
        UserRepresentation representation = userResource.toRepresentation();
        representation.setFirstName(request.firstName());
        representation.setLastName(request.lastName());
        representation.setEmail(request.email());
        userResource.update(representation);

        auditPublisher.publish("USER_UPDATED", id.toString(), actor, Map.of("username", profile.getUsername()));
        return mapper.toResponse(profile, currentRoles(profile.getKeycloakId()));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getById(UUID id) {
        UserProfile profile = findProfile(id);
        return mapper.toResponse(profile, currentRoles(profile.getKeycloakId()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAll() {
        return repository.findAll().stream()
            .map(profile -> mapper.toResponse(profile, currentRoles(profile.getKeycloakId())))
            .toList();
    }

    @Override
    @Transactional
    public void assignRoles(UUID id, RoleAssignRequest request, String actor) {
        UserProfile profile = findProfile(id);
        RealmResource realmResource = keycloak.realm(realm);
        UserResource userResource = realmResource.users().get(profile.getKeycloakId());
        List<RoleRepresentation> existing = userResource.roles().realmLevel().listAll();
        userResource.roles().realmLevel().remove(existing);
        applyRoles(realmResource, userResource, request.roles());
        auditPublisher.publish("USER_ROLES_CHANGED", id.toString(), actor,
            Map.of("roles", String.join(",", request.roles())));
    }

    @Override
    @Transactional
    public void block(UUID id, String actor) {
        UserProfile profile = findProfile(id);
        profile.deactivate();
        setEnabled(profile.getKeycloakId(), false);
        auditPublisher.publish("USER_BLOCKED", id.toString(), actor, Map.of("username", profile.getUsername()));
    }

    @Override
    @Transactional
    public void activate(UUID id, String actor) {
        UserProfile profile = findProfile(id);
        profile.activate();
        setEnabled(profile.getKeycloakId(), true);
        auditPublisher.publish("USER_ACTIVATED", id.toString(), actor, Map.of("username", profile.getUsername()));
    }

    private void setEnabled(String keycloakId, boolean enabled) {
        UserResource userResource = keycloak.realm(realm).users().get(keycloakId);
        UserRepresentation representation = userResource.toRepresentation();
        representation.setEnabled(enabled);
        userResource.update(representation);
    }

    private void applyRoles(RealmResource realmResource, UserResource userResource, Set<String> roles) {
        List<RoleRepresentation> representations = roles.stream()
            .map(role -> realmResource.roles().get(role).toRepresentation())
            .toList();
        userResource.roles().realmLevel().add(representations);
    }

    private Set<String> currentRoles(String keycloakId) {
        return keycloak.realm(realm).users().get(keycloakId)
            .roles().realmLevel().listAll().stream()
            .map(RoleRepresentation::getName)
            .filter(name -> name.startsWith("ROLE_"))
            .collect(Collectors.toSet());
    }

    private UserProfile findProfile(UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Пользователь %s не найден".formatted(id)));
    }
}
