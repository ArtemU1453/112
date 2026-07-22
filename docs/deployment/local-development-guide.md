# Local Development Guide — ЕАСУР

- **Status:** APPROVED · Реализует Environment Governance Standard. Локальный запуск инфраструктуры и
  сервисов для разработки. Секреты — только через переменные окружения/`.env` (в коде их нет).

## 1. Предпосылки
- Docker + Docker Compose (см. версии в `config/toolchain/versions.yaml`).
- JDK 21 и Maven — для сборки Java-сервисов; Node LTS — для `frontend/`; Python 3.12 — для `ai-service`.

## 2. Переменные окружения
Скопируйте пример и заполните значения:
```
cp environments/local.env.example .env
```
Профили окружений: `environments/{local,development,test,stage,production}.env.example`
(см. `environments/README.md`).

## 3. Запуск инфраструктуры и сервисов
Полный стек (БД+PostGIS, Redis, Kafka/KRaft, Keycloak, сервисы):
```
scripts/run-local.sh            # docker compose -f docker-compose.yml up -d
```
Только инфраструктура для разработки (без прикладных сервисов):
```
docker compose -f docker/compose.dev.yml up -d
```
Наблюдаемость локально: `docker compose -f docker/compose.monitoring.yml up -d`
(см. [Monitoring Platform](../platform/monitoring-platform.md)).

## 4. Сборка и тесты
```
scripts/build.sh                # сборка сервисов и фронтенда
scripts/test.sh                 # тесты
./scripts/verify.sh             # архитектура/версии/документация
```
Интеграционные тесты используют Testcontainers (нужен Docker) — см. [Testing Strategy](../standards/testing-strategy.md).

## 5. Сброс окружения
```
scripts/reset-environment.sh    # ВНИМАНИЕ: удаляет контейнеры и volumes (данные)
```

## 6. Связанные документы
[Docker Guide](docker-guide.md) · [Infrastructure Architecture](../platform/infrastructure-architecture.md) ·
[Deployment Architecture](deployment-architecture.md) · [Troubleshooting Guide](troubleshooting-guide.md).
