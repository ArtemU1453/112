# Logging Platform — ЕАСУР

- **Status:** APPROVED · Stage 3 · Подчиняется Logging Standards, Observability Architecture.

## Централизованный сбор
Promtail → Loki (`CAP-OBS-002`). Логи пишутся в stdout/stderr контейнеров.

## Структурированный формат
JSON (Logging Standards): `timestamp` (UTC ISO-8601), `level`, `service`, `correlationId`,
`traceId`/`spanId`, `message`. Без ПДн/секретов (маскирование).

## Корреляционные идентификаторы
`correlationId` присваивается на Gateway и пробрасывается по HTTP/Kafka; `traceId` связывает логи
с трассами (Tempo).

## Хранение и архивирование
Удержание по окружению (EGS); архивирование в объектное хранилище (ADR-013). Правовые сроки — по
регламенту.

## Автономность
Функционирует без внешних систем (AGSDS).
