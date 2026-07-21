# National Integration Architecture — ЕАСУР

- **Status:** APPROVED · Stage 9 · Подчиняется Architecture.md, Integration Architecture (Stage 4),
  EAGF, ADR-022/023. Единая платформа межведомственного взаимодействия.

## 1. Назначение
Безопасный, контролируемый, масштабируемый обмен данными с внешними информационными системами.
Принципы: **API First, Contract First, Event First, Zero Trust, Least Privilege, Immutable Audit,
Backward Compatibility, Technology Abstraction, Idempotency, Full Traceability.**

## 2. Принцип единой точки
**Все внешние обмены — исключительно через интеграционную платформу** (ADR-022). Прямые
подключения в обход платформы **запрещены** (запрет этапа). Внешние системы обязаны быть
зарегистрированы в реестре (9.2). Прикладные системы не имеют прямых зависимостей друг от друга или
от внешних систем — только через платформу и контракты.

## 3. Карта подэтапов 9.1–9.10

| Подэтап | Возможность | Компонент | Стандарт/ADR |
|---------|-------------|-----------|--------------|
| 9.1 Integration Platform | REST/gRPC/async/batch/file/stream, адаптеры, маршрутизация, трансформация | ESB/gateway (ADR-022) | Integration Architecture (Stage 4) |
| 9.2 External Systems Registry | Реестр внешних систем | Registry | External Systems Registry Spec |
| 9.3 API Lifecycle | Проектирование/публикация/версии/тест/эксплуатация/вывод/каталог | API-платформа | API Lifecycle Management / API Versioning Standards |
| 9.4 Interagency Data Exchange | Sync/async/гарантированная доставка/дедуп/целостность/подтверждение | Data Exchange | Message Reliability Standard |
| 9.5 Canonical Data Model | Канонический формат, справочники, идентификаторы, преобразования | Canonical | Canonical Data Model (Stage 4) + Canonical Identifier Standard |
| 9.6 Integration Security | mTLS/авторизация/подпись/целостность/anti-replay/аудит/сертификаты | Security (ADR-023) | Data Exchange Security Standard |
| 9.7 Integration Monitoring | Состояние каналов/латентность/ошибки/повторы/потери/SLA/алерты | Monitoring | Integration SLA Standard |
| 9.8 Reference Data Management | Версии/публикация/синхронизация/аудит/согласованность НСИ | НСИ | Reference Data Management Standard |
| 9.9 Partner Sandbox | Тестовые данные/эмуляция/contract testing/совместимость | Sandbox | Contract Testing / Partner Onboarding Standards |
| 9.10 Integration Governance | Подключение/изменение контрактов/версии/сертификация/отзыв/вывод | Governance | Contract Governance / Integration Certification Standards |

## 4. Транспорты и трансформация (9.1)
Адаптеры транспортов: REST, gRPC (ADR-022), асинхронный (Kafka, ADR-008), пакетный, файловый,
потоковый. Маршрутизация и **трансформация** между внутренними и **каноническими** сообщениями
(Canonical Data Model). Внешние контракты — канонические и версионируемые (Contract First).

## 5. Трассируемость (Full Traceability)
Каждое сообщение имеет идентификатор, `correlationId`, версию контракта, отметку источника/
назначения; сквозная трассировка (OpenTelemetry, ADR-012) и неизменяемый аудит межсистемных обменов
(Cross-System Audit Standard).

## 6. Инварианты (обязательные проверки этапа)
- Все интеграции используют контрактную модель (Contract First).
- Нет прямых зависимостей между прикладными системами (только через платформу).
- Все API версионируются (API Versioning Standard).
- Все сообщения трассируются (Full Traceability).
- Полная наблюдаемость интеграций (Integration Monitoring Guide).
- Обеспечена обратная совместимость (Backward Compatibility).
- Все изменения — через RFC и ADR.

## 7. Связанные документы
API Governance Guide · Integration Security Architecture · Canonical Data Model · External Systems
Registry Specification · Partner Integration Guide · Integration Monitoring Guide · Interoperability
Handbook · 12 стандартов Stage 9 · контракты `contracts/` · ADR-022/023.
