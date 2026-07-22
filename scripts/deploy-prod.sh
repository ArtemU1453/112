#!/usr/bin/env bash
# ЕАСУР — развёртывание в ПРОМЫШЛЕННОЕ окружение PROD (Kubernetes) через Helm.
# Применяются прод-оверрайды (values-production.yaml). Развёртывание атомарное
# (--atomic): при неуспехе выполняется автоматический откат. Требуется подтверждение.
# Порядок ввода в эксплуатацию — см. docs/operations/deployment-program.md и go-live-playbook.md.
set -euo pipefail
cd "$(dirname "$0")/.."

: "${IMAGE_TAG:?Задайте IMAGE_TAG (тег ПРОВЕРЕННОГО релиза; latest в проде запрещён)}"
REGISTRY="${REGISTRY:-registry.local/easur}"
NAMESPACE="${NAMESPACE:-easur-prod}"
RELEASE="${RELEASE:-easur}"
CHART="helm/emergency-112"

if [ "${CONFIRM:-}" != "yes" ]; then
  read -r -p "Развёртывание в PROD (ns=${NAMESPACE}, tag=${IMAGE_TAG}). Подтвердите ввод 'yes': " ans
  [ "$ans" = "yes" ] || { echo "Отменено."; exit 1; }
fi

echo "[deploy-prod] release=${RELEASE} ns=${NAMESPACE} tag=${IMAGE_TAG} registry=${REGISTRY}"
helm lint "$CHART"
helm upgrade --install "$RELEASE" "$CHART" \
  --namespace "$NAMESPACE" --create-namespace \
  -f "$CHART/values.yaml" \
  -f "$CHART/values-production.yaml" \
  --set global.registry="$REGISTRY" \
  --set global.imageTag="$IMAGE_TAG" \
  --atomic --wait --timeout 20m

echo "[deploy-prod] Готово. Проверка после развёртывания:"
echo "  kubectl -n ${NAMESPACE} get pods,svc"
echo "  См. docs/operations/post-deployment-validation-guide.md"
echo "Откат при необходимости: helm -n ${NAMESPACE} rollback ${RELEASE}"
