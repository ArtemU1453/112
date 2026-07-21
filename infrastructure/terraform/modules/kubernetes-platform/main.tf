# ЕАСУР — платформенные namespaces с Pod Security Admission, квотами и limit ranges (ADR-010).
resource "kubernetes_namespace" "this" {
  for_each = var.namespaces
  metadata {
    name = each.key
    labels = {
      "app.kubernetes.io/part-of"          = "emergency-112"
      "app.kubernetes.io/environment"      = var.environment
      # Pod Security Admission (встроенный контроль безопасности подов)
      "pod-security.kubernetes.io/enforce" = each.value.pod_security_level
      "pod-security.kubernetes.io/audit"   = each.value.pod_security_level
      "pod-security.kubernetes.io/warn"    = each.value.pod_security_level
    }
  }
}

resource "kubernetes_resource_quota" "this" {
  for_each = var.namespaces
  metadata {
    name      = "quota"
    namespace = kubernetes_namespace.this[each.key].metadata[0].name
  }
  spec {
    hard = {
      "requests.cpu"    = each.value.cpu_requests
      "requests.memory" = each.value.memory_requests
      "limits.cpu"      = each.value.cpu_limits
      "limits.memory"   = each.value.memory_limits
      "pods"            = each.value.max_pods
    }
  }
}

resource "kubernetes_limit_range" "this" {
  for_each = var.namespaces
  metadata {
    name      = "limits"
    namespace = kubernetes_namespace.this[each.key].metadata[0].name
  }
  spec {
    limit {
      type = "Container"
      default = {
        cpu    = "500m"
        memory = "512Mi"
      }
      default_request = {
        cpu    = "100m"
        memory = "128Mi"
      }
    }
  }
}
