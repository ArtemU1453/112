package by.mchs.e112.dispatch.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import by.mchs.e112.dispatch.exception.IllegalUnitStatusTransitionException;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class UnitTest {

    private Unit newUnit() {
        return new Unit(UUID.randomUUID(), "АЦ-1", UnitType.FIRE_TRUCK, UUID.randomUUID(),
            "ПАСЧ-1 Минск", 6, 53.9, 27.56);
    }

    @Test
    void newUnitIsAvailableAtBase() {
        Unit unit = newUnit();
        assertThat(unit.getStatus()).isEqualTo(UnitStatus.AVAILABLE);
        assertThat(unit.isAvailable()).isTrue();
        assertThat(unit.getCurrentLatitude()).isEqualTo(53.9);
    }

    @Test
    void dispatchSetsIncidentAndStatus() {
        Unit unit = newUnit();
        UUID incident = UUID.randomUUID();
        unit.dispatchTo(incident);
        assertThat(unit.getStatus()).isEqualTo(UnitStatus.DISPATCHED);
        assertThat(unit.getActiveIncidentId()).isEqualTo(incident);
    }

    @Test
    void illegalTransitionThrows() {
        Unit unit = newUnit();
        assertThatThrownBy(() -> unit.changeStatus(UnitStatus.ON_SCENE))
            .isInstanceOf(IllegalUnitStatusTransitionException.class);
    }

    @Test
    void returningToAvailableClearsIncident() {
        Unit unit = newUnit();
        unit.dispatchTo(UUID.randomUUID());
        unit.changeStatus(UnitStatus.EN_ROUTE);
        unit.changeStatus(UnitStatus.ON_SCENE);
        unit.changeStatus(UnitStatus.RETURNING);
        unit.changeStatus(UnitStatus.AVAILABLE);
        assertThat(unit.getActiveIncidentId()).isNull();
    }
}
