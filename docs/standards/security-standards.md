# Security Standards — ЕАСУР

- **Status:** APPROVED · Baseline: v1.0

Единые требования безопасности (Security by Design; Constitution ARTICLE 10). Государственная
система с обработкой персональных данных заявителей.

## Аутентификация и авторизация
- Аутентификация — через Identity Provider (`CAP-IDP-001`, ADR-007): OIDC/OAuth2, JWT.
- Клиент (SPA) — Authorization Code + PKCE (S256). Пароли в приложениях не хранятся.
- Авторизация — RBAC-роли (`ROLE_DISPATCHER`, `ROLE_SENIOR_DISPATCHER`, `ROLE_ADMIN`,
  `ROLE_CREW`, `ROLE_ANALYST`); проверка на периметре (Gateway) и в сервисах (resource server).
- Принцип минимальных привилегий для пользователей, сервисов и учётных записей CI.

## Управление секретами
- Секреты не хранятся в Git/Dockerfile/образах/открытых конфигах (RGS, EGS).
- Источник — внешний секрет-менеджер (`CAP-SECRETS-001`) / Kubernetes Secret / External Secrets.
- Ротация секретов и токенов; короткоживущие токены доступа.

## Защита данных
- Минимизация ПДн; шифрование при передаче (TLS) и, где требуется, при хранении.
- Записи разговоров и ПДн — с контролем доступа и аудитом; сроки хранения — по политике (открытый
  вопрос Vision §8, фиксируется решением/ADR).
- Реальные ПДн не используются вне Production (EGS).

## Безопасность цепочки поставки (Supply Chain)
- **Secret Scanning** — обнаружение секретов в коммитах/истории (CI).
- **Dependency Scanning** — CVE в прямых/транзитивных зависимостях (DepGS; блок при critical/high).
- **Container Scanning** — уязвимости базовых образов и слоёв.
- **License Checking** — только допустимые лицензии (Apache-2.0/MIT/BSD; прочее — по решению).
- **SBOM Generation** — CycloneDX/SPDX для каждого артефакта (прослеживаемость состава).

Автоматизация — `.github/workflows/{security,dependency-scan}.yml`; конфигурация секрет-сканера —
`config/quality/gitleaks.toml`.

## Безопасная разработка
- Обработка ошибок без утечки внутренних деталей/стека/ПДн (RFC 7807, AGS).
- Валидация всех входных данных (Bean Validation/Pydantic); защита от инъекций (параметризованные
  запросы, ORM).
- Ограничение частоты запросов на периметре (rate limiting) против злоупотреблений.
- Аудит значимых действий (audit-service, `audit.events`).

## Наблюдаемость безопасности
- Логирование событий доступа/отказа без чувствительных данных (Logging Standards).
- Алертинг на аномалии (Environment Governance Standard, operations.md).

## Процесс
- Security Review — обязательное Quality Gate (QG5) перед слиянием.
- Уязвимости/инциденты фиксируются как задачи Backlog (Risk Register: R-S1..R-S4).
- Каналы сообщения об уязвимостях — `SECURITY.md`.
