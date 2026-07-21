# BI Architecture — ЕАСУР

- **Status:** APPROVED · Stage 8 · ADR-020 · Подчиняется Analytics Architecture, BI Security /
  Executive Dashboard / Dashboard Design Standards.

## Назначение
BI и визуализация (`CAP-BI-001`) поверх DWH (ADR-019): дашборды для всех уровней управления,
real-time и историческая аналитика, drill-down, фильтрация, экспорт. Self-hosted (суверенность).

## Компоненты
- **Real-time дашборды** — Grafana (Stage 3) поверх Prometheus/Loki (оперативные показатели).
- **Управленческая аналитика** — self-hosted BI (Metabase/Superset) поверх DWH.
- **API дашбордов** — единый контракт для рендера показателей в АРМ/ситуационном центре
  (`contracts/internal/dashboards-api.yaml`), чтобы клиенты получали данные единообразно.

## Уровни управления (8.2)
Диспетчер · старший диспетчер · оперативный дежурный · руководитель гарнизона · областное
управление · центральный аппарат. Каждый уровень — свой набор показателей и область видимости
(RBAC, BI Security Standard).

## Возможности
Показатели в реальном времени; исторические данные; сравнение периодов; drill-down; фильтрация;
экспорт (Dashboard Design Standard).

## Безопасность
Доступ к дашбордам/данным — по RBAC и уровню управления (BI Security Standard); чувствительные
данные — минимизация/обезличивание (Privacy стандарты). Данные защищены при хранении/передаче.

## Единость
Единая BI-платформа для всех уровней — **без дублирующих аналитических платформ** (запрет этапа).

## Связанные документы
Analytics/DWH Architecture · National Situation Center Architecture · Executive Dashboard/Dashboard
Design/BI Security Standards · ADR-020.
