#!/usr/bin/env bash
# ЕАСУР — сборка офлайн-пакета обновления для air-gapped площадок (AGSDS).
# Формирует архив артефактов с контрольной суммой и манифестом происхождения.
set -euo pipefail
OUT="${1:-/opt/easur/updates}"
STAMP="$(date +%Y%m%d_%H%M%S)"
BUNDLE="$OUT/bundle_${STAMP}.tar.gz"
mkdir -p "$OUT"
echo "[offline] формирование пакета: $BUNDLE"
# Список артефактов формируется из релиза (образы, чарты, манифесты, документация).
tar -czf "$BUNDLE" -C "$(cd "$(dirname "$0")/../.." && pwd)" \
  helm kubernetes infrastructure docs config CHANGELOG.md
sha256sum "$BUNDLE" > "${BUNDLE}.sha256"
cat > "${BUNDLE}.provenance.txt" <<PROV
bundle: $(basename "$BUNDLE")
created_at: $STAMP
source_commit: $(git -C "$(dirname "$0")" rev-parse HEAD 2>/dev/null || echo unknown)
checksum_file: $(basename "$BUNDLE").sha256
PROV
echo "[offline] готово: $BUNDLE (+ .sha256, .provenance.txt)"
