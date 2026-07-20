#!/usr/bin/env bash
# Резервное копирование всех БД системы 112.
# Использование: PGHOST=... PGUSER=e112 PGPASSWORD=... ./backup.sh /backups
set -euo pipefail
DEST="${1:-/backups}"
STAMP="$(date +%Y%m%d_%H%M%S)"
DATABASES=(incident_db dispatch_db telephony_db gis_db audit_db notification_db auth_db keycloak)
mkdir -p "$DEST/$STAMP"
for db in "${DATABASES[@]}"; do
  echo "backup: $db"
  pg_dump --format=custom --compress=6 --file="$DEST/$STAMP/$db.dump" "$db"
done
# Удержание: 14 суток
find "$DEST" -mindepth 1 -maxdepth 1 -type d -mtime +14 -exec rm -rf {} +
echo "backup completed: $DEST/$STAMP"
