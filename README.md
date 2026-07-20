# Система-112 Республики Беларусь — платформа диспетчеризации экстренных служб

Промышленная микросервисная система приёма и обработки экстренных вызовов (МЧС / 112):
приём вызова, ИИ-анализ речи, классификация происшествия, геопривязка, диспетчеризация
подразделений, уведомления, аудит и мониторинг в реальном времени.

## Архитектура

```
 Гражданин ──► Телефония (SIP/PSTN) ──► telephony-service ──► ai-service (Whisper/NER)
                                              │ Kafka
 Диспетчер ──► frontend-dispatcher ──► gateway-service ──► incident-service ──► dispatch-service
                       ▲  WebSocket                            │ Kafka             │ Kafka
                       └──────────── realtime-service ◄────────┴───────────────────┤
                                                                                   ▼
                                        audit-service ◄── Kafka ──► notification-service
                                              gis-service (PostGIS, маршруты, зоны выезда)
```

| Сервис | Порт | Назначение |
|---|---|---|
| gateway-service | 8080 | API Gateway (Spring Cloud Gateway), маршрутизация, JWT |
| auth-service | 8081 | Управление пользователями/ролями поверх Keycloak |
| incident-service | 8082 | Карточки происшествий (ядро домена, CQRS) |
| dispatch-service | 8083 | Подразделения, наряды, назначения |
| telephony-service | 8084 | Регистрация вызовов, привязка записей разговоров |
| gis-service | 8085 | Геокодирование, PostGIS, зоны ответственности |
| audit-service | 8086 | Журнал аудита всех событий (Kafka consumer) |
| notification-service | 8087 | SMS/Push/E-mail уведомления |
| realtime-service | 8088 | WebSocket-трансляция событий диспетчерам |
| ai-service | 8090 | FastAPI: транскрибация, NER, классификация, рекомендации |
| frontend-dispatcher | 3000 | АРМ диспетчера (React 19 + OpenLayers) |

Инфраструктура: PostgreSQL 17 + PostGIS, Kafka, Redis, Keycloak, Prometheus, Grafana, Loki.

## Быстрый старт (Docker Compose)

```bash
docker compose up -d --build
# АРМ диспетчера:  http://localhost:3000
# Keycloak:        http://localhost:8180 (admin/admin)
# Grafana:         http://localhost:3001 (admin/admin)
# OpenAPI шлюза:   http://localhost:8080/swagger-ui.html
```

## Kubernetes

```bash
helm dependency update helm/emergency-112
helm install emergency-112 helm/emergency-112 -n emergency --create-namespace
```

## Kafka-топики

| Топик | Producer | Consumers |
|---|---|---|
| `incident.created` | incident-service | dispatch, realtime, audit, notification |
| `incident.updated` | incident-service | realtime, audit |
| `dispatch.assigned` | dispatch-service | incident, realtime, audit, notification |
| `unit.status-changed` | dispatch-service | realtime, audit |
| `call.received` | telephony-service | ai-service, audit |
| `call.analyzed` | ai-service | telephony, incident, realtime, audit |
| `notification.requested` | * | notification-service |
| `audit.events` | * | audit-service |

## Роли (RBAC, Keycloak realm `emergency-112`)

`ROLE_DISPATCHER` — приём и ведение карточек; `ROLE_SENIOR_DISPATCHER` — назначение нарядов,
закрытие; `ROLE_ADMIN` — администрирование; `ROLE_CREW` — мобильный экипаж; `ROLE_ANALYST` — чтение.

## Сборка и тесты

```bash
for s in services/*; do (cd "$s" && mvn -q verify); done   # unit + integration (Testcontainers)
cd ai-service && pip install -r requirements.txt && pytest
cd frontend/dispatcher && npm ci && npm run build
```

## Резервное копирование

`infrastructure/backup/backup.sh` — pg_dump всех БД в S3/каталог, `restore.sh` — восстановление.
CronJob для Kubernetes — `helm/emergency-112/templates/backup-cronjob.yaml`.

## Документация

- `docs/architecture.md` — архитектурные решения (DDD, CQRS, hexagonal)
- `docs/operations.md` — эксплуатация, мониторинг, алерты
- README в каталоге каждого сервиса
