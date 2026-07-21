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
- [Enterprise Architecture Governance Framework](governance/Enterprise-Architecture-Governance-Framework.md) — пирамида документов, контрольные точки, трассируемость

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

### Инженерная основа (Stage 2)

- [Repository Governance Standard](standards/Repository-Governance-Standard.md)
- [Dependency Governance Standard (DepGS)](standards/Dependency-Governance-Standard.md)
- [Release Governance Standard](standards/Release-Governance-Standard.md)
- [Toolchain Governance Standard](standards/Toolchain-Governance-Standard.md)
- [Environment Governance Standard](standards/Environment-Governance-Standard.md)
- [Technology Abstraction Policy (TAP)](standards/Technology-Abstraction-Policy.md) · [Technology Mapping](architecture/technology-mapping.md)
- [Logging Standards](standards/logging-standards.md) · [Configuration Standards](standards/configuration-standards.md)
- [Testing Strategy](standards/testing-strategy.md) · [Naming Standards](standards/naming-standards.md) · [Security Standards](standards/security-standards.md)
- Каталог версий (SSOT): [`config/toolchain/`](../config/toolchain/README.md)
- Шаблоны документов: [`docs/templates/`](templates/) · Гайды разработчика: [`docs/developer-experience/`](developer-experience/quick-start.md)

## Платформа (Stage 3)

- [Платформенная документация — индекс](platform/README.md)
- Архитектура: [Platform](platform/platform-architecture.md) · [Infrastructure](platform/infrastructure-architecture.md) ·
  [Deployment](deployment/deployment-architecture.md) · [Network](platform/network-architecture.md) ·
  [Security](security/security-architecture.md) · [Observability](platform/observability-architecture.md)
- Эксплуатация: [Backup Policy](platform/backup-policy.md) · [Disaster Recovery Plan](platform/disaster-recovery-plan.md) ·
  [Operational Procedures](platform/operational-procedures.md) · [Runbooks](platform/runbooks/README.md) ·
  [Platform Operations Framework](governance/Platform-Operations-Framework.md)
- [Air-Gapped & Sovereign Deployment Standard](standards/Air-Gapped-And-Sovereign-Deployment-Standard.md)
- IaC: [`infrastructure/`](../infrastructure/README.md) · [`kubernetes/platform/`](../kubernetes/platform/README.md)

## Core Platform (Stage 4)

- Архитектура: [Core Platform](core-platform/core-platform-architecture.md) ·
  [Identity](core-platform/identity-architecture.md) · [Integration](core-platform/integration-architecture.md) ·
  [Messaging](core-platform/messaging-architecture.md) · [Security](core-platform/security-architecture.md) ·
  [AI Integration](core-platform/ai-integration-architecture.md)
- Разработчику: [SDK Documentation](core-platform/sdk-documentation.md) ·
  [Common Libraries Guide](core-platform/common-libraries-guide.md) · [API Guidelines](core-platform/api-guidelines.md)
- Библиотеки: [`libraries/platform-commons`](../libraries/platform-commons/README.md) ·
  [`platform-sdk`](../libraries/platform-sdk/README.md) · порты `platform-abstractions`
- Контракты API (API First): [`contracts/`](../contracts/README.md)
- Стандарты Stage 4: [Shared Domain Kernel](standards/Shared-Domain-Kernel-Standard.md) ·
  [Internal SDK Governance](standards/Internal-SDK-Governance-Standard.md) ·
  [API Lifecycle](standards/API-Lifecycle-Management-Standard.md) ·
  [Event Governance](standards/Event-Governance-Standard.md) ·
  [Canonical Data Model](standards/Canonical-Data-Model-Standard.md) ·
  [Integration Governance](standards/Integration-Governance-Standard.md) ·
  [AI Provider Abstraction](standards/AI-Provider-Abstraction-Standard.md) ·
  [Document Lifecycle](standards/Document-Lifecycle-Management-Standard.md) ·
  [Operational Telemetry](standards/Operational-Telemetry-Standard.md) ·
  [Service Maturity Model](standards/Service-Maturity-Model.md)

## Emergency Operations Platform (Stage 5)

- [Emergency Operations Platform Architecture](emergency/emergency-operations-platform-architecture.md) — маппинг 5.1–5.10, трассируемость
- [Emergency Domain Model](emergency/emergency-domain-model.md) · [Operational State Machine](emergency/operational-state-machine.md) ·
  [Emergency Data Dictionary](emergency/emergency-data-dictionary.md)
- Стандарты: [Incident Lifecycle](standards/Incident-Lifecycle-Standard.md) ·
  [Dispatcher Workflow](standards/Dispatcher-Workflow-Standard.md) · [Call Processing](standards/Call-Processing-Standard.md) ·
  [Speech Processing](standards/Speech-Processing-Standard.md) · [AI Assistance Policy](standards/AI-Assistance-Policy.md) ·
  [Operational Decision Support](standards/Operational-Decision-Support-Standard.md) ·
  [Incident Classification](standards/Incident-Classification-Standard.md)
- Контракты операций (API First): [`contracts/internal`](../contracts/README.md), [`contracts/admin`](../contracts/README.md)

## GIS & Resource Management (Stage 6)

- [GIS Architecture](gis/gis-architecture.md) — маппинг 6.1–6.10, инварианты · [Geospatial Data Model](gis/geospatial-data-model.md)
- ADR: [ADR-014 Routing/Navigation](adr/ADR-014-routing-navigation-abstraction.md) · [ADR-015 Cartography/Telemetry](adr/ADR-015-cartographic-telemetry-provider-abstraction.md); [RFC-0003](rfc/RFC-0003-gis-routing-telemetry.md)
- Стандарты (обязательные): [Routing](standards/Routing-Standard.md) · [Address Intelligence](standards/Address-Intelligence-Standard.md) ·
  [Resource Registry](standards/Resource-Registry-Standard.md) · [Operational Mapping](standards/Operational-Mapping-Standard.md) ·
  [Dispatch Recommendation](standards/Dispatch-Recommendation-Standard.md) · [Telemetry Integration](standards/Telemetry-Integration-Standard.md) ·
  [Spatial Security](standards/Spatial-Security-Standard.md) · [Geospatial Data Governance](standards/Geospatial-Data-Governance-Standard.md)
- Стандарты (дополнительные): [CRS](standards/Coordinate-Reference-System-Standard.md) · [Map Layer Governance](standards/Map-Layer-Governance-Standard.md) ·
  [Address Registry](standards/Address-Registry-Standard.md) · [Routing Governance](standards/Routing-Governance-Standard.md) ·
  [Resource Availability](standards/Resource-Availability-Standard.md) · [Operational Geography](standards/Operational-Geography-Standard.md) ·
  [Telemetry Abstraction](standards/Telemetry-Abstraction-Standard.md) · [GIS Performance](standards/GIS-Performance-Standard.md) ·
  [Geospatial Privacy](standards/Geospatial-Privacy-Standard.md) · [Spatial Analytics Governance](standards/Spatial-Analytics-Governance-Standard.md)
- Контракты 6.1–6.10 (API First): [`contracts/internal`](../contracts/README.md)

## Риски

- [Risk Register](risk/risk-register.md)

## Разделы (наполняются на последующих этапах)

- [business/](business/) · [deployment/](deployment/) · [database/](database/) ·
  [api/](api/) · [security/](security/) · [testing/](testing/)

---

Изменение любого утверждённого документа выполняется только через [RFC](rfc/README.md) с
обновлением связанного [ADR](adr/README.md) и синхронизацией зависимой документации.
