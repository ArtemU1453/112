# Helm-чарт `emergency-112`

Зонтичный чарт для развёртывания системы диспетчеризации экстренных служб 112/МЧС
в Kubernetes. Генерирует `Deployment` + `Service` для всех прикладных сервисов,
общий `ConfigMap`, `Secret`, `Ingress`, при необходимости `HorizontalPodAutoscaler`
и `CronJob` резервного копирования БД.

## Состав

Чарт разворачивает **прикладные** сервисы (по одному `Deployment`/`Service` на каждый):

| Сервис               | Порт | БД              | Kafka | Redis | Keycloak |
|----------------------|------|-----------------|-------|-------|----------|
| gateway-service      | 8080 | —               | —     | ✓     | ✓        |
| auth-service         | 8081 | auth_db         | ✓     | —     | ✓ (admin)|
| incident-service     | 8082 | incident_db     | ✓     | ✓     | ✓        |
| dispatch-service     | 8083 | dispatch_db     | ✓     | —     | ✓        |
| telephony-service    | 8084 | telephony_db    | ✓     | —     | ✓        |
| gis-service          | 8085 | gis_db          | —     | —     | ✓        |
| audit-service        | 8086 | audit_db        | ✓     | —     | ✓        |
| notification-service | 8087 | notification_db | ✓     | —     | ✓        |
| realtime-service     | 8088 | —               | ✓     | —     | ✓        |
| ai-service           | 8090 | —               | ✓     | —     | —        |
| frontend-dispatcher  | 80   | —               | —     | —     | —        |

## Зависимости инфраструктуры

Чарт **не** разворачивает PostgreSQL/PostGIS, Kafka, Redis и Keycloak — они
подключаются как внешние endpoint'ы (управляемые сервисы или отдельные чарты
операторов). Адреса задаются в `infrastructure.*`:

```yaml
infrastructure:
  kafkaBootstrapServers: kafka:9092
  keycloakIssuerUri: http://keycloak:8080/realms/emergency-112
  keycloakAdminUrl: http://keycloak:8080
  postgresHost: postgres
  postgresPort: "5432"
  redisHost: redis
```

## Установка

```bash
# staging
helm upgrade --install emergency-112 helm/emergency-112 \
  --namespace emergency-staging --create-namespace \
  --set global.imageTag=$CI_COMMIT_SHORT_SHA \
  --set global.registry=$REGISTRY

# production (с оверрайдами)
helm upgrade --install emergency-112 helm/emergency-112 \
  --namespace emergency --create-namespace \
  -f helm/emergency-112/values-production.yaml \
  --set global.imageTag=stable \
  --set secrets.dbPassword=$DB_PASSWORD \
  --set secrets.keycloakAdminPassword=$KC_ADMIN_PASSWORD
```

> В git-репозитории `secrets.*` содержат только заглушки. В проде передавайте
> реальные значения через `--set`, `--values` из защищённого хранилища или
> внешний секрет-менеджер (External Secrets Operator / Vault).

## Ключевые параметры

| Параметр                              | Назначение                                   | По умолчанию |
|---------------------------------------|----------------------------------------------|--------------|
| `global.registry`                     | Реестр образов                               | `registry.gitlab.com/mchs/emergency-112` |
| `global.imageTag`                     | Тег образов (обычно SHA/`stable`)            | `latest`     |
| `defaults.replicas`                   | Реплики по умолчанию                          | `2`          |
| `defaults.autoscaling.enabled`        | Включить HPA для всех сервисов                | `false`      |
| `ingress.enabled`                     | Создавать Ingress                            | `true`       |
| `ingress.hosts.dispatcher` / `.api`   | Хосты АРМ и API                              | `dispatch.112.by` / `api.112.by` |
| `backup.enabled`                      | CronJob `pg_dump`                            | `true`       |
| `backup.schedule`                     | Расписание бэкапа (cron)                      | `0 2 * * *`  |
| `backup.retentionDays`                | Хранение бэкапов, дней                        | `14`         |

Автоскейлинг и ресурсы можно переопределить по каждому сервису через
`services.<name>.autoscaling` и `services.<name>.resources`.

## Проверка рендера

```bash
helm lint helm/emergency-112
helm template emergency-112 helm/emergency-112 -f helm/emergency-112/values-production.yaml
```
