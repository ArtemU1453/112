package by.mchs.e112.auth.repository;

import by.mchs.e112.auth.domain.UserProfile;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {

    Optional<UserProfile> findByUsername(String username);

    Optional<UserProfile> findByKeycloakId(String keycloakId);

    boolean existsByUsername(String username);
}
