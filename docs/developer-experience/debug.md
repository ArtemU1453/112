# Debug Guide — ЕАСУР

## Java-сервисы
- Локальный запуск с отладкой: `mvn spring-boot:run` или запуск из IDE с точками останова.
- Удалённая отладка контейнера: включить JDWP-агент через переменную окружения и опубликовать
  порт отладки (конфигурация — `docker/compose.dev.yml`, профиль отладки).
- VS Code: конфигурации в `.vscode/launch.json`.

## Frontend
- `npm run dev` (Vite) с HMR; отладка в браузере (source maps) и через VS Code launch-конфиг.

## ai-service (Python)
- `uvicorn app.main:app --reload`; отладчик Python (debugpy) через VS Code launch-конфиг.

## Наблюдаемость при отладке
- Структурированные логи (Logging Standards), корреляция по `correlationId`.
- Метрики — `/actuator/prometheus` (Java) и `/health` (ai-service).
- Локальный мониторинг — `docker/compose.monitoring.yml`.

## Диагностика инфраструктуры
- Состояние контейнеров: `docker compose -f docker/compose.dev.yml ps`.
- Логи сервиса: `docker compose -f docker/compose.dev.yml logs -f <service>`.
- Частые проблемы (401/403, отсутствие событий, миграции) — см. Runbook-шаблон и `../operations.md`.
