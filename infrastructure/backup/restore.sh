#!/usr/bin/env bash
# Восстановление БД из каталога бэкапа.
# Использование: PGHOST=... PGUSER=e112 PGPASSWORD=... ./restore.sh /backups/20260720_120000 [db]
set -euo pipefail
SRC="${1:?укажите каталог бэкапа}"
ONLY="${2:-}"
for dump in "$SRC"/*.dump; do
  db="$(basename "$dump" .dump)"
  if [[ -n "$ONLY" && "$db" != "$ONLY" ]]; then continue; fi
  echo "restore: $db"
  dropdb --if-exists "$db"
  createdb "$db"
  pg_restore --dbname="$db" --no-owner "$dump"
done
echo "restore completed"
