# environments/ — шаблоны конфигураций окружений

Версионируемые шаблоны переменных окружения по типам сред (Environment Governance Standard,
Configuration Standards). Содержат только имена переменных и безопасные значения по умолчанию —
БЕЗ секретов. Секреты берутся из секрет-менеджера/Kubernetes Secret.

| Файл | Окружение |
|------|-----------|
| local.env.example | Local Development |
| development.env.example | Development |
| test.env.example | Integration/QA/Test |
| stage.env.example | Staging |
| production.env.example | Production (только имена; значения — из секрет-менеджера) |
