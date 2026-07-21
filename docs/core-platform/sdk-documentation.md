# SDK Documentation — ЕАСУР (platform-sdk)

- **Status:** APPROVED · Stage 4 · Подчиняется Internal SDK Governance Standard.

## Назначение
`platform-sdk` — единая точка подключения общих возможностей платформы для **каждого** сервиса:
Security, Logging, Configuration, Audit, Error Handling, Monitoring, Tracing (`PlatformModule`).

## Состав
- Агрегирует `platform-commons` (ошибки, DTO, валидация, время, локализация, логирование,
  безопасность, идентификаторы) и `platform-abstractions` (порты storage/search/notification/ai/
  messaging/integration/document).
- `PlatformContext` — доступ к `SecurityContext`, `TimeProvider`, `MessageResolver`, имени сервиса.
- `PlatformSdk` — версия и перечень предоставляемых модулей.

## Подключение
Зависимость `by.mchs.e112:platform-sdk` (версии — BOM `libraries/build/bom`, каталог TVMS).
Сервис реализует `PlatformContext` через свою конфигурацию (например, Spring), не дублируя
инфраструктурный код.

## Требования (Service Maturity Model)
Базовый уровень зрелости сервиса требует использования SDK и единых библиотек. Прикладная логика
в SDK/библиотеках запрещена (Stage 4; Shared Domain Kernel Standard).

## Связанные документы
Common Libraries Guide · Internal SDK Governance Standard · Operational Telemetry Standard ·
Service Maturity Model.
