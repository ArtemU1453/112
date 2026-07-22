#!/usr/bin/env bash
# ЕАСУР — развёртывание в окружение DEV (Kubernetes) через Helm-чарт helm/emergency-112.
# Требуются настроенные kubectl/helm и доступ к кластеру. Образы — из указанного реестра/тега.
set -euo pipefail
cd "$(dirname "$0")/.."

: "${IMAGE_TAG:?Задайте IMAGE_TAG (тег образов: git SHA или версия релиза)}"
REGISTRY="${REGISTRY:-registry.local/easur}"
NAMESPACE="${NAMESPACE:-easur-dev}"
RELEASE="${RELEASE:-easur}"
CHART="helm/emergency-112"

echo "[deploy-dev] release=${RELEASE} ns=${NAMESPACE} tag=${IMAGE_TAG} registry=${REGISTRY}"
helm lint "$CHART"
helm upgrade --install "$RELEASE" "$CHART" \
  --namespace "$NAMESPACE" --create-namespace \
  -f "$CHART/values.yaml" \
  --set global.registry="$REGISTRY" \
  --set global.imageTag="$IMAGE_TAG" \
  --wait --timeout 10m

echo "[deploy-dev] Готово. Состояние: kubectl -n ${NAMESPACE} get pods,svc"
