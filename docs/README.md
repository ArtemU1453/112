# Документация ЕАСУР — индекс

> ЕАСУР — Единая автоматизированная система управления реагированием (112/МЧС РБ).
> Архитектурный фундамент (Stage 1). Baseline: v1.0 · Status: APPROVED.

Архитектурная документация — **Single Source of Truth**: имеет приоритет над кодом. При
противоречии действует порядок приоритетов из Project Constitution.

## Single Source of Truth — порядок приоритета

1. [Vision](Vision.md)
2. [Architecture Principles](architecture/principles.md)
3. [Architecture.md](Architecture.md)
4. [ADR](adr/README.md)
5. [RFC](rfc/README.md)
6. Domain Model → [Domain Modeling Standard](standards/domain-modeling-standard.md)
7. API Contracts → [API Governance Standard](standards/api-governance-standard.md)
8. Database Schema → [Database Governance Standard](standards/database-governance-standard.md)
9. [Coding Standards](standards/coding-standards.md)
10. Исходный код
11. Комментарии в коде

## Управление проектом (Governance)

- [Project Constitution](governance/project-constitution.md) — фундаментальные правила
- [Architecture Baseline](governance/architecture-baseline.md) — эталон архитектуры, Freeze, Drift
- [Engineering Handbook](governance/engineering-handbook.md) — инженерные процессы

## Архитектура

- [Vision](Vision.md) · [Architecture.md](Architecture.md)
- [Architecture Principles](architecture/principles.md) · [System Structure](architecture/system-structure.md)
- [ADR Index](adr/README.md) (ADR-001..008) · [RFC Process](rfc/README.md) · [RFC Template](rfc/RFC-template.md)
- Реализационные материалы: [architecture.md](architecture.md) (карта событий/потоков),
  [operations.md](operations.md) (эксплуатация)

## Стандарты

- [Development Guidelines](standards/development-guidelines.md) (именование)
- [Coding Standards](standards/coding-standards.md)
- [Git Strategy](standards/git-strategy.md)
- [Definition of Ready](standards/definition-of-ready.md) · [Definition of Done](standards/definition-of-done.md)
- [Backlog Structure](standards/backlog-structure.md) · [Quality Gates](standards/quality-gates.md)
- [Requirements Management (RMS)](standards/requirements-management-standard.md)
- [Domain Modeling (DMS)](standards/domain-modeling-standard.md)
- [API Governance (AGS)](standards/api-governance-standard.md)
- [Database Governance (DGS)](standards/database-governance-standard.md)

## Риски

- [Risk Register](risk/risk-register.md)

## Разделы (наполняются на последующих этапах)

- [business/](business/) · [deployment/](deployment/) · [database/](database/) ·
  [api/](api/) · [security/](security/) · [testing/](testing/)

---

Изменение любого утверждённого документа выполняется только через [RFC](rfc/README.md) с
обновлением связанного [ADR](adr/README.md) и синхронизацией зависимой документации.
