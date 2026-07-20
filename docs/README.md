# Документация

Документация для платформы EASUR.

## Структура

```
docs/
├── architecture/           # Архитектура системы
│   ├── overview.md
│   ├── microservices.md
│   ├── data-flow.md
│   └── security.md
├── api/                    # API документация
│   ├── auth-api.md
│   ├── incident-api.md
│   ├── dispatch-api.md
│   └── ...
├── deployment/             # Развёртывание
│   ├── local-setup.md
│   ├── docker-setup.md
│   ├── kubernetes.md
│   └── terraform.md
├── development/            # Разработка
│   ├── setup.md
│   ├── coding-standards.md
│   ├── testing.md
│   └── ci-cd.md
├── operations/             # Эксплуатация
│   ├── monitoring.md
│   ├── logging.md
│   ├── troubleshooting.md
│   └── disaster-recovery.md
└── images/                 # Диаграммы и изображения
```

## Инструменты

- MkDocs / Docusaurus для генерации статических сайтов
- Plantuml / Mermaid для диаграмм
- OpenAPI / Swagger для API документации

## Генерация

```bash
mkdocs build
mkdocs serve
```
