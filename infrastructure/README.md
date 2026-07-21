# infrastructure/ — платформенная инфраструктура ЕАСУР

Инфраструктура как код (IaC) и конфигурация платформы. Реализует Stage 3 (Platform
Infrastructure) в соответствии с Platform Architecture и Infrastructure Architecture.

## Состав

| Каталог | Назначение | Ссылка |
|---------|-----------|--------|
| `terraform/` | Декларативное управление платформенным слоем Kubernetes (провайдеры `kubernetes`/`helm`): namespaces, RBAC, квоты, сетевые политики, storage classes, платформенные чарты | ADR-010 |
| `ansible/` | Подготовка узлов/ОС, контейнерный рантайм, узлы Kubernetes, локальный реестр/зеркала, офлайн-обновления | ADR-010, AGSDS |
| `network/` | Сетевые политики и сегментация (K8s NetworkPolicy, egress/ingress-правила) | Network Architecture |
| `storage/` | StorageClass и политики томов/бэкапов | Infrastructure Architecture |
| `scripts/` | Платформенные скрипты (bootstrap, проверка восстановления, офлайн-пакеты) | Operational Procedures |
| `postgres/`, `keycloak/`, `prometheus/`, `grafana/`, `loki/`, `promtail/`, `backup/` | Существующая инфраструктурная конфигурация (инициализация БД, IdP realm, мониторинг, бэкап) | — |

## Оркестрация и приложения

- Kubernetes-манифесты платформы — `../kubernetes/platform/` (Kustomize); базовые/оверлеи — `../kubernetes/`.
- Helm-чарт приложений — `../helm/emergency-112/`.
- Данное разделение зафиксировано Repository Governance Standard (без дублирования каталогов).

## Принципы

- Всё воспроизводимо и версионируемо (Constitution ARTICLE 3, EGS).
- Секреты не хранятся в коде — только ссылки/шаблоны (ADR-011, Security Architecture).
- Конкретные версии инструментов — в каталоге версий (`../config/toolchain/versions.yaml`, TVMS).
- Поддержка изолированного (air-gapped) развёртывания (AGSDS).
