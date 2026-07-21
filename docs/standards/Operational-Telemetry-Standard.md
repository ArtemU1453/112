# Operational Telemetry Standard — ЕАСУР

- **Status:** APPROVED · Stage 4 · Согласован с Observability Architecture, Logging Standards,
  ADR-012.

## Цель
Единые требования к телеметрии сервисов: метрики, логи, трассы, события, health — через SDK
(единообразно во всех сервисах).

## Метрики
Каждый сервис экспонирует технические (RED/USE: rate/errors/duration; utilization/saturation/errors)
и прикладные метрики (`/actuator/prometheus`). Именование метрик — единое.

## Логи
Структурированные JSON-логи (Logging Standards) с `correlationId`/`traceId`/`spanId`/`service`
(`LogFields`); без ПДн/секретов.

## Трассы
OpenTelemetry (ADR-012); распространение W3C Trace Context (HTTP/Kafka); связка логов и трасс по
`traceId`.

## События и health
Kubernetes/бизнес-события; liveness/readiness пробы; согласованность со стандартом наблюдаемости.

## Ключевые показатели
MTTD, MTTR, доступность, доля успешных бэкапов/восстановлений, consumer lag/DLQ rate, соответствие
SLO (Platform Operations Framework, Observability Architecture).

## Обязательность (через SDK)
Телеметрия предоставляется SDK (`PlatformModule` MONITORING/TRACING/LOGGING); сервис без телеметрии
считается незрелым (Service Maturity Model).

## Definition of Done
Определены требования к метрикам/логам/трассам/событиям/health и ключевые показатели; телеметрия
единообразна через SDK.
