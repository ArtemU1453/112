# Monitoring Platform — ЕАСУР

- **Status:** APPROVED · Stage 3 · Подчиняется Observability Architecture.

## Сбор метрик
- Prometheus (`CAP-OBS-001`): системные метрики (узлы/кластер) + прикладные
  (`/actuator/prometheus`, ai-service). Конфигурация — `infrastructure/prometheus`.

## SLA / SLO
- Доступность ядра ≥ 99.9% (Ц6); SLO латентности назначения наряда (Ц1).
- Контроль соблюдения — Availability Management (POF).

## Алерты
Недоступность (`up==0`), рост 5xx, латентность p99, Kafka consumer lag, пул соединений Hikari,
приближение к квотам, сбой бэкапа. Маршрутизация — по ролям (POF).

## Дашборды
Grafana (`CAP-OBS-003`): системные и прикладные панели, единый обзор Metrics/Logs/Traces.

## Автономность
Работает внутри контура без внешних зависимостей (AGSDS).
