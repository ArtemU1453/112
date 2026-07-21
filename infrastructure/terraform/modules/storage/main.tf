# ЕАСУР — StorageClass по умолчанию (Infrastructure Architecture, ADR-013 для объектного слоя).
# provisioner задаётся под целевую платформу хранения через переменную окружения кластера;
# по умолчанию — локальный/CSI-провайдер контура. Реальный provisioner подставляется на месте.
resource "kubernetes_storage_class" "default" {
  metadata {
    name = var.default_storage_class
    annotations = {
      "storageclass.kubernetes.io/is-default-class" = "true"
    }
  }
  storage_provisioner    = "kubernetes.io/no-provisioner"
  reclaim_policy         = "Retain"
  volume_binding_mode    = "WaitForFirstConsumer"
  allow_volume_expansion = true
}
