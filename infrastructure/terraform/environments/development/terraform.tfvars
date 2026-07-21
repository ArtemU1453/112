# ЕАСУР — параметры окружения development (EGS). Значения квот — ориентировочные для dev.
namespaces = {
  "emergency-112" = {
    pod_security_level = "baseline"
    cpu_requests       = "2"
    cpu_limits         = "4"
    memory_requests    = "4Gi"
    memory_limits      = "8Gi"
    max_pods           = 40
  }
  "emergency-112-monitoring" = {
    pod_security_level = "baseline"
    cpu_requests       = "1"
    cpu_limits         = "2"
    memory_requests    = "2Gi"
    memory_limits      = "4Gi"
    max_pods           = 20
  }
}
