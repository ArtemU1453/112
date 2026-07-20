package by.mchs.e112.incident.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class IncidentStatusTest {

    @Test
    void allowsValidTransitions() {
        assertThat(IncidentStatus.RECEIVED.canTransitionTo(IncidentStatus.CLASSIFIED)).isTrue();
        assertThat(IncidentStatus.CLASSIFIED.canTransitionTo(IncidentStatus.DISPATCHED)).isTrue();
        assertThat(IncidentStatus.DISPATCHED.canTransitionTo(IncidentStatus.IN_PROGRESS)).isTrue();
        assertThat(IncidentStatus.IN_PROGRESS.canTransitionTo(IncidentStatus.RESOLVED)).isTrue();
        assertThat(IncidentStatus.RESOLVED.canTransitionTo(IncidentStatus.CLOSED)).isTrue();
    }

    @Test
    void rejectsInvalidTransitions() {
        assertThat(IncidentStatus.RECEIVED.canTransitionTo(IncidentStatus.RESOLVED)).isFalse();
        assertThat(IncidentStatus.CLOSED.canTransitionTo(IncidentStatus.IN_PROGRESS)).isFalse();
        assertThat(IncidentStatus.CANCELLED.canTransitionTo(IncidentStatus.RECEIVED)).isFalse();
    }

    @Test
    void terminalStatesAreTerminal() {
        assertThat(IncidentStatus.CLOSED.isTerminal()).isTrue();
        assertThat(IncidentStatus.CANCELLED.isTerminal()).isTrue();
        assertThat(IncidentStatus.RECEIVED.isTerminal()).isFalse();
    }
}
