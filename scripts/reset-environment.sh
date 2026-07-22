#!/usr/bin/env bash
# ЕАСУР — СБРОС локального окружения Docker Compose.
# ВНИМАНИЕ: удаляет контейнеры, сети И volumes (все локальные данные БД/кэша/брокера).
# Не предназначен для kubernetes-окружений. Требует подтверждения (или FORCE=1).
set -euo pipefail
cd "$(dirname "$0")/.."

COMPOSE="${COMPOSE:-docker compose}"
COMPOSE_FILE="${COMPOSE_FILE:-docker-compose.yml}"

if [ "${FORCE:-}" != "1" ]; then
  read -r -p "Будут удалены контейнеры И volumes локального стека (${COMPOSE_FILE}). Продолжить? [y/N] " ans
  [ "$ans" = "y" ] || { echo "Отменено."; exit 1; }
fi

echo "[reset] Останавливаем и удаляем контейнеры, сети и volumes…"
$COMPOSE -f "$COMPOSE_FILE" down -v --remove-orphans

echo "[reset] Готово. Локальная инфраструктура сброшена. Повторный запуск: scripts/run-local.sh"
