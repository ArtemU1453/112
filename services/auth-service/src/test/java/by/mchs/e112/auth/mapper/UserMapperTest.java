package by.mchs.e112.auth.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import by.mchs.e112.auth.domain.UserProfile;
import by.mchs.e112.auth.domain.UserStatus;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class UserMapperTest {

    private final UserMapper mapper = new UserMapper();

    @Test
    void toResponseMapsAllFields() {
        UUID id = UUID.randomUUID();
        UserProfile profile = new UserProfile(id, "kc-1", "dispatcher1", "Алеся", "Ковалёва",
            "d1@112.by", "+375291234567", "T-100", "ЦОУ Минск");

        var response = mapper.toResponse(profile, Set.of("ROLE_DISPATCHER"));

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.keycloakId()).isEqualTo("kc-1");
        assertThat(response.username()).isEqualTo("dispatcher1");
        assertThat(response.firstName()).isEqualTo("Алеся");
        assertThat(response.lastName()).isEqualTo("Ковалёва");
        assertThat(response.email()).isEqualTo("d1@112.by");
        assertThat(response.phone()).isEqualTo("+375291234567");
        assertThat(response.employeeNumber()).isEqualTo("T-100");
        assertThat(response.department()).isEqualTo("ЦОУ Минск");
        assertThat(response.status()).isEqualTo(UserStatus.ACTIVE.name());
        assertThat(response.roles()).containsExactly("ROLE_DISPATCHER");
        assertThat(response.createdAt()).isNotNull();
    }

    @Test
    void blockAndActivateToggleStatus() {
        UserProfile profile = new UserProfile(UUID.randomUUID(), "kc-2", "u2", "И", "Ф",
            "u2@112.by", null, null, null);

        profile.deactivate();
        assertThat(profile.getStatus()).isEqualTo(UserStatus.BLOCKED);

        profile.activate();
        assertThat(profile.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}
