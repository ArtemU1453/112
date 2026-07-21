# Service Maturity Model — ЕАСУР

- **Status:** APPROVED · Stage 4 · Согласован с Engineering Handbook, Internal SDK Governance
  Standard, Quality Gates.

## Цель
Определить уровни зрелости сервиса и обязательные критерии перехода, гарантирующие
переиспользуемость, безопасность, наблюдаемость и сопровождаемость.

## Уровни

| Уровень | Название | Обязательные критерии |
|---------|----------|------------------------|
| L0 | Skeleton | Каталог/структура по RGS; README; заведён в Backlog |
| L1 | Integrated | Подключён Platform SDK; единые ошибки/логирование/безопасность/конфигурация |
| L2 | Contracted | API описаны контрактами (API First); события — Event Governance Standard |
| L3 | Observable | Метрики/логи/трассы/health (Operational Telemetry Standard); алерты |
| L4 | Reliable | Тесты (Testing Strategy) и Quality Gates зелёные; HA/масштабирование; бэкап где применимо |
| L5 | Governed | Соответствие Baseline/стандартам; документация синхронизирована; аудит готовности |

## Правила
- Новый сервис не может выйти в production ниже уровня **L4**; платформенные — не ниже **L5**.
- Переход уровня подтверждается на Governance Checkpoint (EAGF).
- Несоответствие — задача Backlog (техдолг), не игнорируется (ARTICLE 19).

## Оценка
Периодический аудит зрелости (Platform Operations Framework); результаты фиксируются.

## Definition of Done
Определены уровни, критерии перехода и правила применения; модель согласована со стандартами.
