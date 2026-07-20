# ADR Index — ЕАСУР

Architecture Decision Records фиксируют значимые архитектурные решения. Формат каждого ADR:
**Статус · Контекст · Рассматриваемые варианты · Принятое решение · Последствия** (плюс
обоснование: проблема, альтернативы, причины выбора, преимущества, недостатки, компромиссы,
влияние на развитие, условия пересмотра).

Статусы: `Proposed` → `Under Review` → `Accepted` → (`Superseded by ADR-XXX` | `Deprecated`).

Изменение принятого ADR — только через новый RFC и новый ADR (см. `docs/rfc/README.md`).

| ADR | Тема | Статус |
|-----|------|--------|
| [ADR-001](ADR-001-language.md) | Язык программирования backend | Accepted |
| [ADR-002](ADR-002-architecture.md) | Архитектурный стиль | Accepted |
| [ADR-003](ADR-003-database.md) | База данных | Accepted |
| [ADR-004](ADR-004-frontend.md) | Frontend | Accepted |
| [ADR-005](ADR-005-containerization.md) | Контейнеризация и оркестрация | Accepted |
| [ADR-006](ADR-006-cicd.md) | CI/CD | Accepted |
| [ADR-007](ADR-007-authorization.md) | Система авторизации | Accepted |
| [ADR-008](ADR-008-message-broker.md) | Брокер сообщений | Accepted |

Нумерация сквозная и неизменяемая. Новый ADR получает следующий свободный номер.
