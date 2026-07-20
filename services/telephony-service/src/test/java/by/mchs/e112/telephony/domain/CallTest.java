package by.mchs.e112.telephony.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import by.mchs.e112.telephony.exception.IllegalCallStatusTransitionException;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CallTest {

    private Call newCall() {
        return new Call(UUID.randomUUID(), "+375291234567", CallDirection.INBOUND);
    }

    @Test
    void newCallIsRinging() {
        assertThat(newCall().getStatus()).isEqualTo(CallStatus.RINGING);
    }

    @Test
    void answerCompleteFlowComputesDuration() {
        Call call = newCall();
        call.answer("dispatcher1");
        assertThat(call.getStatus()).isEqualTo(CallStatus.ACTIVE);
        assertThat(call.getOperator()).isEqualTo("dispatcher1");
        call.complete("http://records/1.wav");
        assertThat(call.getStatus()).isEqualTo(CallStatus.COMPLETED);
        assertThat(call.getDurationSeconds()).isNotNull();
        assertThat(call.getRecordingUrl()).isEqualTo("http://records/1.wav");
    }

    @Test
    void transcribeThenAnalyze() {
        Call call = newCall();
        call.answer("d1");
        call.complete(null);
        call.attachTranscript("Пожар на Немиге");
        assertThat(call.getStatus()).isEqualTo(CallStatus.TRANSCRIBED);
        UUID incident = UUID.randomUUID();
        call.markAnalyzed(incident);
        assertThat(call.getStatus()).isEqualTo(CallStatus.ANALYZED);
        assertThat(call.getIncidentId()).isEqualTo(incident);
    }

    @Test
    void illegalTransitionThrows() {
        Call call = newCall();
        assertThatThrownBy(() -> call.attachTranscript("x"))
            .isInstanceOf(IllegalCallStatusTransitionException.class);
    }
}
