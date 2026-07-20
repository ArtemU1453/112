# auth-service

Управление сотрудниками службы 112 поверх Keycloak: создание учётных записей,
назначение ролей RBAC, блокировка, локальные профили (подразделение, табельный номер).

- Порт: **8081**, БД: `auth_db`, Kafka producer: `audit.events`
- Роли: операции записи — `ROLE_ADMIN`; чтение — `ROLE_ADMIN`, `ROLE_SENIOR_DISPATCHER`, `ROLE_ANALYST`
- OpenAPI: `GET /swagger-ui.html`

## Запуск

```bash
mvn spring-boot:run   # требуется Postgres, Kafka, Keycloak (docker compose up postgres kafka keycloak)
```

## Тесты

```bash
mvn verify
```
