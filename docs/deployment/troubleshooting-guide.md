# Troubleshooting Guide — ЕАСУР

- **Status:** APPROVED · Диагностика проблем локального (Docker Compose) и кластерного
  (Kubernetes) развёртывания. Дополняет Runbooks и Operational Procedures.

## 1. Локальное окружение (Docker Compose)

| Симптом | Диагностика | Действие |
|---------|-------------|----------|
| Сервис не поднимается | `docker compose -f docker-compose.yml ps`, `logs -f <service>` | Проверить зависимости (`depends_on` healthy), переменные `.env` |
| Порт занят | `docker compose ... ps`, проверка локальных портов | Освободить порт или переопределить в `.env` |
| БД без схемы | Логи сервиса (Liquibase changelog) | Дождаться healthy postgres; при повреждении — `scripts/reset-environment.sh` |
| Устаревшие данные/состояние | — | Полный сброс: `scripts/reset-environment.sh` (удаляет volumes) |
| Нет Docker Compose | `docker compose version` | Установить Docker; см. [Local Development Guide](local-development-guide.md) |

Общая последовательность: `ps` → `logs -f <service>` → проверка `.env` и healthcheck зависимостей.

## 2. Kubernetes / Helm

| Симптом | Диагностика | Действие |
|---------|-------------|----------|
| Pod в `CrashLoopBackOff` | `kubectl -n <ns> logs <pod> --previous` | Исправить конфигурацию/секрет, переустановить релиз |
| Pod в `Pending` | `kubectl -n <ns> describe pod <pod>` | Проверить ресурсы/квоты (`kubernetes/platform/namespaces/resource-quotas.yaml`), storage class |
| `ImagePullBackOff` | `describe pod` (events) | Проверить `global.registry`/`global.imageTag`, доступ к реестру |
| Readiness не проходит | `kubectl -n <ns> get pods`, `logs` | Проверить `/actuator/health/readiness`, доступность БД/Kafka/Keycloak |
| Не применяются секреты | `kubectl -n <ns> get secret,externalsecret` | Проверить External Secrets/Vault (`kubernetes/platform/config/secret-store.yaml`) |
| Ingress не отвечает | `kubectl -n <ns> get ingress`, логи ingress-nginx | Проверить хосты/TLS (`ingress.hosts.*`), cert-manager |
| Неуспешный `helm upgrade` | `helm -n <ns> history easur` | При `--atomic` откат автоматический; иначе `helm rollback` |

## 3. Типовые причины по зависимостям

- **PostgreSQL/PostGIS:** сервис не стартует до готовности БД и применения Liquibase-changelog.
  Проверить доступность хоста БД и корректность `SPRING_DATASOURCE_*` / `infrastructure.postgresHost`.
- **Kafka (KRaft):** консюмеры/продюсеры логируют ретраи при недоступности брокера —
  проверить `KAFKA_BOOTSTRAP_SERVERS` / `infrastructure.kafkaBootstrapServers`.
- **Keycloak (OIDC):** 401/пустой JWT — проверить `issuer-uri`
  (`infrastructure.keycloakIssuerUri`) и доступность realm `emergency-112`.
- **Redis:** для кэширующих сервисов (gateway, incident) — проверить `infrastructure.redisHost`.

## 4. Диагностика наблюдаемости

- Метрики: Prometheus targets должны быть `UP`; локально —
  `docker compose -f docker/compose.monitoring.yml up -d`.
- Логи: Loki/Promtail (кластер) либо `docker compose ... logs` (локально).
- Health: `GET /actuator/health` (liveness/readiness) у Java-сервисов.

## 5. Эскалация

Если проблема не локализуется по этому руководству — перейти к сценариям Runbooks и
Operational Procedures (`docs/operations/`), при инцидентах уровня платформы — к Disaster
Recovery Plan. Не отключать механизмы безопасности (аутентификацию, TLS, сетевые политики)
ради обхода проблемы.

## 6. Связанные документы

[Local Development Guide](local-development-guide.md) · [Docker Guide](docker-guide.md) ·
[Kubernetes Guide](kubernetes-guide.md) · [Helm Guide](helm-guide.md) ·
[Deployment Architecture](deployment-architecture.md).
