# incident-service

Ядро домена системы 112: карточки происшествий, статусная модель, классификация,
история изменений, операционная статистика.

- Порт: **8082**, БД: `incident_db` (+pg_trgm), кэш: Redis
- CQRS: `IncidentCommandService` (мутации) / `IncidentQueryService` (чтение, кэш статистики)
- Kafka producer: `incident.created`, `incident.updated`
- Kafka consumer: `dispatch.assigned` (перевод в DISPATCHED), `call.analyzed` (автосоздание карточки от ИИ)
- Статусная модель: RECEIVED → CLASSIFIED → DISPATCHED → IN_PROGRESS → RESOLVED → CLOSED, ветка CANCELLED;
  недопустимые переходы отклоняются с 409
- Оптимистическая блокировка (`@Version`) — конкурентное редактирование двух диспетчеров даёт 409

## Тесты

`mvn verify` — unit-тесты + интеграционные (Testcontainers: PostgreSQL 17 + Kafka).
