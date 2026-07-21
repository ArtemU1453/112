# Observability Architecture — ЕАСУР

- **Status:** APPROVED · Stage 3 · Подчиняется Architecture.md (наблюдаемость), Constitution
  ARTICLE 11, Logging Standards.

Единая наблюдаемость: **Metrics · Logs · Traces · Events · Health Checks** — автономна
(работает без внешних сетей, AGSDS).

## 1. Metrics (CAP-OBS-001)
- Сбор — Prometheus; источники: системные метрики узлов/кластера и прикладные
  `/actuator/prometheus` (Java), `/health`+метрики (ai-service).
- Хранение — локально; удержание по политике окружения (EGS).

## 2. Logs (CAP-OBS-002)
- Структурированные JSON-логи (Logging Standards) → сбор Promtail → Loki.
- Корреляция: `correlationId` (сквозной), `traceId`/`spanId` (связь с трассами).
- Правила хранения/архивирования — Backup Policy / EGS; без ПДн/секретов в логах.

## 3. Traces (CAP-TRACE-001)
- Инструментирование — OpenTelemetry; сбор — OTel Collector; backend — Grafana Tempo (ADR-012).
- Контекст — W3C Trace Context (HTTP) + заголовки Kafka; семплирование настраивается.

## 4. Events
- Доменные события — Kafka (ADR-008); бизнес-аудит — audit-service.
- Kubernetes Events и алерты — источники эксплуатационных событий.

## 5. Health Checks
- Liveness/Readiness пробы всех подов; `/actuator/health/*` (Java), `/health` (ai-service).
- Проверки готовности при развёртывании (Deployment Architecture).

## 6. Визуализация и алерты (CAP-OBS-003)
- Grafana — единая панель Metrics/Logs/Traces (Prometheus/Loki/Tempo).
- Алерты (примеры): недоступность (`up==0`), рост 5xx, латентность p99 (цель Ц1), Kafka consumer
  lag, использование пула соединений, нехватка ресурсов (квоты), сбой бэкапа.

## 7. SLA / SLO
- Целевая доступность ядра ≥ 99.9% (Vision Ц6); SLO по латентности назначения наряда (Ц1).
- Контроль соблюдения — Availability Management (Platform Operations Framework).
- Ключевые метрики эксплуатации: MTTD, MTTR, доля успешных бэкапов/восстановлений, соответствие SLO.

## 8. Автономность (AGSDS)
Весь стек наблюдаемости работает внутри контура; передача во внешние системы не является
условием функционирования.

## 9. Связанные документы
Logging Standards · Platform/Deployment Architecture · Platform Operations Framework ·
Air-Gapped Standard · operations.md.
