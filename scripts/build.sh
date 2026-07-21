#!/usr/bin/env bash
# ЕАСУР — сборка всех компонентов.
set -euo pipefail
cd "$(dirname "$0")/.."
for svc in services/*/; do
  if [ -f "${svc}pom.xml" ]; then
    echo "[build] ${svc}"
    (cd "$svc" && mvn -B -ntp clean test-compile)
  fi
done
if [ -d frontend/dispatcher ]; then
  echo "[build] frontend/dispatcher"
  (cd frontend/dispatcher && npm ci && npm run build)
fi
echo "[build] Готово."
