package by.mchs.e112.incident.domain;

import java.util.Map;
import java.util.Set;

/**
 * Статусная модель карточки происшествия с допустимыми переходами.
 */
public enum IncidentStatus {
    RECEIVED,      // Принят вызов
    CLASSIFIED,    // Классифицировано
    DISPATCHED,    // Наряд назначен
    IN_PROGRESS,   // Работы на месте
    RESOLVED,      // Ликвидировано
    CLOSED,        // Карточка закрыта
    CANCELLED;     // Отменено (ложный вызов, дубликат)

    private static final Map<IncidentStatus, Set<IncidentStatus>> ALLOWED = Map.of(
        RECEIVED, Set.of(CLASSIFIED, DISPATCHED, CANCELLED),
        CLASSIFIED, Set.of(DISPATCHED, CANCELLED),
        DISPATCHED, Set.of(IN_PROGRESS, CANCELLED),
        IN_PROGRESS, Set.of(RESOLVED, CANCELLED),
        RESOLVED, Set.of(CLOSED, IN_PROGRESS),
        CLOSED, Set.of(),
        CANCELLED, Set.of()
    );

    public boolean canTransitionTo(IncidentStatus target) {
        return ALLOWED.getOrDefault(this, Set.of()).contains(target);
    }

    public boolean isTerminal() {
        return ALLOWED.getOrDefault(this, Set.of()).isEmpty();
    }
}
