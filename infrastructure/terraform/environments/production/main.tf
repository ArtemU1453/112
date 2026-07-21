# ЕАСУР — окружение: production (EGS). Подключает корневой модуль платформы.
module "platform" {
  source      = "../../"
  environment = "production"
  namespaces  = var.namespaces
}
