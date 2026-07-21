# Local Development Guide — ЕАСУР

## Среда
Локальная разработка ведётся в Dev Container либо с локальным Docker. Инфраструктура
(PostgreSQL/PostGIS, Kafka, Redis, Keycloak) поднимается через `docker/compose.dev.yml`.

## Типовой цикл
```bash
./scripts/bootstrap.sh                 # инфраструктура разработки
# запуск отдельного сервиса (пример, Java)
cd services/incident-service && mvn spring-boot:run
# frontend
cd frontend/dispatcher && npm run dev
# ai-service
cd ai-service && uvicorn app.main:app --reload
```

## Конфигурация
Параметры — через переменные окружения (`environments/`, Configuration Standards). Значения по
умолчанию рассчитаны на локальную инфраструктуру из `docker/compose.dev.yml`. Секреты — только
локальные плейсхолдеры, не коммитить.

## Ветки и коммиты
Одна задача — одна ветка (Git Strategy); Conventional Commits. Перед PR — `./scripts/verify.sh`.

## Проверки перед PR
```bash
./scripts/lint.sh        # линтеры (config/quality)
./scripts/test.sh        # тесты
./scripts/verify.sh      # архитектура/версии/документация
```

## Полезное
- Тестовые окружения — `docker/compose.test.yml`; инструменты — `docker/compose.tools.yml`;
  мониторинг — `docker/compose.monitoring.yml`.
- Эксплуатация и мониторинг — `../operations.md`.
