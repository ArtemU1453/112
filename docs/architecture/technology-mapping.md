# Technology Mapping — ЕАСУР

- **Status:** APPROVED · Baseline: v1.0
- Реализует Technology Abstraction Policy (TAP). Связывает **возможности (capabilities)** с
  утверждёнными технологиями (через ADR) и версиями (через каталог версий, TVMS).

## Правило использования

Во всех документах вместо названий продуктов ссылаться на **Capability ID** и/или **ADR**.
Конкретный продукт определяется здесь и в ADR; конкретная версия — в
`config/toolchain/versions.yaml`.

## Реестр возможностей

| Capability ID | Возможность | ADR | Утверждённая технология | Статус | Утверждено |
|---------------|-------------|-----|-------------------------|--------|------------|
| CAP-LANG-001 | Рантайм доменных сервисов | ADR-001 | Java (LTS) + Spring Boot | Approved | Baseline v1.0 |
| CAP-LANG-002 | Рантайм AI-сервиса | ADR-001 | Python + FastAPI | Approved | Baseline v1.0 |
| CAP-ARCH-001 | Стиль системы (микросервисы, событийная интеграция) | ADR-002 | Микросервисы + Clean/Hexagonal/CQRS | Approved | Baseline v1.0 |
| CAP-DB-001 | Реляционная база данных | ADR-003 | PostgreSQL | Approved | Baseline v1.0 |
| CAP-DB-002 | Геопространственная база данных | ADR-003 | PostGIS | Approved | Baseline v1.0 |
| CAP-DB-003 | Гибкое хранение событий аудита | ADR-003 | PostgreSQL JSONB | Approved | Baseline v1.0 |
| CAP-CACHE-001 | Кэш и распределённый счётчик | ADR-003 | Redis | Approved | Baseline v1.0 |
| CAP-SCHEMA-001 | Версионирование схемы БД | ADR-003 | Liquibase | Approved | Baseline v1.0 |
| CAP-FE-001 | Клиентское веб-приложение (АРМ) | ADR-004 | React + TypeScript | Approved | Baseline v1.0 |
| CAP-CONTAINER-001 | Контейнеризация | ADR-005 | Docker/OCI | Approved | Baseline v1.0 |
| CAP-ORCH-001 | Оркестрация | ADR-005 | Kubernetes | Approved | Baseline v1.0 |
| CAP-RELEASE-001 | Управление релизами в кластер | ADR-005 | Helm | Approved | Baseline v1.0 |
| CAP-CI-001 | Непрерывная интеграция/доставка | ADR-006, ADR-009 | GitHub Actions (осн.), GitLab CI | Approved | Baseline v1.0 |
| CAP-IDP-001 | Identity Provider (OIDC/OAuth2) | ADR-007 | Keycloak | Approved | Baseline v1.0 |
| CAP-MSG-001 | Брокер сообщений (событийная шина) | ADR-008 | Apache Kafka | Approved | Baseline v1.0 |
| CAP-OBS-001 | Метрики/мониторинг | Architecture.md | Prometheus | Approved | Baseline v1.0 |
| CAP-OBS-002 | Логи (агрегация) | Architecture.md | Loki | Approved | Baseline v1.0 |
| CAP-OBS-003 | Визуализация/дашборды | Architecture.md | Grafana | Approved | Baseline v1.0 |
| CAP-IAC-001 | Infrastructure as Code | ADR-010 | Terraform + Ansible | Approved | Stage 3 |
| CAP-SECRETS-001 | Централизованное хранилище секретов | ADR-011 | HashiCorp Vault + External Secrets | Approved | Stage 3 |
| CAP-STORAGE-001 | Объектное хранилище (бэкапы, артефакты, зеркала) | ADR-013 | S3-совместимое (self-hosted, напр. MinIO) | Approved | Stage 3 |
| CAP-TRACE-001 | Распределённая трассировка | ADR-012 | OpenTelemetry + Grafana Tempo | Approved | Stage 3 |
| CAP-REGISTRY-001 | Реестр контейнерных образов (локальный) | ADR-005, ADR-010 | OCI-реестр (self-hosted/зеркало) | Approved | Stage 3 |

## Возможности со статусом Proposed

На текущем этапе таких возможностей нет: `CAP-SECRETS-001`, `CAP-STORAGE-001`, `CAP-TRACE-001`
переведены в Approved на Stage 3 (ADR-011/013/012, RFC-0002). Новые возможности со статусом
Proposed добавляются здесь и утверждаются через RFC + новый ADR (TAP «Изменение технологии»);
до утверждения документы ссылаются на возможность, а не продукт.

## Синхронизация

При добавлении/замене технологии обновляются: соответствующий ADR, данная таблица, каталог
версий (при необходимости) и зависимые ссылки. Проверка ссылок — в CI (documentation/architecture
validation).
