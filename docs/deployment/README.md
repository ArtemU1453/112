# Раздел документации: deployment

Руководства по сборке, запуску и развёртыванию ЕАСУР в локальном (Docker Compose) и
кластерном (Kubernetes/Helm) окружениях. Секретов в репозитории нет — только заглушки;
реальные значения поставляются через переменные окружения / секрет-менеджер (ADR-011).

## Руководства

| Документ | Назначение |
|----------|-----------|
| [Local Development Guide](local-development-guide.md) | Локальный запуск инфраструктуры и сервисов для разработки |
| [Docker Guide](docker-guide.md) | Compose-файлы, образы, сети/тома/healthcheck, локальный стек |
| [Kubernetes Guide](kubernetes-guide.md) | Платформенный слой (Kustomize) и порядок развёртывания «с нуля» |
| [Helm Guide](helm-guide.md) | Развёртывание прикладных сервисов чартом `helm/emergency-112` |
| [Troubleshooting Guide](troubleshooting-guide.md) | Диагностика проблем локального и кластерного развёртывания |

## Архитектура развёртывания

- [Deployment Architecture](deployment-architecture.md) — конвейер CI/CD, окружения, стратегия
  выката, откат, артефакты и проверки после развёртывания.

## Скрипты

| Скрипт | Назначение |
|--------|-----------|
| `scripts/run-local.sh` | Запуск полного локального стека (Docker Compose) |
| `scripts/reset-environment.sh` | Полный сброс локального окружения (с удалением volumes) |
| `scripts/deploy-dev.sh` | Развёртывание в окружение DEV (Helm) |
| `scripts/deploy-stage.sh` | Развёртывание в окружение STAGE (Helm) |
| `scripts/deploy-prod.sh` | Развёртывание в PROD (Helm, атомарное, с подтверждением) |
