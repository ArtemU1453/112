# Toolchain Version Catalog — ЕАСУР

**Единый источник истины (Single Source of Truth) по версиям** инструментов, языков, платформ
и ключевых библиотек проекта. Реализует Toolchain Version Management Standard (TVMS).

## Правило

Конкретные номера версий **не дублируются** в архитектурной и инженерной документации
(Project Constitution, Architecture Baseline, Engineering Handbook, Architecture.md, ADR, RFC,
Development Guidelines, Coding Standards). Эти документы ссылаются на данный каталог формулировкой
уровня: «использовать актуальную поддерживаемую версию согласно каталогу версий проекта
(`config/toolchain/versions.yaml`)». См. также Technology Abstraction Policy (TAP).

## Состав

| Файл | Назначение |
|------|-----------|
| `versions.yaml` | SSOT версий (min/recommended/category) по категориям инструментов |
| `compatibility-matrix.yaml` | Совместимые/несовместимые версии и зависимости `requires` |
| `supported-platforms.yaml` | Поддерживаемые платформы разработки/исполнения и их ограничения |
| `deprecated-versions.yaml` | Реестр устаревших/снятых с поддержки версий |
| `migration-policy.md` | Правила обновления версий, тестирование, откат, критерии завершения |

## Категории версий

`LTS` · `Supported` · `Preview` · `Experimental` · `Deprecated` · `EndOfLife`.
ИИ и разработчики обязаны учитывать категорию при подготовке решений; `Deprecated`/`EndOfLife`
в prod-путь — только с явным разрешением (RFC).

## Управление

Любое изменение версии выполняется по `migration-policy.md`: анализ совместимости, обновление
`compatibility-matrix.yaml`, проверка CI/CD, обновление документации, запись в журнал изменений.
CI автоматически проверяет использование только утверждённых версий (см.
`.github/workflows/architecture-validation.yml`).

## Связанные документы

- Toolchain Governance Standard — `docs/standards/Toolchain-Governance-Standard.md`
- Technology Abstraction Policy — `docs/standards/Technology-Abstraction-Policy.md`
- Technology Mapping — `docs/architecture/technology-mapping.md`
- Dependency Governance Standard — `docs/standards/Dependency-Governance-Standard.md`
