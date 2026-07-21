# infrastructure/terraform — платформенный слой Kubernetes (ADR-010)

Terraform управляет платформенным слоем кластера через провайдеры `kubernetes` и `helm`:
namespaces (с метками Pod Security), ResourceQuota, LimitRange, RBAC-заготовки, NetworkPolicy,
StorageClass. Провайдер-агностично к облаку — управляются ресурсы кластера.

## Использование
```bash
cd infrastructure/terraform/environments/<env>
terraform init            # провайдеры кэшируются локально (air-gapped: из локального зеркала)
terraform plan
terraform apply
```
kubeconfig и backend состояния предоставляются окружением и НЕ хранятся в git (EGS, ADR-011).

## Структура
- `modules/kubernetes-platform` — namespaces, квоты, limit ranges, pod-security-метки.
- `modules/network` — сетевые политики (default-deny + разрешения).
- `modules/storage` — StorageClass.
- `environments/{development,production}` — параметры окружений (EGS).

Версии Terraform/провайдеров — согласно каталогу версий (TVMS).
