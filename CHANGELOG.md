# Changelog — ЕАСУР

Формат основан на «Keep a Changelog»; проект следует Semantic Versioning
(Release Governance Standard). Разделы релиза: новые возможности, исправления,
изменения безопасности, изменения зависимостей, известные ограничения.

## [Unreleased]

### Инженерная основа (Stage 2)
- Монорепозиторная структура, каталог версий (SSOT), governance-стандарты (RGS, DepGS, RLS,
  TGS, EGS), Technology Abstraction Policy, EAGF.
- GitHub Actions (build/test/lint/security/dependency-scan/docker-build/release/validation),
  Dev Container, конфигурации качества, Kubernetes (Kustomize) и Docker-окружения, скрипты.
- ADR-009 (основная CI-платформа: GitHub Actions) + RFC-0001.

### Архитектурный фундамент (Stage 1)
- Vision, Project Constitution, Architecture Baseline, Engineering Handbook, ADR-001..008,
  RFC-процесс, стандарты (Coding/Git/DoR/DoD/Quality Gates/RMS/DMS/AGS/DGS), Risk Register.

### Система (реализация)
- 9 Java-микросервисов, ai-service (FastAPI), АРМ диспетчера (React), инфраструктура,
  Helm-чарт, docker-compose.

[Unreleased]: https://github.com/ArtemU1453/112/commits/main
