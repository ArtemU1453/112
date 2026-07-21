# API Guidelines — ЕАСУР

- **Status:** APPROVED · Stage 4 · Дополняет API Governance Standard (AGS) для Core Platform.

## Контракты
Все API описаны контрактами до реализации (API First, `contracts/`; API Lifecycle Management
Standard). OpenAPI 3.1; internal/admin/external разделены.

## Стиль
- Путь: `/{scope}/{domain}/v{N}/{resource}` (например, `/internal/config/v1/parameters`).
- Ресурсы — существительные во множественном числе, `kebab-case`.
- Версия — в пути; эволюция — аддитивно; несовместимо — новая мажорная версия.

## Пагинация/фильтрация/сортировка
`?page`, `?size` (0-based; `PageRequest`), фильтры по полям, `?sort=field,asc|desc`.
Ответ-страница содержит метаданные (`Page`: page/size/totalElements/totalPages).

## Ошибки
Единый формат RFC 7807 (`shared/problem-detail.yaml`, `ProblemDetail`): `type/title/status/code/
detail/instance/correlationId/timestamp/violations`. Без утечки внутренних деталей.

## Безопасность
Bearer JWT (Keycloak, ADR-007); авторизация RBAC/ABAC; идемпотентность изменяющих операций при
необходимости. Rate limiting и аудит запросов — на Gateway (API Gateway Platform).

## Коды ответов
200/201/204; 400/401/403/404/409/422/429; 5xx. Семантика — AGS.

## Соответствие
API Governance Standard, API Lifecycle Management Standard, Security Architecture.
