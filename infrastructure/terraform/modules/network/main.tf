# ЕАСУР — сетевые политики: default-deny входящего трафика в каждом namespace (Network Architecture).
# Разрешающие политики (внутри namespace, к БД/Kafka, egress) задаются манифестами
# kubernetes/platform/networking согласно сегментации.
resource "kubernetes_network_policy" "default_deny_ingress" {
  for_each = toset(var.namespaces)
  metadata {
    name      = "default-deny-ingress"
    namespace = each.value
  }
  spec {
    pod_selector {}
    policy_types = ["Ingress"]
  }
}
