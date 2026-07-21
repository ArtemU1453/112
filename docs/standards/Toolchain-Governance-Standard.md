# Toolchain Governance Standard (TGS) — ЕАСУР

- **Status:** APPROVED · Baseline: v1.0

## Цель
Определить единый набор инструментов разработки, правила обновления и требования к
воспроизводимости среды. Все участники, включая ИИ, используют только утверждённый набор.
Конкретные версии — в каталоге версий (`config/toolchain/versions.yaml`, TVMS), не в теле стандарта.

## Область применения
Локальная разработка, CI/CD, контейнеры, тестовые окружения, staging, production (в части
инструментов сборки и доставки).

## Утверждённый набор инструментов (по возможностям, версии — в каталоге)

| Область | Инструмент (реализующий возможность) | Ссылка |
|---------|--------------------------------------|--------|
| Языки/рантаймы | Java (LTS), Node.js (LTS), TypeScript, Python | ADR-001, versions.yaml |
| Сборка | Maven; npm (единый пакетный менеджер frontend); Docker Buildx | versions.yaml |
| Контейнеризация | Docker Engine, Docker Compose | ADR-005 |
| Оркестрация | Kubernetes, Helm, Kustomize | ADR-005 |
| База данных | PostgreSQL (+PostGIS), Liquibase | ADR-003 |
| Брокер сообщений | Apache Kafka | ADR-008 |
| Кэш | Redis | ADR-003 |
| Контроль версий | Git | — |
| CI/CD | GitHub Actions (осн.), GitLab CI | ADR-006, ADR-009 |

**Выбор npm** (а не pnpm) — единый пакетный менеджер frontend; смена — только через ADR.

## IDE и инструменты разработки
Поддерживаемые IDE: **IntelliJ IDEA**, **Visual Studio Code**. Для каждой:
- рекомендуемые/обязательные плагины (VS Code — `.vscode/extensions.json`; IntelliJ — импорт
  профилей форматирования);
- настройки форматирования и импортов (`.editorconfig`, `.vscode/settings.json`, Checkstyle-профиль);
- правила синхронизации конфигураций — через committed subset в `.vscode/` и `.editorconfig`.

## Линтеры и анализаторы
Единый набор (роли; версии — в каталоге): Checkstyle, SpotBugs, PMD, ESLint, Prettier,
Markdownlint, Yamllint, Dockerfile-линт (hadolint), Stylelint. Конфигурации — `config/quality/`.
Использование альтернативных инструментов — только через отдельный ADR.

## Управление версиями инструментов
Для каждого инструмента: минимальная/рекомендуемая/(при необходимости) максимально
протестированная версия, политика и критерии обновления — определены в каталоге версий и
`compatibility-matrix.yaml`. Версии используются последовательно во всех окружениях.

## Воспроизводимость среды
- Единая среда через **Dev Container** (`.devcontainer/`).
- Единые базовые Docker-образы; фиксированные версии инструментов из каталога.
- Автоматическая установка зависимостей; единые переменные окружения для разработки
  (`environments/`, `.devcontainer/`).

## Проверка в CI/CD
Каждая сборка автоматически проверяет: соответствие утверждённым версиям; отсутствие
неподдерживаемых версий; успешную сборку в чистом окружении; воспроизводимость
(`.github/workflows/architecture-validation.yml`, `build.yml`).

## Обновление toolchain
Только после: анализа совместимости; оценки влияния; обновления документации/каталога; проверки
в CI/CD; утверждения через ADR (если влияет на архитектуру/процесс). См. `migration-policy.md`.

## Исключения
Нестандартный инструмент — только при наличии обоснования, анализа альтернатив, оценки рисков и
ссылки на ADR.

## Связи с другими документами
Project Constitution · Architecture Baseline · Engineering Handbook · Repository Governance
Standard · Dependency Governance Standard · Release Governance Standard · Coding Standards ·
Toolchain Version Management Standard.

## Журнал версий
| Версия | Дата | Изменение |
|--------|------|-----------|
| 1.0 | 2026-07-21 | Первичное утверждение (Stage 2). |
