# mobile-command — Incident Commander Workspace (Stage 7.3)

**Статус:** архитектура и контракты утверждены (Stage 7). Рабочее место руководителя ликвидации ЧС/
штаба на едином мобильном стеке (ADR-016), общий Mobile SDK и дизайн-система.

## Возможности (7.3)
Управление силами; распределение задач; журнал решений; временная шкала; карта; контроль
безопасности; журнал приказов; журнал рисков; контроль зон работ.

## Платформа и правила
- Единый Mobile SDK и дизайн-система; offline-first (ADR-017).
- Карта — оперативная карта (Stage 6); силы/координация — общие контракты (Stage 6); решения — с
  фиксацией; приказы/риски журналируются (Mobile Audit Standard).
- Только общие платформенные API; секреты не в коде; MDM/безопасность — Mobile/Device Security.

## Стандарты
Incident Command, Offline Synchronization, Operational Messaging, Mobile Security, Media Governance,
Mobile Accessibility/Performance/Battery/Audit/Lifecycle.
