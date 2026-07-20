package by.mchs.e112.dispatch.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import by.mchs.e112.dispatch.domain.Unit;
import by.mchs.e112.dispatch.domain.UnitStatus;
import by.mchs.e112.dispatch.domain.UnitType;
import by.mchs.e112.dispatch.dto.AssignmentResponse;
import by.mchs.e112.dispatch.dto.AutoDispatchRequest;
import by.mchs.e112.dispatch.exception.NoAvailableUnitException;
import by.mchs.e112.dispatch.kafka.DispatchEventProducer;
import by.mchs.e112.dispatch.mapper.DispatchMapper;
import by.mchs.e112.dispatch.repository.AssignmentRepository;
import by.mchs.e112.dispatch.repository.UnitRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DispatchServiceTest {

    @Mock UnitRepository unitRepository;
    @Mock AssignmentRepository assignmentRepository;
    @Mock DispatchEventProducer eventProducer;

    private DispatchService service() {
        return new DispatchService(unitRepository, assignmentRepository, new DispatchMapper(), eventProducer);
    }

    @Test
    void autoDispatchPicksNearestAvailableUnit() {
        UUID incident = UUID.randomUUID();
        Unit near = new Unit(UUID.randomUUID(), "АЦ-1", UnitType.FIRE_TRUCK, UUID.randomUUID(),
            "ПАСЧ-1", 6, 53.90, 27.56);
        Unit far = new Unit(UUID.randomUUID(), "АЦ-2", UnitType.FIRE_TRUCK, UUID.randomUUID(),
            "ПАСЧ-2", 6, 52.10, 23.70);
        when(unitRepository.findByTypeAndStatus(UnitType.FIRE_TRUCK, UnitStatus.AVAILABLE))
            .thenReturn(List.of(far, near));
        when(assignmentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        AssignmentResponse response = service().autoDispatch(
            new AutoDispatchRequest(incident, UnitType.FIRE_TRUCK, 53.91, 27.57), "dispatcher1");

        assertThat(response.unitCallSign()).isEqualTo("АЦ-1");
        assertThat(response.distanceKm()).isLessThan(5.0);
    }

    @Test
    void autoDispatchThrowsWhenNoUnits() {
        when(unitRepository.findByTypeAndStatus(UnitType.AMBULANCE, UnitStatus.AVAILABLE))
            .thenReturn(List.of());
        assertThatThrownBy(() -> service().autoDispatch(
            new AutoDispatchRequest(UUID.randomUUID(), UnitType.AMBULANCE, 53.9, 27.5), "d1"))
            .isInstanceOf(NoAvailableUnitException.class);
    }
}
