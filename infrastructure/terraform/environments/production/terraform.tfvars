# ЕАСУР — параметры окружения production (EGS). Ужесточённый Pod Security и увеличенные квоты.
namespaces = {
  "emergency-112" = {
    pod_security_level = "restricted"
    cpu_requests       = "8"
    cpu_limits         = "16"
    memory_requests    = "16Gi"
    memory_limits      = "32Gi"
    max_pods           = 200
  }
  "emergency-112-monitoring" = {
    pod_security_level = "restricted"
    cpu_requests       = "4"
    cpu_limits         = "8"
    memory_requests    = "8Gi"
    memory_limits      = "16Gi"
    max_pods           = 60
  }
  "emergency-112-platform" = {
    pod_security_level = "restricted"
    cpu_requests       = "2"
    cpu_limits         = "4"
    memory_requests    = "4Gi"
    memory_limits      = "8Gi"
    max_pods           = 40
  }
}
