# mobile-crew — Field Responder Mobile Application (Stage 7.1)

**Статус:** архитектура и контракты утверждены (Stage 7); реализация — на едином мобильном стеке
(ADR-016) с общим Mobile SDK и дизайн-системой. Приложение подразделения (пожарно-спасательные/
аварийно-спасательные).

## Возможности (7.1)
Получение карточки выезда; маршрут и навигация; информация об объекте; статус подразделения; журнал
действий; отправка статусов; прикрепление фото/видео/документов/координат.

## Платформа и правила
- Единый Mobile SDK (`docs/mobile/mobile-sdk-guide.md`); дизайн-система (`libraries/mobile-design-tokens`).
- Offline-first (ADR-017); синхронизация — `contracts/internal/mobile-sync-api.yaml`.
- API — только **общие** платформенные контракты (`contracts/`); app-специфичные API запрещены.
- Безопасность — Mobile/Device Security Standards; секреты не в коде; MDM (ADR-018).

## Стандарты
Field Operations, Offline Synchronization, Mobile Security, Media Governance, Operational Forms,
Field Telemetry, Operational Messaging, Mobile Accessibility/Performance/Battery/Audit/Lifecycle.
