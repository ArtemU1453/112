package by.mchs.e112.platform.abstractions.ai;

import java.util.Set;

/**
 * Порт ИИ-провайдера (AI Integration Framework, Stage 4). Все интеграции — через адаптеры;
 * привязка к конкретной модели запрещена (AI Provider Abstraction Standard, TAP).
 * Поддерживаемые возможности объявляются через {@link AiCapability}.
 */
public interface AiProvider {

    Set<AiCapability> capabilities();

    default boolean supports(AiCapability capability) {
        return capabilities().contains(capability);
    }

    AiResponse execute(AiCapability capability, AiRequest request);
}
