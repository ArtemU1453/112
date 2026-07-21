# Core Platform Architecture — ЕАСУР

- **Status:** APPROVED · Stage 4 · Подчиняется Architecture.md, Platform Architecture, EAGF.

## 1. Назначение
Core Platform — набор **общих сервисов и библиотек**, переиспользуемых всеми прикладными
подсистемами (диспетчеризация, телефония, ГИС и т. д. — реализуются на последующих этапах).
Прикладная логика (обработка вызовов/инцидентов/диспетчеризации/операторов/ИИ-анализа) на данном
этапе **запрещена**.

## 2. Отношение к существующим сервисам
Часть платформенных возможностей уже реализована сервисами Stage 0 и не дублируется (Architecture
Freeze, SSOT): Identity — auth-service + Keycloak (ADR-007); Audit — audit-service; Notification —
notification-service; API Gateway — gateway-service; Messaging — Kafka (ADR-008). Stage 4 добавляет
**общий слой**: библиотеки (`platform-commons`), SDK (`platform-sdk`), порты-абстракции
(`platform-abstractions`), контракты (`contracts/`) и governance-стандарты, обобщающие эти сервисы.

## 3. Состав Core Platform

| Возможность | Где | Capability/ADR |
|-------------|-----|----------------|
| Identity (Authn/Authz/Session/Token/MFA/Lockout/Service Accounts) | auth-service + Keycloak | CAP-IDP-001, ADR-007 |
| RBAC/ABAC | Keycloak roles + policy-абстракции | ADR-007, RBAC/ABAC (Identity Arch) |
| User Management (пользователи/подразделения/должности/справочники) | auth-service + admin API | Identity Architecture |
| Audit (неизменяемый журнал/поиск/экспорт) | audit-service | Audit Platform |
| Configuration (параметры/flags/профили/политики) | config + ConfigMap/Vault | Configuration Standards |
| Notification (IN_APP/SMS/EMAIL/PUSH/SYSTEM) | notification-service + порт | CAP-NOTIFY-001 |
| Messaging (команды/события/DLQ/retry/идемпотентность) | Kafka + порты | CAP-MSG-001, ADR-008 |
| API Gateway (маршрут/лимиты/токены/аудит/версии/ошибки) | gateway-service | API Gateway Platform |
| Common Libraries + SDK | platform-commons / platform-sdk | CAP-SDK-001 |
| File Storage / Search / Document / AI / Integration | порты + адаптеры | CAP-STORAGE/SEARCH/DOC/AI-001 |
| Localization / Time / Error Handling / Validation | platform-commons | Common Libraries |

## 4. Принципы
- **Reuse-first:** каждый новый сервис использует SDK и общие библиотеки (Shared Domain Kernel /
  Internal SDK Governance Standards); дублирование инфраструктурного кода запрещено (ARTICLE 7).
- **Capability-first (TAP):** сервисы независимы от конкретных технологий через Capability ID/ADR.
- **API First:** все API имеют контракты до реализации (`contracts/`, ARTICLE 12).
- **No application logic:** платформа не содержит прикладной логики (запрет Stage 4).
- **Security/Observability by Design:** через SDK (единые безопасность/логирование/аудит/трассировка).

## 5. Связанные документы
Identity Architecture · Integration Architecture · Messaging Architecture · Security Architecture ·
SDK Documentation · Common Libraries Guide · API Guidelines · AI Integration Architecture ·
10 стандартов Stage 4 (Shared Domain Kernel, Internal SDK Governance, API Lifecycle, Event
Governance, Canonical Data Model, Integration Governance, AI Provider Abstraction, Document
Lifecycle, Operational Telemetry, Service Maturity Model).
