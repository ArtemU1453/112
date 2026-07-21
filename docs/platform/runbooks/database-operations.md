# Runbook — Операции с базой данных

- **Подчиняется:** Database Governance Standard, Backup Policy.

## Проверка состояния
```bash
kubectl -n emergency-112-data get pods -l app=postgres
psql -c "SELECT count(*) FROM pg_stat_activity;"        # активные подключения
psql -c "SELECT * FROM pg_stat_replication;"            # состояние репликации
```

## Симптомы и действия

| Симптом | Диагностика | Действия |
|---------|-------------|----------|
| Исчерпание подключений | `pg_stat_activity` | Проверить пулы Hikari; ограничить; масштабировать |
| Отставание репликации | `pg_stat_replication` | Проверить сеть/нагрузку реплики |
| Медленные запросы | `pg_stat_statements` | Проверить индексы (DGS); план запроса |
| Падение при старте сервиса | Liquibase `validate` | Сверить схему с миграциями (Database First) |

## Миграции
Только через Liquibase (`ddl-auto: validate`). Стратегия несовместимых изменений —
expand → migrate → contract (DGS). Проверка на Staging до Production (EGS).

## Резервирование/репликация
Реплики для чтения/отказоустойчивости; бэкапы — Backup Policy; восстановление — backup-restore runbook.
