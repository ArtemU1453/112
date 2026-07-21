# GIS Architecture — ЕАСУР

- **Status:** APPROVED · Stage 6 · Подчиняется Architecture.md, Core Platform Architecture, EAGF,
  ADR-003/014/015. Пространственная платформа управления экстренным реагированием.

## 1. Назначение
Отображение происшествий, управление силами и средствами, пространственный анализ, навигация,
рекомендации по выбору подразделений, контроль выполнения реагирования. **Автоматические решения
вместо диспетчера запрещены** (система рекомендует).

## 2. Реализованная основа (Stage 0) и новое (Stage 6)
gis-service (PostGIS: геокодирование, ближайшие станции KNN, зоны ответственности) и
dispatch-service (наряды `UnitType`, автоподбор) реализуют часть 6.1–6.9 и **не дублируются**
(Architecture Freeze, SSOT). Stage 6 добавляет доменный слой (GIS Architecture, Geospatial Data
Model), 20 стандартов и **API-First контракты** для новых возможностей: маршрутизация,
телеметрия-трекинг, геоаналитика, address intelligence — с провайдер-абстракциями (ADR-014/015).

## 3. Карта подэтапов 6.1–6.10

| Подэтап | Возможность | Основа (Stage 0) | Новое (контракт/стандарт) |
|---------|-------------|------------------|----------------------------|
| 6.1 GIS Platform | Карта/слои/объекты/адресный реестр/CRS/поиск | gis-service (PostGIS) | `gis-api.yaml`; CRS / Map Layer Governance Standards |
| 6.2 Address Intelligence | Нормализация/проверка/геокодирование/reverse | gis-service (geocode/reverse) | `address-intelligence-api.yaml`; Address Intelligence / Registry Standards |
| 6.3 Resource Registry | Части/техника/расчёты/оборудование | dispatch-service (Unit) | `resource-registry-api.yaml`; Resource Registry Standard |
| 6.4 Resource Availability | Состояние/загрузка/резерв/прогноз | dispatch-service (`UnitStatus`) | `resource-availability-api.yaml`; Resource Availability Standard |
| 6.5 Routing | Маршруты/альтернативы/ETA/объезды | — (новое, ADR-014) | `routing-api.yaml`; Routing / Routing Governance Standards |
| 6.6 Dispatch Recommendation | Рекомендации нарядов | dispatch (автоподбор) | `dispatch-recommendation-api.yaml`; Dispatch Recommendation Standard |
| 6.7 Operational Map | Инциденты/наряды/маршруты/зоны/фильтр/timeline | frontend + gis/dispatch | `operational-map-api.yaml`; Operational Mapping Standard |
| 6.8 Resource Tracking | Телеметрия местоположения/движение/прибытие | — (новое, ADR-015) | `resource-tracking-api.yaml`; Telemetry Integration/Abstraction Standards |
| 6.9 Operational Coordination | Назначение/усиление/передача/отмена/мультислужба | dispatch (Assignment) | `operational-coordination-api.yaml`; Operational Geography Standard |
| 6.10 Geospatial Analytics | Тепловые карты/покрытие/ETA/плотность/прогноз | — (новое) | `geospatial-analytics-api.yaml`; Spatial Analytics Governance Standard |

## 4. Слои GIS-платформы
- **Данные:** PostGIS (ADR-003) — геометрии, адресный реестр, зоны; CRS — WGS84 (EPSG:4326) для
  обмена, метрическая проекция для расчётов (CRS Standard).
- **Картография:** self-hosted слои/тайлы (`MapDataProvider`, ADR-015); клиент — OpenLayers (ADR-004).
- **Маршрутизация:** единый сервис (`RoutingProvider`, ADR-014).
- **Телеметрия:** мультиисточник через `TelemetrySource` (ADR-015), отделена от прикладной логики.
- **Аналитика:** пространственные агрегаты поверх PostGIS/данных (Spatial Analytics Governance).

## 5. Инварианты (обязательные проверки этапа)
- Все пространственные объекты — единые идентификаторы (UUID) (Geospatial Data Model).
- Адреса проходят нормализацию и проверку (Address Intelligence Standard).
- Маршруты — только через единый сервис (ADR-014).
- Рекомендации не выполняются автоматически; направление нарядов — по подтверждению диспетчера.
- Все действия журналируются; изменения отражаются на оперативной карте.
- Телеметрия — через адаптеры (отделена от логики).
- Координаты меняются только с фиксацией; историю перемещений удалять запрещено.
- Нет привязки к поставщику карт/навигации (TAP, ADR-014/015).

## 6. Связанные документы
Geospatial Data Model · 20 стандартов Stage 6 · контракты `contracts/` · Emergency Operations
Platform Architecture (Stage 5) · Security/Observability Architecture.
