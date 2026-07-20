# Development Guidelines — ЕАСУР

> Приоритет SSOT: часть Baseline (именование). Статус: APPROVED · Baseline: v1.0
> Изменение соглашений об именовании — только через новый ADR (Architecture Freeze).

Единые соглашения об именовании для всего проекта. Правила обязательны и проверяются на Code Review.

## Именование файлов
- Java: один публичный тип на файл, имя файла = имя типа (`IncidentCommandService.java`).
- Python: `snake_case.py` (`kafka_bridge.py`).
- TypeScript/React: компоненты — `PascalCase.tsx` (`IncidentTable.tsx`); прочие модули —
  `camelCase.ts` (`http.ts`, `realtime.ts`).
- Документация: `kebab-case.md`; ключевые baseline-документы — как в индексе (`Vision.md`,
  `Architecture.md`).
- Конфигурация: `application.yml`, `values.yaml`, `docker-compose.yml`.

## Именование классов/типов
- Java: `PascalCase` типы, `camelCase` методы/поля, `UPPER_SNAKE_CASE` константы, enum-значения
  `UPPER_SNAKE_CASE`. Суффиксы по роли: `*Controller`, `*Service`, `*Repository`, `*Mapper`,
  `*Config`, `*Exception`, `*Dto`/record.
- TypeScript: типы/компоненты `PascalCase`, переменные/функции `camelCase`.

## Именование пакетов (Java)
`by.mchs.e112.<service>.<layer>` — слои: `domain`, `application` (или `service`), `dto`,
`repository`, `controller`, `mapper`, `kafka`, `config`, `exception`. Домен не зависит от
инфраструктурных слоёв.

## Именование REST API
- Базовый путь: `/api/v1/<resource>` (версия в пути). Ресурсы — существительные во
  множественном числе, `kebab-case` (`/api/v1/incidents`, `/api/v1/response-zones`).
- Идентификаторы — path-параметры (`/api/v1/incidents/{id}`). Действия вне CRUD — подресурсом
  (`/api/v1/incidents/{id}/status`). Детали — API Governance Standard.

## Именование таблиц/индексов (PostgreSQL)
- Таблицы: `snake_case`, множественное число (`incidents`, `assignments`).
- Столбцы: `snake_case`; PK `id`; FK `<entity>_id`; временные метки `created_at`, `updated_at`.
- Индексы: `ix_<table>_<columns>`; уникальные `ux_<table>_<columns>`; PK `pk_<table>`;
  FK `fk_<table>_<ref>`. Детали — Database Governance Standard.

## Именование Kafka Topics
`<context>.<event>` в `kebab-case`/`dot`-нотации: `incident.created`, `dispatch.assigned`,
`unit.status-changed`, `call.analyzed`, `audit.events`. Несовместимая эволюция — новый топик
с версией (`<context>.<event>.v2`).

## Именование Docker Images
`<registry>/<service>:<tag>`, имя сервиса = имя каталога (`incident-service`); теги — SHA
коммита и канал (`stable`/`latest`).

## Именование Helm Charts
Зонтичный чарт — `emergency-112`; ключи сервисов в `values.yaml` совпадают с именами образов.

## Именование Git Branches
`feature/*`, `bugfix/*`, `hotfix/*`, `release/*`, `develop`, `main` (см. Git Strategy).
Формат: `feature/<TICKET>-<short-description>`.

## Общие правила
- Язык идентификаторов кода — английский; доменные термины — по Ubiquitous Language
  (Domain Modeling Standard). Комментарии/документация — русский (предметная область).
- Единые формат ошибок API, структурированное логирование и коды ответов — Coding Standards и AGS.
