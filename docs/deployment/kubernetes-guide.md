# Kubernetes Guide — ЕАСУР

- **Status:** APPROVED · Развёртывание платформенного слоя и прикладных сервисов в Kubernetes
  (Deployment Architecture, Environment Governance Standard, ADR-005). Секретов в git нет.

## 1. Разделение ответственности

| Слой | Инструмент | Каталог |
|------|-----------|---------|
| Платформенные ресурсы (namespaces, RBAC, сети, квоты, storage, secret store) | Kustomize | `kubernetes/platform`, `kubernetes/base`, `kubernetes/overlays/<env>` |
| Прикладные сервисы (Deployment/Service/Ingress/HPA/CronJob) | Helm | `helm/emergency-112` (см. [Helm Guide](helm-guide.md)) |

Kustomize задаёт окружение; Helm разворачивает приложения поверх подготовленного окружения.

## 2. Порядок развёртывания «с нуля»

Полный порядок — [Deployment Architecture](deployment-architecture.md), §2. Кратко:

1. Узлы/ОС/рантайм/реестр — Ansible (`infrastructure/ansible/site.yml`).
2. Кластер Kubernetes — Terraform (`infrastructure/terraform/environments/<env>`).
3. Платформенные манифесты — Kustomize:
   ```
   kubectl apply -k kubernetes/platform
   kubectl apply -k kubernetes/overlays/<development|test|stage|production>
   ```
4. Платформенные компоненты: ingress-nginx, cert-manager, External Secrets + Vault,
   Prometheus/Grafana/Loki, объектное хранилище.
5. Приложения — Helm-чарт `helm/emergency-112` (см. [Helm Guide](helm-guide.md)).

Автоматизация шагов 1–4 — `infrastructure/scripts/platform-bootstrap.sh`.

## 3. Окружения и оверлеи

`kubernetes/overlays/{development,test,stage,production}` — отличия сводятся к масштабу,
ресурсам, endpoint'ам и feature flags. Версии, схемы БД и API-контракты во всех окружениях
идентичны (EGS «Синхронизация»).

Развёртывание приложений по окружениям — скрипты `scripts/deploy-{dev,stage,prod}.sh`.

## 4. Секреты

В кластере секреты поставляются через External Secrets Operator из Vault
(`kubernetes/platform/config/secret-store.yaml`, ADR-011). В git — только заглушки
(`helm/emergency-112/templates/secrets.yaml`). См. [Security Standards](../standards/security-standards.md).

## 5. Проверки после развёртывания

```
kubectl -n <namespace> get pods,svc,ingress
kubectl -n <namespace> rollout status deploy/<service>
```
Health/readiness всех подов, Prometheus targets, smoke-проверки — см.
[Deployment Architecture](deployment-architecture.md), §6, и Operational Procedures.

## 6. Откат

`helm -n <namespace> rollback <release>` (см. [Helm Guide](helm-guide.md)) либо повторное
применение предыдущего Kustomize-оверлея. Критерии и шаги — Release Governance Standard, Runbooks.

## 7. Связанные документы

[Helm Guide](helm-guide.md) · [Deployment Architecture](deployment-architecture.md) ·
[Docker Guide](docker-guide.md) · [Troubleshooting Guide](troubleshooting-guide.md) ·
`kubernetes/README.md` · `kubernetes/platform/README.md`.
