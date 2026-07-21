# analytics-service

Аналитические витрины, KPI и отчётность системы ЕАСУР. **Read-oriented** сервис: наполняется из
интеграционных событий (Kafka) в собственную витрину (PostgreSQL) и предоставляет только чтение —
не влияет на оперативный контур (incident/dispatch и др.). Соответствует ADR-002 (микросервисы),
контрактам Stage 8 (operational-analytics/kpi/reporting) и стандартам проекта.

- **Bounded context:** аналитика (read model, CQRS read-side).
- **Порт:** 8089. **БД:** `analytics_db`. **Стек:** Java 21, Spring Boot 3.3, JPA, Liquibase, Kafka.

## Источники данных (Kafka)
| Топик | Публикует | Витрина |
|-------|-----------|---------|
| `incident.created`, `incident.updated` | incident-service | `analytics_incident` (upsert по `incidentId`, last-write-wins по времени события) |
| `dispatch.assigned` | dispatch-service | `analytics_dispatch` (вставка по `assignmentId` — идемпотентно) |

Приём событий идемпотентен: повторная доставка не искажает показатели. Ошибки разбора одного
сообщения логируются и не роняют потребителя.

## REST API (требует Keycloak JWT)
Базовый путь `/api/v1/analytics`. Период задаётся ISO-8601 (`from` включительно, `to` исключительно).

| Метод | Путь | Назначение |
|-------|------|-----------|
| GET | `/kpi/summary?from&to` | Сводка KPI: всего/с нарядом/решено, доли, ср. время до первого наряда, срезы по типу/приоритету/статусу |
| GET | `/operational/by-type?from&to` | Распределение по типам происшествий |
| GET | `/operational/by-priority?from&to` | Распределение по приоритетам |
| GET | `/operational/by-status?from&to` | Распределение по статусам |
| GET | `/operational/timeseries?from&to&granularity=HOUR\|DAY\|WEEK\|MONTH` | Временной ряд числа происшествий |
| GET | `/reporting/incidents?from&to&type&priority&status&page&size&sort` | Табличный отчёт (пагинация), с числом нарядов и временем первого наряда |

Ошибки — в формате RFC 7807 (ProblemDetail); некорректные параметры → 400.

## Сборка и тесты
```
mvn -q package                 # сборка
mvn -q verify                  # unit + integration (Testcontainers PostgreSQL)
```
- Unit: `EventValuesTest` (толерантный разбор значений событий).
- Integration: `AnalyticsIntegrationIT` — приём событий (включая идемпотентность и устаревшие
  события) и все запросы (KPI/срезы/временной ряд/отчёт) на реальном PostgreSQL.

## Наблюдаемость и безопасность
- Actuator: `/actuator/health`, `/actuator/prometheus`.
- OAuth2 Resource Server (Keycloak, realm `emergency-112`); роли реалма — как authorities.
- Секретов в коде нет; конфигурация — через переменные окружения (Configuration Standards).

## Развёртывание
Helm-чарт `helm/emergency-112` (сервис `analytics-service`, порт 8089, БД `analytics_db`, Kafka,
Keycloak). Схема БД — Liquibase (`db/changelog`).
