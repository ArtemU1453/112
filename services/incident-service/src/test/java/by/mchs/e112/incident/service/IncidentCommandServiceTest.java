package by.mchs.e112.incident.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.mchs.e112.incident.domain.Incident;
import by.mchs.e112.incident.domain.IncidentPriority;
import by.mchs.e112.incident.domain.IncidentType;
import by.mchs.e112.incident.dto.IncidentCreateRequest;
import by.mchs.e112.incident.dto.IncidentResponse;
import by.mchs.e112.incident.kafka.IncidentEventProducer;
import by.mchs.e112.incident.mapper.IncidentMapper;
import by.mchs.e112.incident.repository.IncidentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IncidentCommandServiceTest {

    @Mock
    private IncidentRepository repository;
    @Mock
    private IncidentEventProducer eventProducer;

    @org.mockito.Spy
    private IncidentMapper mapper = new IncidentMapper();

    @InjectMocks
    private IncidentCommandService service;

    @Test
    void createPersistsPublishesEventAndReturnsResponse() {
        IncidentCreateRequest request = new IncidentCreateRequest(IncidentType.FIRE,
            IncidentPriority.CRITICAL, "Пожар в здании", "Минск, ул. Ленина 1",
            53.9, 27.56, "+375291234567", "Пётр", 3, null);
        when(repository.saveAndFlush(any(Incident.class))).thenAnswer(inv -> inv.getArgument(0));

        IncidentResponse response = service.create(request, "dispatcher1");

        assertThat(response.type()).isEqualTo("FIRE");
        assertThat(response.priority()).isEqualTo("CRITICAL");
        assertThat(response.casualtiesCount()).isEqualTo(3);
        assertThat(response.createdBy()).isEqualTo("dispatcher1");
        verify(eventProducer).publishCreated(any(Incident.class), anyString());
    }
}
