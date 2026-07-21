# CI/CD Platform — ЕАСУР

- **Status:** APPROVED · Stage 3 · ADR-006/009 · Подчиняется Release Governance Standard, Quality Gates.

## Конвейер
`build → unit tests → integration tests → security scan → SBOM → dependency scan →
container build → container scan → deployment → rollback → release`.
Реализация — GitHub Actions (осн., ADR-009) + GitLab CI (ADR-006). Версии — каталог (TVMS).

## Quality Gates
Code Review, Unit/Integration тесты, Static Analysis, Security Scan, Documentation/Architecture
validation (Quality Gates). Продвижение в production — ручной gate.

## Артефакты
Неизменяемые образы (тег SHA+канал), SBOM (CycloneDX), подпись; хранение в реестре/объектном
хранилище. В air-gapped — офлайн-пакет (AGSDS).

## Развёртывание и откат
Rolling update; откат `helm rollback`/повторное применение манифеста. Критерии — Release
Governance Standard, Runbooks.

## Автономность
Для изолированного контура — self-hosted runners и локальные зеркала зависимостей (AGSDS).
