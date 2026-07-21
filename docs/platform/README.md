# Платформенная документация ЕАСУР (Stage 3)

Архитектура и эксплуатация платформы, обеспечивающей разработку, тестирование, развёртывание,
масштабирование, наблюдаемость, резервирование, восстановление, безопасность и эксплуатацию
(без бизнес-логики). Подчиняется Architecture Baseline и EAGF.

## Архитектура платформы
- [Platform Architecture](platform-architecture.md)
- [Infrastructure Architecture](infrastructure-architecture.md)
- [Deployment Architecture](../deployment/deployment-architecture.md)
- [Network Architecture](network-architecture.md)
- [Security Architecture](../security/security-architecture.md)
- [Observability Architecture](observability-architecture.md)

## Возможности платформы (топики)
- [Secrets Management](secrets-management.md) · [IAM](iam.md)
- [Monitoring](monitoring-platform.md) · [Logging](logging-platform.md) · [Tracing](tracing-platform.md)
- [Message Platform](message-platform.md) · [Database Platform](database-platform.md)
- [Configuration Platform](configuration-platform.md) · [Service Discovery](service-discovery.md)
- [API Gateway Platform](api-gateway-platform.md) · [Container Platform](container-platform.md)
- [CI/CD Platform](cicd-platform.md) · [High Availability](high-availability.md)

## Эксплуатация и непрерывность
- [Backup Policy](backup-policy.md) · [Disaster Recovery Plan](disaster-recovery-plan.md)
- [Operational Procedures](operational-procedures.md) · [Runbooks](runbooks/README.md)
- [Platform Operations Framework](../governance/Platform-Operations-Framework.md)
- [Air-Gapped & Sovereign Deployment Standard](../standards/Air-Gapped-And-Sovereign-Deployment-Standard.md)

## Инфраструктура как код
- Terraform/Ansible/network/storage/scripts — [`infrastructure/`](../../infrastructure/README.md)
- Kubernetes платформа — [`kubernetes/platform/`](../../kubernetes/platform/README.md)
- ADR платформы: [ADR-010](../adr/ADR-010-iac-tooling.md) · [ADR-011](../adr/ADR-011-secrets-management.md) ·
  [ADR-012](../adr/ADR-012-distributed-tracing.md) · [ADR-013](../adr/ADR-013-object-storage.md)

Конкретные версии инструментов — единый каталог версий (`config/toolchain/versions.yaml`, TVMS).
