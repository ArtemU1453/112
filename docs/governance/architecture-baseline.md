# Architecture Baseline — ЕАСУР

- **Architecture Baseline:** v1.0
- **Status:** APPROVED
- **Дата утверждения:** Project Start

Architecture Baseline — официальная базовая архитектура проекта и эталон, с которым
сравниваются все последующие решения.

## Состав Baseline

Vision · Project Constitution · Architecture Principles · Architecture.md · System Structure ·
Domain Model (см. Domain Modeling Standard) · Technology Stack (ADR-001..008) · Development
Guidelines · Coding Standards · Git Strategy · Definition of Ready · Definition of Done ·
ADR Index · RFC Process · Quality Gates · Risk Register · Security Principles · Deployment
Principles · Documentation Standards.

## Технологический стек (сводка, детали в ADR)

| Область | Решение | ADR |
|---------|---------|-----|
| Backend | Java 21 (LTS) + Spring Boot 3.3; ai-service — Python/FastAPI | ADR-001 |
| Стиль | Микросервисы, событийная интеграция, Clean Arch, гексагон, CQRS точечно | ADR-002 |
| Данные | PostgreSQL + PostGIS + JSONB; Redis; Liquibase | ADR-003 |
| Frontend | React 19 + TS, Redux Toolkit, MUI, OpenLayers, Vite | ADR-004 |
| Платформа | Docker, Kubernetes, Helm; Compose локально | ADR-005 |
| CI/CD | GitLab CI/CD | ADR-006 |
| Auth | Keycloak (OIDC/OAuth2, RBAC) | ADR-007 |
| Брокер | Apache Kafka | ADR-008 |
| Наблюдаемость | Prometheus, Grafana, Loki | Architecture.md |

## Правило неизменности (Architecture Freeze)

После утверждения Baseline **запрещается без отдельного RFC и нового ADR** изменять:
архитектурный стиль; структуру модулей; структуру репозитория; структуру каталогов; правила
зависимостей; правила именования; технологический стек; архитектурные принципы.

## Architecture Governance

Стадии архитектурных изменений (только последовательно):
`Proposed → Under Review → Approved → Implemented → Verified → Closed`.

## Architecture Compliance

Перед завершением любой задачи — проверка соответствия Baseline: Vision, Constitution,
Architecture Principles, ADR, RFC, структура модулей, правила зависимостей, Coding Standards.
При отклонении задача не считается завершённой.

## Architecture Drift

Архитектурный дрейф — расхождение утверждённой архитектуры и реализации. При обнаружении:
описать отклонение, оценить влияние, определить причину, предложить устранение, создать задачу
Backlog, при необходимости — RFC. Игнорировать дрейф запрещено.

## Baseline Review и версионирование

Baseline пересматривается только после завершения крупного этапа: Foundation Complete,
Infrastructure Complete, Core Platform Complete, Dispatcher MVP Complete, AI Integration
Complete, Pilot Release, Production Release. Вне этих этапов Baseline не изменяется.

Каждое изменение Baseline оформляется новой версией (v1.0 → v1.1 → v2.0 …) с Changelog и
перечнем затронутых документов.

## Change Control

Каждое изменение имеет идентификатор (`RFC-XXXX`, `ADR-XXXX`), дату, автора, причину, описание
влияния, план миграции, статус реализации.

## Governance Report

После завершения этапа готовится краткий отчёт: использованные архитектурные решения,
затронутые ADR/RFC, наличие дрейфа, технического долга, рисков, соответствие Baseline.

## Правило для ИИ

Если задача требует изменения Baseline, ИИ обязан остановить реализацию, обосновать
необходимость, подготовить RFC и ADR, выполнить Impact Analysis, предложить Migration Plan,
дождаться утверждения и только затем продолжить.
