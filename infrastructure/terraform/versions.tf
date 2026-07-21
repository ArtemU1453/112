# ЕАСУР — требования к версиям Terraform и провайдеров (ADR-010).
# Конкретные номера согласованы с каталогом версий (config/toolchain/versions.yaml, TVMS).
terraform {
  required_version = ">= 1.8.0"
  required_providers {
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = ">= 2.31.0"
    }
    helm = {
      source  = "hashicorp/helm"
      version = ">= 2.14.0"
    }
  }
  # backend настраивается на защищённое хранилище состояния внутри контура (S3-совместимое,
  # ADR-013) через backend-config при init; в air-gapped — локальный зашифрованный backend.
}
