# infrastructure/network — сетевая архитектура и сегментация

Реализация Network Architecture (`docs/platform/network-architecture.md`). Сетевые политики
Kubernetes применяются как манифесты (`../../kubernetes/platform/networking`) и через Terraform
(default-deny). Данный каталог хранит эталонные политики сегментации и egress/ingress-правила.

## Принципы
- Сегментация по зонам доверия: внешняя (Ingress), прикладная (сервисы), данных (БД/брокер/кэш),
  платформенная (мониторинг, секреты).
- Модель «запрет по умолчанию» (default-deny) + явные разрешения (least privilege).
- Egress ограничивается; в air-gapped внешний egress отсутствует (AGSDS).
