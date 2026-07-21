# contracts/ — контракты API платформы (API First, Stage 4)

Контракты **описаны до реализации** (Constitution ARTICLE 12; API Lifecycle Management Standard).
OpenAPI 3.1. Формат ошибок — RFC 7807 (`shared/problem-detail.yaml`).

## Структура
| Каталог | Назначение |
|---------|-----------|
| `internal/` | Внутренние API платформы (config, notification, file-storage, search, time); операционные Stage 5 (call-intake, recording, speech-processing, information-extraction, incident-card, decision-support, resource-recommendation, operational-timeline); GIS Stage 6 (gis, address-intelligence, resource-registry, resource-availability, routing, dispatch-recommendation, operational-map, resource-tracking, operational-coordination, geospatial-analytics); мобильные общие Stage 7 (mobile-sync, operational-messaging, operational-forms, media, field-telemetry, mdm-integration); аналитика Stage 8 (operational-analytics, kpi, dashboards, decision-intelligence, data-warehouse, forecasting, situation-center, data-quality, ai-analytics) |
| `admin/` | + reporting (Stage 8) |
| `admin/` | Административные API (identity/user management, audit, operational-audit) |
| `external/` | Внешние API (партнёрские/межведомственные) — по мере утверждения |
| `shared/` | Переиспользуемые компоненты (ProblemDetail, Page) |

## Правила
- Версия в пути (`/v1`); эволюция — API Guidelines / API Lifecycle Management Standard.
- Аутентификация — Bearer JWT (Keycloak, ADR-007); авторизация — RBAC/ABAC.
- Контракты не содержат прикладной логики (обработка вызовов/инцидентов/диспетчеризации запрещена).
