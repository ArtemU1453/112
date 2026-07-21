# Mobile SDK Guide — ЕАСУР

- **Status:** APPROVED · Stage 7 · Подчиняется Internal SDK Governance Standard, Mobile Architecture.

## Назначение
Единый Mobile SDK — обязательная основа **всех** мобильных приложений экосистемы (Field Responder,
Commander, Incident Command). Обеспечивает единообразие безопасности, синхронизации, аудита и
доступа к платформе. Аналог Platform SDK (Stage 4) для мобильного стека (ADR-016).

## Модули SDK
| Модуль | Назначение | Стандарт |
|--------|-----------|----------|
| Security | Аутентификация OIDC/JWT, защищённое хранилище ключей, авто-выход, проверка целостности | Mobile/Device Security |
| Sync | Offline-хранилище, outbox, дельта-синхронизация, версии, конфликты | ADR-017, Sync Standards |
| Messaging | Операционные сообщения (инд./групп./системные), подтверждения | Operational Messaging Standard |
| Media | Фото/видео/голос/документы, метаданные, целостность, привязка к инциденту | Media Governance Standard |
| Forms | Цифровые формы (рапорт/доклад/чек-листы/осмотр/разведка/пострадавшие/оборудование/расход) | Operational Forms Standard |
| Telemetry | Полевая телеметрия через адаптеры (GPS/терминалы/датчики) | Field Telemetry Standard |
| Audit | Мобильный аудит действий (синхронизируется с платформой) | Mobile Audit Standard |
| Config | Конфигурация/feature flags/политики | Configuration Standards |
| Observability | Логи/метрики/трассы (correlationId) | Operational Telemetry Standard |

## Правила использования
- Приложения обращаются к платформе **только** через SDK и **общие** контракты (`contracts/`) — не
  создают app-специфичные API (запрет этапа).
- Домашняя бизнес-логика домена не дублируется на клиенте (используется платформа).
- Секреты не хранятся в коде приложения; ключи — в защищённом хранилище устройства.
- Единый дизайн — через Mobile Design System (обязательно для всех приложений).

## Версионирование
SDK версионируется (SemVer); версии стека/библиотек — каталог версий (TVMS). Обратная совместимость
по умолчанию; несовместимо — мажорная версия + миграция (Internal SDK Governance / Mobile Lifecycle
Management Standards).

## Соответствие
Internal SDK Governance Standard, Service Maturity Model (мобильные приложения — уровень L1+ при
подключении SDK), Constitution ARTICLE 7.
