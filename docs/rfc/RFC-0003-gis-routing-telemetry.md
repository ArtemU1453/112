# RFC-0003 — GIS-платформа: маршрутизация, картография, телеметрия

- **Статус:** Closed (Approved → Implemented → Verified)
- **Автор:** GIS/Architecture Review (Stage 6)
- **Дата:** 2026-07-21
- **Связанные ADR:** ADR-014 (маршрутизация), ADR-015 (картография/телеметрия)
- **Связанные документы:** Technology Mapping (`CAP-ROUTING-001`, `CAP-MAP-001`,
  `CAP-TELEMETRY-001`), GIS Architecture, Routing/Telemetry/Map стандарты

## 1. Причина изменения
Stage 6 создаёт пространственную платформу. Требуется утвердить абстракции маршрутизации,
картографических данных и телеметрии без привязки к поставщику (явный запрет этапа) и с
суверенностью (AGSDS), не нарушая Architecture Freeze.

## 2. Существующее решение
gis-service (PostGIS: геокодирование, ближайшие станции, зоны — ADR-003) и dispatch-service
(наряды/автоподбор). Отсутствовали единые маршрутизация, мультиисточниковая телеметрия и
провайдер-абстракция карт.

## 3. Предлагаемое решение
Утвердить: единый сервис маршрутизации через порт `RoutingProvider` (self-hosted OSS-движок);
провайдер-абстракции `MapDataProvider` и `TelemetrySource`. Оформить ADR-014/015; добавить
возможности в Technology Mapping.

## 4. Impact Analysis
- Модули/сервисы: без изменения кода существующих сервисов (Stage 6 — доменный слой + контракты).
- API: новые контракты `contracts/internal/*` (routing, tracking, map, analytics, …), API First.
- БД: гео-данные — PostGIS (ADR-003); новые сущности описаны в Geospatial Data Model.
- Frontend: оперативная карта — OpenLayers поверх self-hosted слоёв (ADR-004/015).
- Безопасность: Spatial Security / Geospatial Privacy Standards; история перемещений append-only.

## 5. Compatibility Check
Обратная совместимость сохранена; миграция данных не требуется; версии API — новые; клиенты —
через контракты; документация синхронизирована.

## 6. Риски
Эксплуатация маршрутного движка/тайлов и гео-данных (R-T1/R-T3). Снижение — стандартные OSS,
Geospatial Data Governance / GIS Performance Standards.

## 7. Migration Plan
Подготовка (ADR-014/015, каталог) → реализация (контракты, доменный слой) → проверка
(валидаторы/OpenAPI) → внедрение адаптеров → мониторинг → завершение.

## 8. Rollback Plan
Откат контрактов/адаптеров из VCS; существующие gis/dispatch не затрагиваются.

## 9. Document Synchronization
ADR-014/015, ADR Index, RFC Index, Technology Mapping, каталог версий, docs/README.

## 10. Definition of Done для RFC
- [x] ADR созданы (014, 015)
- [x] Impact Analysis выполнен
- [x] Migration/Rollback планы готовы
- [x] Документация синхронизирована
- [x] Реализовано и проверено (Verified)
