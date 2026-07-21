# Operational State Machine — ЕАСУР

- **Status:** APPROVED · Stage 5 · Синхронизирован с реализацией (SSOT): incident/telephony/dispatch.
  Изменение статусных моделей — только через RFC/ADR с обновлением кода и данного документа.

Единые статусные модели операционного домена. Значения соответствуют реальным перечислениям в коде.

## 1. Происшествие — `IncidentStatus` (incident-service)

```
RECEIVED → CLASSIFIED → DISPATCHED → IN_PROGRESS → RESOLVED → CLOSED
   │            │            │             │            │
   └────────────┴────────────┴─────────────┴──> CANCELLED (кроме терминальных)
                                            RESOLVED → IN_PROGRESS (возврат в работу)
```

Допустимые переходы (карта из `IncidentStatus`):
| Из | В |
|----|---|
| RECEIVED | CLASSIFIED, DISPATCHED, CANCELLED |
| CLASSIFIED | DISPATCHED, CANCELLED |
| DISPATCHED | IN_PROGRESS, CANCELLED |
| IN_PROGRESS | RESOLVED, CANCELLED |
| RESOLVED | CLOSED, IN_PROGRESS |
| CLOSED | — (терминальный) |
| CANCELLED | — (терминальный) |

Недопустимый переход → `IllegalStatusTransitionException` (HTTP 409). Конкурентные правки —
оптимистическая блокировка (`@Version`, HTTP 409). Каждый переход фиксируется в истории и аудите.

## 2. Вызов — `CallStatus` (telephony-service)

```
RINGING → ACTIVE → ON_HOLD → COMPLETED → TRANSCRIBED → ANALYZED
   └────────────────────────> MISSED (не отвечен)
```
Направление — `CallDirection`: INBOUND, OUTBOUND. Переход COMPLETED→TRANSCRIBED→ANALYZED
запускается обработкой речи (5.3) и извлечением (5.4).

## 3. Подразделение/наряд — `UnitStatus` (dispatch-service)

```
AVAILABLE → DISPATCHED → EN_ROUTE → ON_SCENE → RETURNING → AVAILABLE
                                                   └──> OUT_OF_SERVICE
```

## 4. Сущность извлечения — статус подтверждения (Stage 5, новое)

```
EXTRACTED (ИИ) → CONFIRMED (диспетчер) | REJECTED (диспетчер) | EDITED (диспетчер)
```
Результаты ИИ поступают со статусом `EXTRACTED` (предложение); окончательный статус присваивает
диспетчер (AI Assistance Policy). Не подтверждённые сущности не влияют на окончательную карточку.

## 5. Рекомендация (Stage 5, новое)

```
PROPOSED (система/ИИ) → APPROVED (диспетчер) | MODIFIED (диспетчер) | DECLINED (диспетчер)
```
Наряды направляются только после `APPROVED`/`MODIFIED` диспетчером (запрет автонаправления).

## 6. Правила
- Переходы валидируются на стороне владеющего сервиса; недопустимые отклоняются.
- Все переходы журналируются (Operational Audit) и попадают в Operational Timeline (5.9).
- Терминальные статусы неизменяемы (кроме RESOLVED→IN_PROGRESS для возврата в работу).
