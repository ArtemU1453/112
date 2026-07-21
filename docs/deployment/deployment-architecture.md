# Deployment Architecture — ЕАСУР

- **Status:** APPROVED · Stage 3 · Подчиняется Platform/Infrastructure Architecture, Release
  Governance Standard, Environment Governance Standard.

## 1. Конвейер развёртывания (CI/CD Platform)
Стадии (реализуются GitHub Actions — осн., ADR-009; и GitLab CI — ADR-006):
`build → unit tests → integration tests → security scan → SBOM → dependency scan →
container build → container scan → deployment → rollback → release`.
Продвижение в production — ручной gate (Release Governance Standard). Версии — каталог (TVMS).

## 2. Порядок развёртывания платформы «с нуля»
1. Подготовка узлов/ОС/рантайма/реестра — Ansible (`infrastructure/ansible/site.yml`).
2. Платформенный слой Kubernetes — Terraform (`infrastructure/terraform/environments/<env>`).
3. Платформенные манифесты — Kustomize (`kubectl apply -k kubernetes/platform`).
4. Платформенные компоненты (операторы/чарты): ingress-nginx, cert-manager, External Secrets +
   Vault, Prometheus/Grafana/Loki/Tempo/OTel Collector, объектное хранилище.
5. Приложения — Helm-чарт `helm/emergency-112` (вне объёма Stage 3).
Автоматизация — `infrastructure/scripts/platform-bootstrap.sh`.

## 3. Окружения (EGS)
`Local → Development → Integration → QA → UAT → Staging → Production` (+ DR). Оверлеи —
`kubernetes/overlays/{development,test,stage,production}`; отличия — масштаб/ресурсы/endpoint'ы/
feature flags; идентичны — версии, схемы, контракты (EGS «Синхронизация»).

## 4. Стратегия выката
- Rolling update (по умолчанию) с readiness/liveness-пробами.
- HPA для приложений (масштабирование по нагрузке).
- Откат — `helm rollback` / повторное применение предыдущего манифеста; критерии и шаги —
  Release Governance Standard, Runbooks.

## 5. Артефакты и реестр
Неизменяемые образы (тег = SHA + канал), подпись и сканирование (Security Architecture), SBOM
(CycloneDX). В air-gapped — доставка через офлайн-пакет (`build-offline-bundle.sh`, AGSDS).

## 6. Проверки после развёртывания
Health/readiness всех подов; Prometheus targets; smoke-проверки; согласованность конфигураций
(EGS). Чек-лист — Operational Procedures.

## 7. Связанные документы
Platform/Infrastructure/Network/Security/Observability Architecture · Disaster Recovery Plan ·
Backup Policy · Platform Operations Framework · Air-Gapped Standard.
