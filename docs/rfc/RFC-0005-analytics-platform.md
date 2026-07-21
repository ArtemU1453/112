# RFC-0005 — Аналитическая платформа

- **Статус:** Closed (Approved → Implemented → Verified)
- **Автор:** Analytics/Architecture Review (Stage 8)
- **Дата:** 2026-07-21
- **Связанные ADR:** ADR-019 (DWH), ADR-020 (BI), ADR-021 (AI-аналитика)
- **Связанные документы:** Technology Mapping (`CAP-DWH-001`, `CAP-BI-001`, `CAP-AIANALYTICS-001`),
  Analytics/BI/DWH/Situation Center Architecture, стандарты Stage 8

## 1. Причина изменения
Stage 8 создаёт единое аналитическое пространство (аналитика, BI, DWH, отчётность, прогнозирование,
ситуационный центр, качество данных, аналитический ИИ). Требуется утвердить хранилище, BI и
абстракцию аналитического ИИ без нарушения Architecture Freeze.

## 2. Существующее решение
Оперативные сервисы (Stage 0), наблюдаемость Grafana/Prometheus/Loki (Stage 3), плейсхолдер
`services/analytics-service`. Отсутствовали DWH, BI-дашборды уровней управления, аналитический ИИ.

## 3. Предлагаемое решение
DWH из событий/снапшотов (ADR-019); self-hosted BI (ADR-020); абстракция аналитического ИИ с
объяснимостью и методикой (ADR-021). Единый `analytics-service` — без дублирующих платформ.

## 4. Impact Analysis
- Модули/сервисы: без изменения кода оперативных сервисов; `analytics-service` реализует платформу.
- API: новые контракты `contracts/*` (analytics, kpi, dashboards, decision-intelligence, reporting,
  data-warehouse, forecasting, situation-center, data-quality, ai-analytics), API First.
- БД: отдельное аналитическое хранилище (ADR-019); оперативные БД не затронуты.
- Безопасность: BI Security Standard (RBAC по уровням); данные защищены (Security Architecture).

## 5. Compatibility Check
Обратная совместимость сохранена; миграция данных не требуется; версии API — новые; исторические
данные неизменяемы; документация синхронизирована.

## 6. Риски
Нагрузка/объёмы аналитики (R-T3), дублирование данных, качество прогнозов. Снижение — изоляция DWH,
Data Quality/Lineage/Forecast Validation стандарты.

## 7. Migration Plan
Подготовка (ADR-019..021, каталог) → реализация (контракты, витрины, дашборды, методики) →
проверка (валидаторы/OpenAPI) → внедрение ETL/дашбордов → мониторинг → завершение.

## 8. Rollback Plan
Откат контрактов/конфигураций из VCS; оперативный контур и его данные не затрагиваются.

## 9. Document Synchronization
ADR-019..021, ADR Index, RFC Index, Technology Mapping, каталог версий, docs/README.

## 10. Definition of Done для RFC
- [x] ADR созданы (019, 020, 021)
- [x] Impact Analysis выполнен
- [x] Migration/Rollback планы готовы
- [x] Документация синхронизирована
- [x] Реализовано и проверено (Verified)
