# dispatch-service

Управление оперативными подразделениями и нарядами системы 112.

- Порт: **8083**, БД: `dispatch_db`
- Подбор ближайшего свободного наряда (формула гаверсинусов) — `POST /api/v1/assignments/auto`
- Статусная модель подразделения: AVAILABLE → DISPATCHED → EN_ROUTE → ON_SCENE → RETURNING → AVAILABLE
- Kafka consumer: `incident.created` (автодиспетчеризация для CRITICAL/HIGH)
- Kafka producer: `dispatch.assigned`, `unit.status-changed`, `audit.events`
- Оптимистическая блокировка подразделения (`@Version`)

## Тесты

`mvn verify` — unit-тесты домена и сервиса + интеграционный тест (Testcontainers PostgreSQL).
