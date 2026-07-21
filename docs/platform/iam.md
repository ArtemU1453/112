# Identity & Access Management (IAM) — ЕАСУР

- **Status:** APPROVED · Stage 3 · Подчиняется ADR-007, Security Architecture.

## Аутентификация
- Пользователи — OIDC/OAuth2 через Keycloak (ADR-007); SPA — Authorization Code + PKCE.
- Рабочие нагрузки — Kubernetes ServiceAccount; доступ к Vault — Kubernetes auth.

## Авторизация (RBAC)
- Прикладные роли: `ROLE_DISPATCHER`, `ROLE_SENIOR_DISPATCHER`, `ROLE_ADMIN`, `ROLE_CREW`,
  `ROLE_ANALYST` (проверка на Gateway и в сервисах).
- Кластерный RBAC — минимальные привилегии (`kubernetes/platform/rbac`): отдельные роли/SA для
  деплоя, мониторинга, External Secrets.

## Сервисные учётные записи
`platform-deployer` (деплой в прикладной namespace), `monitoring-agent` (read-only метрики),
`external-secrets` (доступ к Vault). Никаких общих привилегированных SA.

## Минимальные привилегии
- Роли выдают только необходимые глаголы/ресурсы.
- Отсутствие cluster-admin у прикладных SA; привилегированные операции — под аудитом.

## Регулярный контроль
Периодический пересмотр прав доступа и ротация учётных данных (Operational Security, POF).
