# ЕАСУР — входные переменные корневого модуля платформы (ADR-010).
variable "kubeconfig_path" {
  description = "Путь к kubeconfig целевого кластера (значение из окружения, не из git)."
  type        = string
  default     = "~/.kube/config"
}

variable "kube_context" {
  description = "Контекст kubeconfig."
  type        = string
  default     = null
}

variable "environment" {
  description = "Имя окружения: development | test | stage | production (EGS)."
  type        = string
}

variable "namespaces" {
  description = "Прикладные namespaces и их параметры (квоты, pod-security)."
  type = map(object({
    pod_security_level = string           # restricted | baseline | privileged (Pod Security)
    cpu_requests       = string
    cpu_limits         = string
    memory_requests    = string
    memory_limits      = string
    max_pods           = number
  }))
}

variable "default_storage_class" {
  description = "Имя StorageClass по умолчанию."
  type        = string
  default     = "easur-standard"
}
