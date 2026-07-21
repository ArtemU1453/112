# Network Architecture — ЕАСУР

- **Status:** APPROVED · Stage 3 · Подчиняется Platform Architecture, Security Architecture.

## 1. Зоны доверия (сегментация)

| Зона | Состав | Вход | Выход |
|------|--------|------|-------|
| edge | Ingress-контроллер, API Gateway | внешние клиенты | application |
| application | Прикладные сервисы (bounded contexts) | edge, application | data, platform, application |
| data | PostgreSQL/PostGIS, Kafka, Redis, объектное хранилище | application, platform | data |
| platform | Vault, External Secrets, мониторинг/логи/трассы | application, data, platform | platform, data |

Эталон — `infrastructure/network/segmentation.yaml`; реализация — NetworkPolicy в
`kubernetes/platform/networking` и Terraform-модуль `network` (default-deny).

## 2. Внутренние сети
- Внутрикластерная связность — CNI с поддержкой NetworkPolicy.
- Обнаружение сервисов — DNS Kubernetes (Service Discovery); внешний discovery-компонент не
  требуется на текущей Baseline.
- Разрешён DNS-egress; доступ приложений к зоне данных — по портам 5432/9092/6379.

## 3. Внешние сети и периметр
- Единственная точка входа — Ingress (`easur-nginx`, IngressClass) → API Gateway (ADR — gateway).
- TLS-терминация на Ingress; сертификаты — cert-manager (TVMS).
- Внешние домены: АРМ (`dispatch.112.by`), API (`api.112.by`) — Helm ingress приложений.

## 4. Firewall policy (default-deny)
- Каждый namespace: `default-deny` (Ingress+Egress), затем явные разрешения (least privilege).
- Межзонные потоки разрешаются только согласно сегментации.
- Привилегированные потоки (доступ к Vault) — только из авторизованных ServiceAccount.

## 5. Egress
- В подключённом контуре — ограниченный egress (только необходимые endpoint'ы).
- В **air-gapped** — внешний egress запрещён (AGSDS); все зависимости из локальных зеркал.

## 6. Наблюдаемость сети
Метрики сетевых политик/подключений; журналирование отказов доступа (Security журнал);
алерты на аномалии (Observability Architecture).

## 7. Связанные документы
Security Architecture · Platform/Infrastructure Architecture · Air-Gapped Standard ·
`infrastructure/network/`, `kubernetes/platform/networking`.
