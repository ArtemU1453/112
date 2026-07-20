#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR=$(cd "$(dirname "$0")/.." && pwd)
cd "$ROOT_DIR/.."

echo "Starting docker-compose..."
docker-compose -f docker-compose.yml up -d --build

echo "Waiting for services to become healthy..."
sleep 20

HEALTH_URLS=(
  "http://localhost:8080/actuator/health"
  "http://localhost:8080/health"
)

for url in "${HEALTH_URLS[@]}"; do
  echo "Checking $url"
  if ! curl -fsS "$url" >/dev/null; then
    echo "Health check failed for $url"
    docker-compose -f docker-compose.yml logs --no-color
    exit 1
  fi
done

echo "Smoke tests passed. Tearing down..."
docker-compose -f docker-compose.yml down
