# Emergency Domain Model — ЕАСУР

- **Status:** APPROVED · Stage 5 · Подчиняется Domain Modeling Standard (DMS), Canonical Data Model
  Standard. Синхронизирован с реализацией (SSOT).

Доменная модель прикладной платформы экстренных вызовов. Термины — Ubiquitous Language (DMS).

## 1. Bounded contexts (операционные)
| Контекст | Агрегаты | Сервис |
|----------|----------|--------|
| Телефония | Call | telephony-service |
| Речь/ИИ | Transcript, ExtractedEntity | ai-service |
| Происшествие | Incident (+ History, Attachment) | incident-service |
| Диспетчеризация | Unit, Assignment, Recommendation | dispatch-service |
| Аудит | AuditEvent | audit-service |

## 2. Ключевые агрегаты и сущности

### Call (вызов) — 5.1/5.2
Идентификатор, направление (`CallDirection`), состояние (`CallStatus`), время начала, линия,
оператор, ссылка на запись (recording), связанное происшествие. Инварианты — Operational State
Machine §2. Параллельные/повторные вызовы, удержание, переадресация, завершение — Call Processing
Standard.

### Transcript / ExtractedEntity (речь и извлечение) — 5.3/5.4
- **Transcript:** реплики (сегменты) с временными метками, языком, уверенностью распознавания;
  возможна повторная обработка записи.
- **ExtractedEntity (value object):** тип сущности (Emergency Data Dictionary), значение, **источник**
  (STT/ИИ/оператор), **вероятность** (0..1), **статус подтверждения** (EXTRACTED/CONFIRMED/REJECTED/
  EDITED). Не подтверждённые сущности не являются истиной карточки.

### Incident (происшествие) — 5.5
Корень агрегата: уникальный номер, тип (`IncidentType`), приоритет (`IncidentPriority`), статус
(`IncidentStatus`), адрес/координаты, описание, число пострадавших, версия (оптимистическая
блокировка), история изменений, вложения, связанные вызовы/события, участники, временная шкала.
Инварианты статусов — Operational State Machine §1.

### Unit / Assignment / Recommendation (диспетчеризация) — 5.8
- **Unit:** оперативная единица, тип, состояние (`UnitStatus`), координаты.
- **Assignment:** связь наряд↔происшествие.
- **Recommendation (Stage 5):** предложение сил/средств со статусом (PROPOSED/APPROVED/MODIFIED/
  DECLINED); направление — только после утверждения диспетчером.

### AuditEvent (аудит) — 5.10
Неизменяемая запись действия (пользователь/система/ИИ): кто, что, над чем, когда, детали (JSONB).

## 3. Доменные события (Kafka, ADR-008)
`call.received`, `call.analyzed`, `incident.created`, `incident.updated`, `dispatch.assigned`,
`unit.status-changed`, `audit.events`. Именование/эволюция — Event Governance Standard.

## 4. Инварианты домена (обязательные)
- ИИ формирует **предложения**, не решения (AI Assistance Policy).
- Изменение карточки — только с фиксацией (версия/история/аудит).
- Направление нарядов — только после подтверждения диспетчером.
- Записи разговоров — по политикам хранения (Call Processing Standard).

## 5. Ubiquitous Language (дополнение к DMS)
Вызов (Call) · Запись (Recording) · Транскрипт (Transcript) · Сущность (ExtractedEntity) ·
Подтверждение (Confirmation) · Происшествие (Incident) · Рекомендация (Recommendation) ·
Временная шкала (Operational Timeline) · Регламент (Operational Protocol). Полный словарь —
Emergency Data Dictionary.
