#!/usr/bin/env bash
# ЕАСУР — развёртывание в окружение STAGE (Kubernetes) через Helm-чарт helm/emergency-112.
# STAGE максимально приближён к продуктиву (Environment Governance Standard).
set -euo pipefail
cd "$(dirname "$0")/.."

: "${IMAGE_TAG:?Задайте IMAGE_TAG (тег образов: git SHA или версия релиза)}"
REGISTRY="${REGISTRY:-registry.local/easur}"
NAMESPACE="${NAMESPACE:-easur-stage}"
RELEASE="${RELEASE:-easur}"
CHART="helm/emergency-112"

echo "[deploy-stage] release=${RELEASE} ns=${NAMESPACE} tag=${IMAGE_TAG} registry=${REGISTRY}"
helm lint "$CHART"
helm upgrade --install "$RELEASE" "$CHART" \
  --namespace "$NAMESPACE" --create-namespace \
  -f "$CHART/values.yaml" \
  --set global.registry="$REGISTRY" \
  --set global.imageTag="$IMAGE_TAG" \
  --atomic --wait --timeout 15m

echo "[deploy-stage] Готово. Состояние: kubectl -n ${NAMESPACE} get pods,svc"
