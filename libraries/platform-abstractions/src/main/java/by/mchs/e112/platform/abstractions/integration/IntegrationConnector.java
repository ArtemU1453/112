package by.mchs.e112.platform.abstractions.integration;

/**
 * Универсальный соединитель интеграции (Integration Platform, Stage 4). Единый контракт поверх
 * REST/gRPC/Message Bus/файлового/пакетного обмена; конкретный транспорт скрыт за реализацией
 * (Integration Governance Standard, Canonical Data Model Standard).
 */
public interface IntegrationConnector {

    ConnectorType type();

    IntegrationResponse invoke(IntegrationRequest request);
}
