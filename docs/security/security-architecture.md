# Security Architecture — ЕАСУР

- **Status:** APPROVED · Stage 3 · Подчиняется Security Standards, Project Constitution
  (ARTICLE 10), EAGF (Security/Privacy by Design).

Государственная система с ПДн заявителей. Многоуровневая защита (defence in depth).

## 1. Identity & Access Management (IAM)
- Аутентификация пользователей — OIDC/OAuth2 (Keycloak, ADR-007); PKCE для SPA.
- Авторизация — RBAC-роли (`ROLE_DISPATCHER`, `ROLE_SENIOR_DISPATCHER`, `ROLE_ADMIN`,
  `ROLE_CREW`, `ROLE_ANALYST`); проверка на Gateway и в сервисах.
- Рабочие нагрузки в кластере — Kubernetes ServiceAccount + RBAC (минимальные привилегии,
  `kubernetes/platform/rbac`).
- Аутентификация нагрузок в Vault — через Kubernetes auth (ServiceAccount → роль Vault).

## 2. Управление секретами
- Централизованно в Vault (ADR-011); доставка в кластер через External Secrets (`ExternalSecret`
  → `Secret`). Значения секретов в git отсутствуют (RGS, EGS).
- Политика ротации, аудит доступа, аварийное восстановление — Secrets Management.

## 3. Защита Kubernetes
- **Pod Security Admission** (restricted в prod) на namespaces.
- **RBAC** минимальных привилегий; отдельные ServiceAccount на роль.
- **ResourceQuota/LimitRange** — защита от исчерпания ресурсов.
- Запрет привилегированных контейнеров, root, hostPath (Pod Security restricted).

## 4. Защита сети
- Default-deny + явные разрешения (Network Architecture).
- Сегментация по зонам; привилегированные потоки — только авторизованным SA.
- TLS на периметре; внутрикластерный трафик — по политике.

## 5. Защита контейнеров (Container Platform)
- Базовые образы — минимальные, из локального доверенного реестра.
- **Image scanning** (уязвимости) и **image signing** (подпись) — в CI/CD (dependency-scan/security).
- **SBOM** (CycloneDX) на каждый артефакт; **image lifecycle** — теги SHA+канал, удаление устаревших.
- Registry policy — только доверенный реестр; в air-gapped — локальное зеркало.

## 6. Защита данных
- Минимизация ПДн; TLS при передаче; шифрование при хранении где требуется.
- Записи разговоров/ПДн — контроль доступа + аудит; сроки хранения — по политике (DGS/EGS).
- Реальные ПДн не используются вне Production (EGS).

## 7. Аудит и журнал безопасности
- Бизнес-действия — audit-service (`audit.events`).
- Операции администраторов и привилегированные действия — журнал безопасности (Operational
  Security, Platform Operations Framework).
- Kubernetes audit log; журнал доступа к секретам (Vault audit device).

## 8. Проверки (Quality/Security Gates)
Security Review (QG5), secret/dependency/container scanning, license, SBOM (Security Standards).
Регулярная проверка прав доступа и ротация учётных данных (Operational Security).

## 9. Связанные документы
Security Standards · Network Architecture · Secrets Management · IAM · Platform Operations
Framework · Air-Gapped Standard · Risk Register (R-S1..R-S4).
