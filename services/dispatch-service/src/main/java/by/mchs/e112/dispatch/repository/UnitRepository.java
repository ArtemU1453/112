package by.mchs.e112.dispatch.repository;

import by.mchs.e112.dispatch.domain.Unit;
import by.mchs.e112.dispatch.domain.UnitStatus;
import by.mchs.e112.dispatch.domain.UnitType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, UUID> {

    Optional<Unit> findByCallSign(String callSign);

    boolean existsByCallSign(String callSign);

    List<Unit> findByStatus(UnitStatus status);

    List<Unit> findByTypeAndStatus(UnitType type, UnitStatus status);
}
