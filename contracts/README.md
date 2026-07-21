# contracts/ — контракты API платформы (API First, Stage 4)

Контракты **описаны до реализации** (Constitution ARTICLE 12; API Lifecycle Management Standard).
OpenAPI 3.1. Формат ошибок — RFC 7807 (`shared/problem-detail.yaml`).

## Структура
| Каталог | Назначение |
|---------|-----------|
| `internal/` | Внутренние API платформы (config, notification, file-storage, search, time) и операционные (call-intake, recording, speech-processing, information-extraction, incident-card, decision-support, resource-recommendation, operational-timeline — Stage 5) |
| `admin/` | Административные API (identity/user management, audit, operational-audit) |
| `external/` | Внешние API (партнёрские/межведомственные) — по мере утверждения |
| `shared/` | Переиспользуемые компоненты (ProblemDetail, Page) |

## Правила
- Версия в пути (`/v1`); эволюция — API Guidelines / API Lifecycle Management Standard.
- Аутентификация — Bearer JWT (Keycloak, ADR-007); авторизация — RBAC/ABAC.
- Контракты не содержат прикладной логики (обработка вызовов/инцидентов/диспетчеризации запрещена).
