package by.mchs.e112.dispatch.domain;

import java.util.Map;
import java.util.Set;

/**
 * Оперативный статус подразделения с моделью допустимых переходов.
 */
public enum UnitStatus {
    AVAILABLE,     // Свободно, на дежурстве
    DISPATCHED,    // Направлено на вызов
    EN_ROUTE,      // В пути
    ON_SCENE,      // На месте
    RETURNING,     // Возвращается
    OUT_OF_SERVICE;// Не на ходу / ТО

    private static final Map<UnitStatus, Set<UnitStatus>> ALLOWED = Map.of(
        AVAILABLE, Set.of(DISPATCHED, OUT_OF_SERVICE),
        DISPATCHED, Set.of(EN_ROUTE, AVAILABLE, OUT_OF_SERVICE),
        EN_ROUTE, Set.of(ON_SCENE, RETURNING, OUT_OF_SERVICE),
        ON_SCENE, Set.of(RETURNING, OUT_OF_SERVICE),
        RETURNING, Set.of(AVAILABLE, OUT_OF_SERVICE),
        OUT_OF_SERVICE, Set.of(AVAILABLE)
    );

    public boolean canTransitionTo(UnitStatus target) {
        return ALLOWED.getOrDefault(this, Set.of()).contains(target);
    }

    public boolean isEngaged() {
        return this == DISPATCHED || this == EN_ROUTE || this == ON_SCENE;
    }
}
