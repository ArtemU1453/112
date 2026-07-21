#!/usr/bin/env bash
# ЕАСУР — линтеры/анализаторы (config/quality).
set -euo pipefail
cd "$(dirname "$0")/.."
if command -v yamllint >/dev/null 2>&1; then
  echo "[lint] yamllint"; yamllint -c config/quality/yamllint.yml .
else
  echo "[lint] yamllint не установлен — пропуск (см. Toolchain Governance Standard)"
fi
if command -v markdownlint-cli2 >/dev/null 2>&1; then
  echo "[lint] markdownlint"; markdownlint-cli2 --config config/quality/.markdownlint.jsonc "**/*.md" "#node_modules" || true
fi
echo "[lint] Готово."
