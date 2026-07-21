#!/usr/bin/env bash
# ЕАСУР — локальная валидация: архитектура + версии + документация.
set -euo pipefail
cd "$(dirname "$0")/.."
python3 scripts/validate_architecture.py
python3 scripts/validate_versions.py
python3 scripts/validate_docs.py
echo "[verify] Все проверки пройдены."
