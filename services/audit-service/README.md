# audit-service

Централизованный журнал аудита системы 112.

- Порт: **8086**, БД: `audit_db`
- Единый Kafka consumer топика `audit.events` — принимает события всех сервисов
- Хранение `details` в JSONB с GIN-индексом
- REST-поиск с фильтрами (сервис, действие, сущность, актор, период) и пагинацией
- Доступ: `ROLE_ADMIN`, `ROLE_ANALYST`, `ROLE_SENIOR_DISPATCHER`

## Тесты

`mvn verify` — IT на Testcontainers PostgreSQL (запись из consumer + поиск).
