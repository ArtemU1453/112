package by.mchs.e112.incident.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import by.mchs.e112.incident.exception.IllegalStatusTransitionException;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class IncidentTest {

    private Incident newIncident() {
        return new Incident(UUID.randomUUID(), IncidentType.FIRE, IncidentPriority.HIGH,
            "Горит квартира", "Минск, пр. Независимости 10", 53.9, 27.56,
            "+375291112233", "Иван", 2, null, "dispatcher1");
    }

    @Test
    void createdIncidentHasReceivedStatusAndHistory() {
        Incident incident = newIncident();
        assertThat(incident.getStatus()).isEqualTo(IncidentStatus.RECEIVED);
        assertThat(incident.getHistory()).hasSize(1);
        assertThat(incident.getHistory().get(0).getAction()).isEqualTo("CREATED");
    }

    @Test
    void classifyMovesFromReceivedToClassified() {
        Incident incident = newIncident();
        incident.classify(IncidentType.MEDICAL, IncidentPriority.CRITICAL, "dispatcher1");
        assertThat(incident.getStatus()).isEqualTo(IncidentStatus.CLASSIFIED);
        assertThat(incident.getType()).isEqualTo(IncidentType.MEDICAL);
        assertThat(incident.getPriority()).isEqualTo(IncidentPriority.CRITICAL);
    }

    @Test
    void illegalTransitionThrows() {
        Incident incident = newIncident();
        assertThatThrownBy(() -> incident.changeStatus(IncidentStatus.RESOLVED, "d1", "x"))
            .isInstanceOf(IllegalStatusTransitionException.class);
    }

    @Test
    void closeSetsReasonAndTerminalStatus() {
        Incident incident = newIncident();
        incident.changeStatus(IncidentStatus.DISPATCHED, "d1", "наряд");
        incident.changeStatus(IncidentStatus.IN_PROGRESS, "d1", "работа");
        incident.changeStatus(IncidentStatus.RESOLVED, "d1", "ликвидировано");
        incident.close("senior1", "Пожар потушен");
        assertThat(incident.getStatus()).isEqualTo(IncidentStatus.CLOSED);
        assertThat(incident.getClosedReason()).isEqualTo("Пожар потушен");
    }

    @Test
    void newIncidentHasNoNumberUntilAssigned() {
        Incident incident = newIncident();
        assertThat(incident.getNumber()).isNull();
    }
}
