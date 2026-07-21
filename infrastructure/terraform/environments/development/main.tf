# ЕАСУР — окружение: development (EGS). Подключает корневой модуль платформы.
module "platform" {
  source      = "../../"
  environment = "development"
  namespaces  = var.namespaces
}
