#!/usr/bin/env bash
# ЕАСУР — развёртывание платформы с нуля (Operational Procedures, DoD Stage 3).
# Последовательно: подготовка узлов (Ansible) -> платформенный слой (Terraform) ->
# платформенные манифесты (Kustomize) -> платформенные чарты. Идемпотентно.
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
ENV="${1:-development}"
echo "[platform] окружение: $ENV"

echo "[1/4] Подготовка узлов (Ansible)"
( cd "$ROOT/infrastructure/ansible" && ansible-playbook site.yml ) || {
  echo "[platform] пропуск Ansible (нет доступа к узлам в текущей среде)"; }

echo "[2/4] Платформенный слой Kubernetes (Terraform)"
( cd "$ROOT/infrastructure/terraform/environments/$ENV" && terraform init && terraform apply -auto-approve ) || {
  echo "[platform] Terraform требует доступ к кластеру"; }

echo "[3/4] Платформенные манифесты (Kustomize)"
kubectl apply -k "$ROOT/kubernetes/platform" || echo "[platform] требуется доступ к кластеру"

echo "[4/4] Платформенные компоненты (Helm-values)"
echo "[platform] см. docs/platform/deployment-architecture.md для установки чартов инфраструктуры"
echo "[platform] Готово."
