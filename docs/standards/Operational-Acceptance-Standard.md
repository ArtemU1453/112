# Operational Acceptance Standard — ЕАСУР

- **Status:** APPROVED · Stage 10 · Согласован с ADR-024, Production Readiness Standard, Service
  Acceptance Standard, Platform Operations Framework.

## Назначение
Правила приёмки системы/сервиса в эксплуатацию эксплуатирующей стороной (Operational Readiness
Review, 10.3). Приёмка подтверждает, что систему можно устойчиво эксплуатировать.

## Критерии приёмки
- Готовность подтверждена (Production Readiness Standard); критических дефектов нет.
- Эксплуатационная документация готова: Operations Handbook, Support Handbook, Production Runbook.
- Наблюдаемость и алертинг настроены; дежурство и эскалация (Incident Escalation Standard) определены.
- Резервное копирование/восстановление и DR проверены (DR Certification).
- Обучение эксплуатирующего/поддерживающего персонала проведено (Training Handbook).
- SLA/SLO согласованы; окна обслуживания определены (Maintenance Window Standard).

## Процесс (ORR)
Заявка на приёмку → проверка критериев комиссией → фиксация замечаний → устранение блокеров →
**решение о приёмке** (принять / принять с условиями / отклонить) → неизменяемая запись решения.

## Definition of Done
Определены критерии, процесс и орган операционной приёмки; решение фиксируется в аудите.
