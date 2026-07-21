# Integration Monitoring Guide — ЕАСУР

- **Status:** APPROVED · Stage 9 · Подчиняется Observability Architecture (Stage 3), Integration
  SLA Standard.

## Наблюдаемость интеграций (9.7)
Показатели по каналам/внешним системам:
- **состояние каналов** (up/down, mTLS-статус);
- **время ответа** (латентность p50/p95/p99);
- **количество ошибок** (по кодам/типам);
- **повторные передачи** (retries);
- **потерянные сообщения** (undelivered/DLQ);
- **нагрузка** (RPS/объём);
- **SLA** (соответствие целям Integration SLA Standard);
- **оповещения** (алерты на деградацию/нарушение SLA/рост ошибок/потери).

## Реализация
Метрики — Prometheus; логи — Loki; трассы — OpenTelemetry/Tempo (ADR-012); дашборды — Grafana
(поверх Stage 3). Каждый обмен трассируется (`correlationId`/traceId); аудит — Cross-System Audit
Standard.

## Правила
Полная наблюдаемость интеграций обязательна (проверка этапа); потери сообщений отслеживаются и
расследуются (Message Reliability Standard).

## Definition of Done
Определены показатели каналов/латентности/ошибок/повторов/потерь/нагрузки/SLA/алертов и реализация.
