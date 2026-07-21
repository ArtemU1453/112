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
| [ADR-009](ADR-009-ci-platform-github-actions.md) | Основная CI-платформа: GitHub Actions (дополняет ADR-006) | Accepted |
| [ADR-010](ADR-010-iac-tooling.md) | Инструменты Infrastructure as Code (Terraform + Ansible) | Accepted |
| [ADR-011](ADR-011-secrets-management.md) | Управление секретами (Vault + External Secrets) | Accepted |
| [ADR-012](ADR-012-distributed-tracing.md) | Распределённая трассировка (OpenTelemetry + Tempo) | Accepted |
| [ADR-013](ADR-013-object-storage.md) | Объектное хранилище (S3-совместимое, self-hosted) | Accepted |
| [ADR-014](ADR-014-routing-navigation-abstraction.md) | Абстракция маршрутизации и навигации (self-hosted) | Accepted |
| [ADR-015](ADR-015-cartographic-telemetry-provider-abstraction.md) | Абстракция картографии и телеметрии (self-hosted) | Accepted |
| [ADR-016](ADR-016-mobile-technology-stack.md) | Технологический стек мобильной экосистемы (единый) | Accepted |
| [ADR-017](ADR-017-offline-first-synchronization.md) | Offline-First и синхронизация данных | Accepted |
| [ADR-018](ADR-018-mdm-integration-abstraction.md) | Абстракция интеграции с MDM | Accepted |
| [ADR-019](ADR-019-analytics-data-warehouse.md) | Аналитическое хранилище данных (DWH, self-hosted) | Accepted |
| [ADR-020](ADR-020-bi-visualization.md) | Платформа BI и визуализации (self-hosted) | Accepted |
| [ADR-021](ADR-021-ai-analytics-abstraction.md) | Абстракция аналитического ИИ и объяснимость | Accepted |
| [ADR-022](ADR-022-national-integration-platform.md) | Национальная интеграционная платформа (ESB/gateway, self-hosted) | Accepted |
| [ADR-023](ADR-023-integration-security-pki.md) | Безопасность интеграций: mTLS, подпись сообщений, PKI | Accepted |
| [ADR-024](ADR-024-production-go-live-operational-acceptance.md) | Ввод в промышленную эксплуатацию и операционная приёмка | Accepted |

Нумерация сквозная и неизменяемая. Новый ADR получает следующий свободный номер.
