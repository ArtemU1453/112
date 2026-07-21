# Runbook — Резервное копирование и восстановление

- **Подчиняется:** Backup Policy, Disaster Recovery Plan.

## Проверка бэкапов
```bash
kubectl -n emergency-112 get cronjob            # CronJob бэкапа активен
kubectl -n emergency-112 get jobs | tail        # последние запуски
```

## Ручной бэкап
```bash
PGHOST=postgres PGUSER=e112 PGPASSWORD=*** ./infrastructure/backup/backup.sh /backups
```

## Восстановление
```bash
pg_restore --clean --if-exists --no-owner --dbname="$DB" /backups/<STAMP>/<db>.dump
```

## Проверка восстановления (регулярно)
```bash
./infrastructure/scripts/verify-recovery.sh /backups/<STAMP>/incident_db.dump
```
Восстанавливает во временную БД и проверяет целостность; сбой — инцидент (POF).

## Цели
RPO ≤ 24 ч, RTO ≤ 4 ч (Backup Policy/DR Plan). Хранилище — локальное S3-совместимое (ADR-013), без внешних облаков (AGSDS).
