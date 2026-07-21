# ЕАСУР — корневой модуль платформы: собирает платформенный слой Kubernetes (ADR-010).
# НЕ содержит бизнес-логики/приложений — только платформенные ресурсы.

module "kubernetes_platform" {
  source     = "./modules/kubernetes-platform"
  environment = var.environment
  namespaces  = var.namespaces
}

module "network" {
  source     = "./modules/network"
  namespaces = keys(var.namespaces)
  depends_on = [module.kubernetes_platform]
}

module "storage" {
  source                = "./modules/storage"
  default_storage_class = var.default_storage_class
}
