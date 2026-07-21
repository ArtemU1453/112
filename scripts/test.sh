#!/usr/bin/env bash
# ЕАСУР — прогон тестов (Testing Strategy).
set -euo pipefail
cd "$(dirname "$0")/.."
for svc in services/*/; do
  if [ -f "${svc}pom.xml" ]; then
    echo "[test] ${svc}"
    (cd "$svc" && mvn -B -ntp verify)
  fi
done
if [ -d ai-service ]; then
  echo "[test] ai-service"
  (cd ai-service && pytest -q)
fi
echo "[test] Готово."
