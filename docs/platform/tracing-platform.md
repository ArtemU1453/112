# Distributed Tracing Platform — ЕАСУР

- **Status:** APPROVED · Stage 3 · ADR-012 · Подчиняется Observability Architecture.

## Инструментирование и сбор
OpenTelemetry (`CAP-TRACE-001`) в сервисах → OpenTelemetry Collector → Grafana Tempo.

## Распространение контекста
W3C Trace Context (HTTP-заголовки) + заголовки сообщений Kafka. `traceId`/`spanId` попадают в
логи (связка логов и трасс).

## Использование
Сквозной разбор сценария «вызов → карточка → наряд → реальное время»; анализ латентности (Ц1);
локализация узких мест и сбоев между сервисами.

## Настройка
Семплирование и удержание трасс — по нагрузке/политике; всё self-hosted (AGSDS).
