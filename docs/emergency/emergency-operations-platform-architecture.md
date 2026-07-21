# Emergency Operations Platform Architecture — ЕАСУР

- **Status:** APPROVED · Stage 5 · Подчиняется Architecture.md, Core Platform Architecture, EAGF,
  всем стандартам предыдущих этапов.

## 1. Назначение
Прикладная платформа управления экстренными вызовами: полный жизненный цикл обращения гражданина
от поступления вызова до завершения реагирования. Опирается на Core Platform (Stage 4) и
инфраструктуру (Stage 3), не дублируя их.

## 2. Реализованная основа (Stage 0) и новые возможности (Stage 5)
Ключевые подсистемы жизненного цикла реализованы сервисами Stage 0 и **не дублируются**
(Architecture Freeze, SSOT). Stage 5 формализует домен (10 обязательных документов) и специфицирует
через контракты (API First) недостающие возможности: **подтверждение извлечённых сущностей**,
**поддержку принятия решений (advisory)**, **единую операционную временную шкалу** и сквозную
политику **«ИИ не принимает окончательное решение»**.

## 3. Карта подэтапов 5.1–5.10

| Подэтап | Возможность | Реализация (Stage 0) | Новое в Stage 5 (контракт/стандарт) |
|---------|-------------|----------------------|--------------------------------------|
| 5.1 Call Intake | Приём/очередь/состояния вызова | telephony-service (`CallStatus`, `CallDirection`) | `contracts/internal/call-intake-api.yaml`; Call Processing Standard |
| 5.2 Recording | Запись/хранение/поиск/политики | telephony-service + File Storage (ADR-013) | `recording-api.yaml`; политики хранения (Call Processing Standard) |
| 5.3 Speech | STT/язык/реплики/уверенность | ai-service (Whisper + адаптеры) | `speech-processing-api.yaml`; Speech Processing Standard (AiProvider, ADR-012 нет — CAP-AI-001) |
| 5.4 Extraction | Сущности + значение/источник/вероятность/подтверждение | ai-service (извлечение) | `information-extraction-api.yaml` (+confirmation); Emergency Data Dictionary |
| 5.5 Incident Card | Карточка/версии/история/статусы | incident-service (CQRS, `IncidentStatus`, история) | `incident-card-api.yaml`; Incident Lifecycle Standard |
| 5.6 Dispatcher Workspace | Управление вызовом/карточкой/подтверждение | frontend-dispatcher | Dispatcher Workflow Standard (подтверждение сущностей/рекомендаций) |
| 5.7 Decision Support | Классификация/пробелы/противоречия/вопросы (advisory) | — (новое) | `decision-support-api.yaml`; Operational Decision Support Standard |
| 5.8 Resource Recommendation | Рекомендации нарядов (утверждает диспетчер) | dispatch-service (автоподбор) | `resource-recommendation-api.yaml`; подтверждение обязательно |
| 5.9 Operational Timeline | Единая временная шкала событий | incident история + Kafka | `operational-timeline-api.yaml`; Operational State Machine |
| 5.10 Operational Audit | Аудит действий пользователя/системы/ИИ | audit-service (`audit.events`) | `contracts/admin/operational-audit-api.yaml`; аудит действий ИИ/рекомендаций |

## 4. Сквозная трассируемость (вызов → завершение)
```
call.received (telephony) → call.analyzed (ai: STT+extraction) → incident.created (incident)
  → [диспетчер подтверждает сущности/классификацию] → dispatch.assigned (dispatch, по утверждению)
  → unit.status-changed → incident.updated (IN_PROGRESS→RESOLVED→CLOSED) → audit.events (все шаги)
```
Каждый шаг фиксируется в Operational Timeline (5.9) и Operational Audit (5.10); связка по
`correlationId`/`traceId` (Operational Telemetry Standard). Топики — ADR-008.

## 5. Инварианты (обязательные проверки этапа)
- **ИИ никогда не принимает окончательное решение** — все результаты ИИ (классификация,
  извлечение, рекомендации) имеют статус «предложение» и требуют подтверждения диспетчера
  (AI Assistance Policy).
- **Все рекомендации требуют подтверждения** оператором (5.7/5.8; запрет автонаправления нарядов).
- **Любое изменение карточки фиксируется** (версии/история/аудит; запрет изменений в обход журнала).
- **Записи разговоров не удаляются в обход политик хранения** (Call Processing Standard, Backup Policy).
- **Решения — в пределах утверждённых регламентов** (Incident Classification / Decision Support Standards).

## 6. Связанные документы
Emergency Domain Model · Operational State Machine · Emergency Data Dictionary · Incident
Lifecycle / Dispatcher Workflow / Call Processing / Speech Processing / Incident Classification /
Operational Decision Support Standards · AI Assistance Policy · контракты `contracts/`.
