# kubernetes/platform — платформенный слой Kubernetes (Stage 3)

Манифесты платформы: namespaces (Pod Security Admission), RBAC + service accounts (IAM),
network policies (default-deny + разрешения), resource quotas / limit ranges, storage classes,
platform ConfigMap, подключение к секретам (External Secrets → Vault, ADR-011), IngressClass.

## Применение
```bash
kubectl apply -k kubernetes/platform
```
Часть ресурсов (SecretStore/ExternalSecret, IngressClass) требует установленных операторов
(External Secrets Operator, ingress-nginx) — устанавливаются на этапе развёртывания платформы
(см. docs/platform/deployment-architecture.md).

## Соответствие
- Pod Security, RBAC, NetworkPolicy — Platform Security / Security Architecture.
- Квоты/LimitRange — Capacity/Availability Management (Platform Operations Framework).
- Секреты — ADR-011; РЕАЛЬНЫЕ ЗНАЧЕНИЯ В GIT НЕ ХРАНЯТСЯ (RGS, EGS).
