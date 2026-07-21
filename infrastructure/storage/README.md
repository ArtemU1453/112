# infrastructure/storage — хранилище и политики томов

Реализация раздела Storage в Infrastructure Architecture. StorageClass'ы применяются через
Terraform (`../terraform/modules/storage`) и/или манифесты (`../../kubernetes/platform/storage`).

## Классы хранения (роли)
- `easur-standard` — общий класс для stateful-нагрузок (WaitForFirstConsumer, Retain, расширяемый).
- `easur-fast` — низколатентный класс для БД (при наличии соответствующего backend).
- Объектное хранилище (S3-совместимое, ADR-013) — для бэкапов/артефактов/зеркал.

## Политики
- reclaimPolicy=Retain для данных (защита от потери при удалении PVC).
- allowVolumeExpansion=true.
- Резервное копирование томов согласовано с Backup Policy.
