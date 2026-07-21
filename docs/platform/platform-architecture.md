# Platform Architecture — ЕАСУР

- **Status:** APPROVED · Baseline: v1.0 · Stage 3
- Подчиняется Architecture.md, Architecture Baseline, EAGF. Описывает платформу, обеспечивающую
  разработку, тестирование, развёртывание, масштабирование, наблюдаемость, резервирование,
  восстановление, безопасность и эксплуатацию системы (без бизнес-логики).

## 1. Назначение
Платформа — набор инфраструктурных возможностей, на которых работают прикладные сервисы
(bounded contexts). Платформа отделена от приложений: её эволюция управляется отдельно, а
приложения потребляют её возможности через стандартные интерфейсы.

## 2. Возможности платформы (capability-first, TAP)

| Возможность | Capability ID | Технология (ADR) |
|-------------|---------------|------------------|
| Оркестрация контейнеров | CAP-ORCH-001 | Kubernetes (ADR-005) |
| Управление релизами в кластер | CAP-RELEASE-001 | Helm (ADR-005) |
| Infrastructure as Code | CAP-IAC-001 | Terraform + Ansible (ADR-010) |
| Реляционная/гео БД | CAP-DB-001/002 | PostgreSQL + PostGIS (ADR-003) |
| Кэш | CAP-CACHE-001 | Redis (ADR-003) |
| Брокер сообщений | CAP-MSG-001 | Apache Kafka (ADR-008) |
| Identity Provider | CAP-IDP-001 | Keycloak (ADR-007) |
| Управление секретами | CAP-SECRETS-001 | Vault + External Secrets (ADR-011) |
| Объектное хранилище | CAP-STORAGE-001 | S3-совместимое self-hosted (ADR-013) |
| Метрики / логи / визуализация | CAP-OBS-001/002/003 | Prometheus / Loki / Grafana |
| Распределённая трассировка | CAP-TRACE-001 | OpenTelemetry + Tempo (ADR-012) |
| Реестр образов (локальный) | CAP-REGISTRY-001 | OCI-реестр self-hosted (ADR-005/010) |
| CI/CD | CAP-CI-001 | GitHub Actions + GitLab CI (ADR-006/009) |

## 3. Слои платформы

```
┌──────────────────────────────────────────────────────────────┐
│ Приложения (bounded contexts) — вне объёма Stage 3            │
├──────────────────────────────────────────────────────────────┤
│ Платформенные сервисы: Ingress/Gateway, IdP, секреты (Vault), │
│   наблюдаемость (Prometheus/Loki/Grafana/Tempo/OTel)          │
├──────────────────────────────────────────────────────────────┤
│ Данные: PostgreSQL/PostGIS, Kafka, Redis, объектное хранилище │
├──────────────────────────────────────────────────────────────┤
│ Оркестрация: Kubernetes (namespaces, RBAC, NetworkPolicy,     │
│   квоты, Pod Security, StorageClass)                          │
├──────────────────────────────────────────────────────────────┤
│ Инфраструктура: узлы/ОС, контейнерный рантайм, сеть, хранилище│
│   (Terraform + Ansible; on-prem / air-gapped)                 │
└──────────────────────────────────────────────────────────────┘
```

## 4. Namespaces и сегментация
- `emergency-112` — прикладные сервисы (Pod Security: restricted в prod).
- `emergency-112-data` — БД/брокер/кэш/объектное хранилище.
- `emergency-112-platform` — секреты (Vault), External Secrets, платформенные компоненты.
- `emergency-112-monitoring` — Prometheus/Grafana/Loki/Tempo/OTel Collector.
Сетевые зоны и потоки — Network Architecture; политики — `kubernetes/platform/networking`.

## 5. Принципы платформы
- **IaC:** всё воспроизводимо (Terraform + Ansible + Kustomize/Helm); ARTICLE 3.
- **Security by Design:** Pod Security, RBAC, NetworkPolicy, централизованные секреты, аудит.
- **Observability by Design:** метрики/логи/трассы/события/health для всех компонентов.
- **HA by default:** реплики, автоскейлинг (HPA), самовосстановление Kubernetes.
- **Sovereignty:** суверенное, self-hosted, поддержка air-gapped (AGSDS).

## 6. Связанные документы
Infrastructure Architecture · Deployment Architecture · Network Architecture · Security
Architecture · Observability Architecture · Disaster Recovery Plan · Backup Policy · Platform
Operations Framework · Air-Gapped Standard. Конкретные версии — каталог версий (TVMS).
