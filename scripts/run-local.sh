#!/usr/bin/env bash
# ЕАСУР — запуск полной локальной инфраструктуры и сервисов через Docker Compose.
# Поднимает корневой стек docker-compose.yml (БД+PostGIS, Redis, Kafka/KRaft, Keycloak,
# сервисы, наблюдаемость — в зависимости от определения файла). Секреты берутся из
# переменных окружения / .env (см. environments/*.env.example), в коде их нет.
set -euo pipefail
cd "$(dirname "$0")/.."

COMPOSE="${COMPOSE:-docker compose}"
COMPOSE_FILE="${COMPOSE_FILE:-docker-compose.yml}"

if ! $COMPOSE version >/dev/null 2>&1; then
  echo "[run-local] Не найден Docker Compose. Установите Docker и повторите." >&2
  exit 1
fi

echo "[run-local] Поднимаем стек из ${COMPOSE_FILE} …"
$COMPOSE -f "$COMPOSE_FILE" up -d --remove-orphans

echo "[run-local] Текущее состояние сервисов:"
$COMPOSE -f "$COMPOSE_FILE" ps

cat <<'EOF'
[run-local] Готово.
  Состояние:   docker compose -f docker-compose.yml ps
  Логи:        docker compose -f docker-compose.yml logs -f <service>
  Остановка:   docker compose -f docker-compose.yml down
  Полный сброс (с удалением данных): scripts/reset-environment.sh
Только инфраструктура для разработки: docker compose -f docker/compose.dev.yml up -d
EOF
