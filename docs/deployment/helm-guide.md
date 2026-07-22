# Helm Guide — ЕАСУР

- **Status:** APPROVED · Развёртывание прикладных сервисов зонтичным чартом `helm/emergency-112`
  (Deployment Architecture, ADR-005). Секретов в git нет — только заглушки.

## 1. Что разворачивает чарт

Зонтичный чарт `helm/emergency-112` генерирует `Deployment` + `Service` для каждого прикладного
сервиса, общий `ConfigMap`, `Secret`, `Ingress`, при необходимости `HorizontalPodAutoscaler`
и `CronJob` резервного копирования БД. Полный перечень сервисов и портов — `helm/emergency-112/README.md`.

Инфраструктура (PostgreSQL/PostGIS, Kafka, Redis, Keycloak) чартом **не** разворачивается —
подключается как внешние endpoint'ы через секцию `infrastructure.*` (см. [Kubernetes Guide](kubernetes-guide.md)).

## 2. Файлы значений

| Файл | Назначение |
|------|-----------|
| `helm/emergency-112/values.yaml` | Базовые значения (реестр, теги, сервисы, ingress, backup) |
| `helm/emergency-112/values-production.yaml` | Прод-оверрайды (реплики, ресурсы, HPA, лимиты) |

Ключи `global.registry` и `global.imageTag` согласованы с CI (`.gitlab-ci.yml`,
`.github/workflows`) — образы поставляются по SHA/каналу, `latest` в проде запрещён.

## 3. Установка через скрипты (рекомендуется)

```
IMAGE_TAG=<git-sha|release> REGISTRY=<registry> scripts/deploy-dev.sh
IMAGE_TAG=<git-sha|release> REGISTRY=<registry> scripts/deploy-stage.sh
IMAGE_TAG=<verified-tag>    REGISTRY=<registry> CONFIRM=yes scripts/deploy-prod.sh
```
`deploy-prod.sh` применяет `values-production.yaml`, работает атомарно (`--atomic`,
автоматический откат при неуспехе) и требует подтверждения.

## 4. Установка вручную

```
helm lint helm/emergency-112
helm upgrade --install easur helm/emergency-112 \
  --namespace easur-stage --create-namespace \
  -f helm/emergency-112/values.yaml \
  --set global.registry=$REGISTRY \
  --set global.imageTag=$IMAGE_TAG \
  --atomic --wait
```
Для прода дополнительно `-f helm/emergency-112/values-production.yaml`.

## 5. Секреты

В git `secrets.*` содержат только заглушки. В проде реальные значения передаются через
External Secrets Operator / Vault (ADR-011) либо `--set`/`--values` из защищённого хранилища —
не через файлы в репозитории. См. [Security Standards](../standards/security-standards.md).

## 6. Проверка рендера и состояния

```
helm lint helm/emergency-112
helm template easur helm/emergency-112 -f helm/emergency-112/values-production.yaml
helm -n <namespace> status easur
helm -n <namespace> history easur
```

## 7. Откат

```
helm -n <namespace> rollback easur           # к предыдущему релизу
helm -n <namespace> rollback easur <revision>
```

## 8. Связанные документы

[Kubernetes Guide](kubernetes-guide.md) · [Deployment Architecture](deployment-architecture.md) ·
[Troubleshooting Guide](troubleshooting-guide.md) · `helm/emergency-112/README.md`.
