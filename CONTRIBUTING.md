# Участие в разработке ЕАСУР (Contributing)

Спасибо за вклад в развитие ЕАСУР — государственной системы диспетчеризации экстренных служб.
Проект ведётся как промышленный: изменения проходят управляемый процесс, документация первична
(SSOT), архитектура защищена от дрейфа. Ниже — обязательные правила участия. Они не дублируют, а
связывают уже существующие стандарты проекта в `docs/`.

## 1. Прежде чем начать

- Ознакомьтесь с [Кодексом поведения](CODE_OF_CONDUCT.md) и [Политикой безопасности](SECURITY.md).
- Изучите видение и архитектуру: [`docs/Vision.md`](docs/Vision.md), [`docs/Architecture.md`](docs/Architecture.md),
  [`docs/README.md`](docs/README.md).
- Ключевые правила закреплены в [Конституции проекта](docs/governance/project-constitution.md) и
  [Architecture Baseline](docs/governance/architecture-baseline.md).

## 2. Рабочий процесс (Git)

- Следуйте [Git Strategy](docs/standards/git-strategy.md): ветки от актуального `main`, атомарные
  коммиты, осмысленные сообщения (Conventional Commits — `feat`, `fix`, `docs`, `test`, `build`,
  `refactor`, `chore`).
- Каждая задача берётся из бэклога ([backlog-structure](docs/standards/backlog-structure.md)); работа
  начинается только при выполнении [Definition of Ready](docs/standards/definition-of-ready.md).
- Pull Request оформляется по [шаблону](.github/pull_request_template.md) и должен соответствовать
  [Definition of Done](docs/standards/definition-of-done.md).

## 3. Стандарты кода

- [Coding Standards](docs/standards/coding-standards.md), [Naming Standards](docs/standards/naming-standards.md),
  [Logging Standards](docs/standards/logging-standards.md), [Configuration Standards](docs/standards/configuration-standards.md).
- Статический анализ обязателен и настроен в `config/quality/`
  (Checkstyle `checkstyle.xml`, PMD `pmd-ruleset.xml`, SpotBugs `spotbugs-exclude.xml`).
- Версии инструментов и библиотек — только через каталог [`config/toolchain/versions.yaml`](config/toolchain/versions.yaml)
  (TVMS); номера версий в документации не дублируются.
- Заглушки, `TODO`, демонстрационный и сокращённый код запрещены — каждый файл должен быть
  пригоден к сборке и включению в проект.

## 4. Тесты и качество

- Следуйте [Testing Strategy](docs/standards/testing-strategy.md): unit + интеграционные тесты
  требуемых уровней; интеграционные — на Testcontainers.
- Перед PR прогоните локальные проверки: `./scripts/verify.sh` (архитектура, каталог версий,
  документация) и сборку/тесты затронутых модулей (`mvn -q verify`).
- Изменение должно проходить [Quality Gates](docs/standards/quality-gates.md) и не ослаблять
  механизмы безопасности ([Security Standards](docs/standards/security-standards.md)).

## 5. Изменения архитектуры

- Любое изменение утверждённой архитектуры (стиль, границы контекстов, структура каталогов, правила
  зависимостей, технологический стек, контракты API, модель данных) выполняется **только** через
  процесс **RFC → Architecture Review Board → ADR**:
  [RFC](docs/rfc/README.md), [ADR](docs/adr/README.md), [Architecture Evolution Standard](docs/standards/Architecture-Evolution-Standard.md).
- Технологии фиксируются как возможности (Capability) в [Technology Mapping](docs/architecture/technology-mapping.md)
  согласно [Technology Abstraction Policy](docs/standards/Technology-Abstraction-Policy.md).
- Документация синхронизируется в том же изменении; история изменений не удаляется.

## 6. Оформление Pull Request

- Небольшой связный объём; описание по шаблону PR; ссылки на задачу/RFC/ADR при наличии.
- Зелёные обязательные проверки CI (сборка, тесты, статанализ, документация/архитектура).
- Внутреннее code review обязательно; секретов в изменениях быть не должно (см. SECURITY.md).

## 7. Сообщения об ошибках и предложения

- Дефекты и предложения оформляйте по шаблонам в `.github/ISSUE_TEMPLATE/` (при наличии) с
  воспроизводимыми шагами; вопросы безопасности — по [SECURITY.md](SECURITY.md), не публично.

Соблюдение этих правил обеспечивает согласованность архитектуры, качество кода и готовность каждого
изменения к включению в основную ветку.
