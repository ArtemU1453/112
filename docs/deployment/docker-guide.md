# Docker Guide — ЕАСУР

- **Status:** APPROVED · Compose-файлы, образы и правила локального контейнерного окружения
  (Environment Governance Standard, ADR-005). Секреты в compose-файлах не хранятся.

## 1. Состав Compose-файлов
| Файл | Назначение |
|------|-----------|
| `docker-compose.yml` (корень) | Полный стек системы (инфраструктура + прикладные сервисы) |
| `docker/compose.dev.yml` | Инфраструктура для локальной разработки (БД, брокер, кэш, IdP) |
| `docker/compose.test.yml` | Изолированная инфраструктура для интеграционных тестов |
| `docker/compose.monitoring.yml` | Локальный стек наблюдаемости (метрики/логи) |
| `docker/compose.tools.yml` | Инструменты разработчика (линтеры/сканеры) |

## 2. Запуск и остановка
```
docker compose -f docker-compose.yml up -d        # запуск (или scripts/run-local.sh)
docker compose -f docker-compose.yml ps           # состояние
docker compose -f docker-compose.yml logs -f <s>  # логи сервиса
docker compose -f docker-compose.yml down         # остановка
scripts/reset-environment.sh                       # полный сброс (с volumes)
```

## 3. Образы сервисов
Каждый сервис содержит `Dockerfile` (multi-stage: сборка → минимальный JRE-рантайм, запуск
не-root). Сборка образов — в CI (`.github/workflows/docker-build.yml`) с публикацией в реестр;
локально образы собираются `docker compose build`.

## 4. Сети, тома, healthcheck
- Изолированная сеть Compose; сервисы обращаются друг к другу по именам.
- Именованные volumes для БД/брокера/хранилища (сохранность между перезапусками; сбрасываются
  `reset-environment.sh`).
- Healthcheck и порядок старта (`depends_on: condition: service_healthy`) обеспечивают готовность
  зависимостей до старта прикладных сервисов.

## 5. Секреты
Значения передаются через переменные окружения/`.env` (см. `environments/*.env.example`); в
продуктиве — через секрет-менеджер (ADR-011). Секретов в git нет ([Security Standards](../standards/security-standards.md)).

## 6. Связанные документы
[Local Development Guide](local-development-guide.md) · [Kubernetes Guide](kubernetes-guide.md) ·
[Infrastructure Architecture](../platform/infrastructure-architecture.md).
