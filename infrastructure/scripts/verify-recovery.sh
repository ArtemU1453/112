#!/usr/bin/env bash
# ЕАСУР — регулярная проверка восстановления из резервной копии (Backup Policy, DR Plan).
# Восстанавливает дамп во временную БД и проверяет целостность. Не трогает production.
set -euo pipefail
DUMP="${1:?Укажите путь к дампу (.dump)}"
TMP_DB="restore_check_$(date +%s)"
echo "[verify-recovery] проверка восстановления: $DUMP -> $TMP_DB"
createdb "$TMP_DB"
trap 'dropdb --if-exists "$TMP_DB"' EXIT
pg_restore --clean --if-exists --no-owner --dbname="$TMP_DB" "$DUMP"
TABLES=$(psql -tAc "SELECT count(*) FROM information_schema.tables WHERE table_schema='public'" "$TMP_DB")
echo "[verify-recovery] восстановлено таблиц: $TABLES"
[ "$TABLES" -gt 0 ] || { echo "[verify-recovery] ОШИБКА: нет таблиц после восстановления"; exit 1; }
echo "[verify-recovery] OK"
