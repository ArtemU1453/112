# Naming Standards — ЕАСУР

- **Status:** APPROVED · Baseline: v1.0
- Дополняет Development Guidelines (`development-guidelines.md`); при расхождении приоритет —
  Development Guidelines для кода. Изменение соглашений — только через ADR (Architecture Freeze).

Единые соглашения об именовании по всем артефактам проекта.

| Артефакт | Соглашение | Пример |
|----------|-----------|--------|
| **Repository** | `kebab-case`, домен проекта | `easur-112` (данный монорепозиторий) |
| **Directories** | `kebab-case` | `dispatch-service`, `response-zones` |
| **Packages** (Java) | `by.mchs.e112.<service>.<layer>`, lower-case | `by.mchs.e112.incident.domain` |
| **Modules** | по имени сервиса/приложения/библиотеки | `incident-service`, `frontend-dispatcher` |
| **Classes/Interfaces** | `PascalCase`; суффикс по роли; интерфейс без префикса `I` | `IncidentService`, `ChannelSender` |
| **Methods** | `camelCase`, глагольные | `autoDispatch`, `changeStatus` |
| **Variables** | `camelCase` (Java/TS), `snake_case` (Python) | `nearestUnit`, `casualties_count` |
| **Constants** | `UPPER_SNAKE_CASE` | `MAX_BURST_CAPACITY` |
| **REST-ресурсы** | `/api/v1/<resource>`, множ. число, `kebab-case` | `/api/v1/incidents` |
| **Kafka Topics** | `<context>.<event>` (прош. время); версия суффиксом | `incident.created`, `call.analyzed`, `unit.status-changed` |
| **Redis Keys** | `<service>:<entity>:<id>[:<attr>]`, `:`-разделитель | `incident:stats:by-status` |
| **Docker Images** | `<registry>/<service>:<tag>`, имя = имя каталога | `.../incident-service:stable` |
| **Helm Charts** | `kebab-case`; зонтичный — `emergency-112` | `emergency-112` |
| **Kubernetes Objects** | `kebab-case`, имя = имя сервиса; лейблы `app.kubernetes.io/*` | `deployment/incident-service` |
| **DB Tables/Columns** | `snake_case`, таблицы во множ. числе (см. DGS) | `incidents`, `created_at` |
| **DB Indexes** | `ix_/ux_/pk_/fk_/ck_ <table>_<cols>` (см. DGS) | `ix_incidents_status` |
| **Git Branches** | `feature/*`,`bugfix/*`,`hotfix/*`,`release/*` (Git Strategy) | `feature/INC-001-aggregate` |
| **Files** | Java=тип; Python=`snake_case`; React=`PascalCase.tsx`; docs=`kebab-case.md` | `IncidentTable.tsx` |
| **Capability ID** | `CAP-<AREA>-<NNN>` (TAP) | `CAP-MSG-001` |
| **ADR/RFC** | `ADR-XXXX-...`, `RFC-XXXX-...` | `ADR-009-...` |
| **Backlog ID** | `<CTX>-<NNN>` (Backlog Structure) | `DISP-014` |

## Правила
- Идентификаторы кода — на английском; доменные термины — по Ubiquitous Language (DMS).
- Единообразие обязательно и проверяется на Code Review и автоматически (architecture validation).
- Новый вид артефакта → добавить соглашение сюда в рамках вводящей его задачи (Document Synchronization).
