# Platform Runbooks — ЕАСУР

- **Status:** APPROVED · Stage 3 · Подчиняется Platform Operations Framework, Operational Procedures.

Runbooks — пошаговые инструкции реагирования на типовые эксплуатационные ситуации. Работают
автономно (доступны локально, AGSDS). Общий шаблон — `docs/templates/Runbook.template.md`.

## Каталог runbooks

| Runbook | Назначение |
|---------|-----------|
| [cluster-operations](cluster-operations.md) | Базовые операции с кластером, узлами, подами |
| [database-operations](database-operations.md) | Обслуживание БД, реплики, миграции, диагностика |
| [backup-restore](backup-restore.md) | Резервное копирование и восстановление |
| [secrets-operations](secrets-operations.md) | Vault: unseal, ротация, доступ, DR |
| [incident-response](incident-response.md) | Обработка эксплуатационного инцидента |

## Принципы
- Каждый runbook: симптом → диагностика → действия → проверка → эскалация.
- Команды безопасны и идемпотентны, где возможно.
- Привилегированные действия — под аудитом (Operational Security, POF).
