# mobile-commander — Commander Application (Stage 7.2)

**Статус:** архитектура и контракты утверждены (Stage 7). Приложение начальника караула на едином
мобильном стеке (ADR-016), общий Mobile SDK и дизайн-система.

## Возможности (7.2)
Состав подразделения; техника; оборудование; задачи; журнал команд/событий; контроль выполнения;
запрос дополнительных сил; изменение статуса работ.

## Платформа и правила
- Единый Mobile SDK и дизайн-система; offline-first (ADR-017).
- Координация/усиление — общий контракт (`operational-coordination-api.yaml`, Stage 6) по
  подтверждению (не автоматически).
- Только общие платформенные API; секреты не в коде; MDM/безопасность — Mobile/Device Security.

## Стандарты
Incident Command, Field Operations, Offline Synchronization, Operational Messaging, Mobile Security,
Mobile Accessibility/Performance/Battery/Audit/Lifecycle.
