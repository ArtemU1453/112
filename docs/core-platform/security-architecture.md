# Core Platform Security Architecture — ЕАСУР

- **Status:** APPROVED · Stage 4 · Дополняет платформенную Security Architecture
  (`docs/security/security-architecture.md`) на уровне общих сервисов.

## Единые механизмы через SDK
Каждый сервис получает безопасность через `platform-sdk`/`platform-commons`:
- **SecurityContext / PlatformPrincipal** — единый доступ к субъекту и ролям (из JWT, ADR-007).
- **Roles** — общие константы RBAC; проверки `requireRole` (least privilege).
- **Error Handling** — RFC 7807 без утечки внутренних деталей/стека/ПДн (безопасное отображение).
- **CorrelationContext** — корреляция для аудита/трассировки без чувствительных данных.

## Аутентификация и авторизация
OIDC/JWT (Keycloak); RBAC + ABAC (Identity Architecture). Административные/внешние API —
отдельные контракты (`contracts/admin`, `contracts/external`) с явными ролями.

## Секреты
Через Vault + External Secrets (ADR-011); в коде/библиотеках секретов нет.

## Аудит безопасности
Изменения доступа, привилегированные операции и действия администраторов — в неизменяемый аудит
(Audit Platform) и журнал безопасности (Operational Security, POF).

## Валидация и защита ввода
Единый Validation Framework (`platform-commons.validation`) — полная валидация входных данных;
защита от инъекций — на уровне ORM/параметризованных запросов (Coding Standards).

## Соответствие
Security Standards, Constitution ARTICLE 10, Security by Design. Без прикладной логики (Stage 4).
