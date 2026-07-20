package by.mchs.e112.telephony.domain;

import java.util.Map;
import java.util.Set;

public enum CallStatus {
    RINGING,      // Поступает
    ACTIVE,       // На линии
    ON_HOLD,      // На удержании
    COMPLETED,    // Завершён
    MISSED,       // Пропущен
    TRANSCRIBED,  // Записан и расшифрован ИИ
    ANALYZED;     // Проанализирован ИИ

    private static final Map<CallStatus, Set<CallStatus>> ALLOWED = Map.of(
        RINGING, Set.of(ACTIVE, MISSED),
        ACTIVE, Set.of(ON_HOLD, COMPLETED),
        ON_HOLD, Set.of(ACTIVE, COMPLETED),
        COMPLETED, Set.of(TRANSCRIBED),
        TRANSCRIBED, Set.of(ANALYZED),
        MISSED, Set.of(),
        ANALYZED, Set.of()
    );

    public boolean canTransitionTo(CallStatus target) {
        return ALLOWED.getOrDefault(this, Set.of()).contains(target);
    }
}
