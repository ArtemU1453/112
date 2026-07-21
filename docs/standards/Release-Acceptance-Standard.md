# Release Acceptance Standard — ЕАСУР

- **Status:** APPROVED · Stage 10 · Согласован с Release Governance Standard, Quality Gates, ADR-024,
  CI/CD (ADR-006/009).

## Назначение
Критерии приёмки конкретного релиза к развёртыванию в продуктив (10.1, 10.7). Дополняет Release
Governance Standard приёмочным gate'ом.

## Критерии приёмки релиза
- Все обязательные quality gates пройдены (сборка, тесты, статанализ, покрытие — Quality Gates).
- Безопасность релиза: секрет-скан, скан образов/зависимостей, SBOM (CycloneDX) без блокирующих находок.
- Обратная совместимость контрактов подтверждена (API Versioning / Contract Governance).
- Изменения прослеживаются к RFC/ADR/требованиям (трассируемость, EAGF).
- Release notes и план отката подготовлены; артефакты неизменяемы и подписаны.
- Развёртывание проверено на staging (идентичность окружений — EGS).

## Процесс
Кандидат релиза → приёмочные проверки → решение о принятии → продвижение через ручной gate
(Release Governance) → развёртывание по Deployment Program/Go-Live Playbook.

## Definition of Done
Определены приёмочные критерии релиза и связь с quality gates, безопасностью и планом отката.
