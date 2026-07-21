variable "environment" { type = string }
variable "namespaces" {
  type = map(object({
    pod_security_level = string
    cpu_requests       = string
    cpu_limits         = string
    memory_requests    = string
    memory_limits      = string
    max_pods           = number
  }))
}
