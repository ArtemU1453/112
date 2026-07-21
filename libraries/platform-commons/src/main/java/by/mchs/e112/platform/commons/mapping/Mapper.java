package by.mchs.e112.platform.commons.mapping;

import java.util.List;

/**
 * Универсальный контракт отображения между типами (Common Libraries: Mapping).
 * Реализации не содержат бизнес-логики — только структурное преобразование.
 *
 * @param <S> исходный тип
 * @param <T> целевой тип
 */
public interface Mapper<S, T> {

    T map(S source);

    default List<T> mapAll(List<S> sources) {
        return sources == null ? List.of() : sources.stream().map(this::map).toList();
    }
}
