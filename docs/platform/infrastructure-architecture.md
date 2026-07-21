# Infrastructure Architecture — ЕАСУР

- **Status:** APPROVED · Stage 3 · Подчиняется Platform Architecture.

## 1. Целевая среда
On-premise Kubernetes в защищённом (в т. ч. air-gapped) контуре. Без привязки к публичному
облаку. Управление — IaC (Terraform + Ansible, ADR-010).

## 2. Вычислительные ресурсы
- **Узлы:** control plane (HA, ≥3) + worker-узлы. Подготовка ОС/рантайма — Ansible.
- **Контейнерный рантайм:** containerd (systemd cgroups), локальное зеркало реестра (air-gapped).
- **Оркестрация:** Kubernetes; квоты/limit ranges по namespace (Capacity Management).

## 3. Хранилище
- **Блочное/файловое:** StorageClass `easur-standard` (по умолчанию), `easur-fast` для БД;
  `reclaimPolicy=Retain`, `WaitForFirstConsumer`, `allowVolumeExpansion=true`.
- **Объектное:** S3-совместимое self-hosted (ADR-013) — бэкапы, артефакты, SBOM, локальные зеркала.
- Политики — `infrastructure/storage/storage-policy.yaml`; классы — `kubernetes/platform/storage`.

## 4. Сеть
Сегментация по зонам (edge/application/data/platform), default-deny + явные разрешения
(NetworkPolicy). Детали — Network Architecture.

## 5. IaC-слои
| Слой | Инструмент | Артефакты |
|------|-----------|-----------|
| Узлы/ОС/рантайм/реестр | Ansible | `infrastructure/ansible` (роли/плейбуки) |
| Платформенный слой K8s | Terraform (провайдеры kubernetes/helm) | `infrastructure/terraform` |
| Манифесты платформы | Kustomize | `kubernetes/platform`, `kubernetes/overlays` |
| Приложения | Helm | `helm/emergency-112` |

## 6. Реестр образов
Локальный OCI-реестр (`CAP-REGISTRY-001`) как зеркало для изолированного контура (AGSDS).
Политики образов (подпись, сканирование, жизненный цикл) — Container Platform (Security
Architecture, CI/CD).

## 7. Управление временем
Синхронизация через локальные NTP-источники (`chrony`), критично для изолированных площадок
(AGSDS). При недоступности источников — удержание по drift-файлу и оповещение.

## 8. Воспроизводимость и соответствие
Развёртывание «с нуля» — `infrastructure/scripts/platform-bootstrap.sh` (Ansible → Terraform →
Kustomize → Helm). Версии инструментов — каталог версий (TVMS). Соответствие EGS/RGS/AGSDS.
