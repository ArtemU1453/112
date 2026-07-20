package by.mchs.e112.auth.mapper;

import by.mchs.e112.auth.domain.UserProfile;
import by.mchs.e112.auth.dto.UserResponse;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(UserProfile profile, Set<String> roles) {
        return new UserResponse(
            profile.getId(),
            profile.getKeycloakId(),
            profile.getUsername(),
            profile.getFirstName(),
            profile.getLastName(),
            profile.getEmail(),
            profile.getPhone(),
            profile.getEmployeeNumber(),
            profile.getDepartment(),
            profile.getStatus().name(),
            roles,
            profile.getCreatedAt(),
            profile.getUpdatedAt()
        );
    }
}
