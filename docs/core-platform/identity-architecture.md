# Identity Architecture — ЕАСУР

- **Status:** APPROVED · Stage 4 · Подчиняется ADR-007, Security Architecture.

## Аутентификация
OIDC/OAuth2 через Keycloak (ADR-007). SPA — Authorization Code + PKCE. Сервисы — OAuth2 resource
server; рабочие нагрузки в кластере — Kubernetes ServiceAccount (Vault auth, ADR-011).

## Компоненты Identity Platform
- **Authentication / Authorization** — Keycloak + проверка на Gateway и в сервисах.
- **Session / Token Management** — токены OIDC (короткоживущие access + refresh); отзыв через IdP.
- **Password Policy / MFA Framework / Account Lockout** — политики Keycloak (сложность паролей,
  многофакторная аутентификация, блокировка после N неудач).
- **Service Accounts** — учётные записи сервисов (client credentials) с минимальными привилегиями.

## RBAC / ABAC
- **RBAC:** роли `ROLE_DISPATCHER`, `ROLE_SENIOR_DISPATCHER`, `ROLE_ADMIN`, `ROLE_CREW`,
  `ROLE_ANALYST` (константы — `platform-commons` `Roles`).
- **ABAC (расширение):** политики на основе атрибутов (подразделение, время действия прав,
  делегирование) — поверх ролей; временные и делегированные права, аудит изменений доступа.
- Организационная модель: пользователи → должности → подразделения → организации (справочники).

## User Management Platform
Управление пользователями/подразделениями/должностями/организациями/справочниками и жизненным
циклом учётных записей (ACTIVE/BLOCKED) — admin API `contracts/admin/identity-admin-api.yaml`
(реализуется auth-service поверх Keycloak Admin API). Все изменения доступа — в аудит.

## Соответствие
Security Architecture, Constitution ARTICLE 10; секреты клиентов — Vault (ADR-011). Без прикладной
логики (Stage 4).
