#!/usr/bin/env bash
# ЕАСУР — подготовка среды разработки одной командой (Developer Experience).
set -euo pipefail
cd "$(dirname "$0")/.."
echo "[bootstrap] Проверка версий инструментов…"
python3 scripts/validate_versions.py || true
echo "[bootstrap] Запуск инфраструктуры разработки (docker/compose.dev.yml)…"
if command -v docker >/dev/null 2>&1; then
  docker compose -f docker/compose.dev.yml up -d
else
  echo "[bootstrap] Docker не найден — установите Docker (см. docs/developer-experience/installation.md)"
fi
echo "[bootstrap] Готово. Далее: ./scripts/build.sh"
