# Geospatial Data Model — ЕАСУР

- **Status:** APPROVED · Stage 6 · Подчиняется DMS, Canonical Data Model / Geospatial Data
  Governance Standards, ADR-003. Синхронизирован с реализацией (gis-service, dispatch-service).

## 1. Системы координат (CRS)
- Обмен/хранение координат — **WGS84 (EPSG:4326)**, lat/lon.
- Метрические расчёты (расстояния/буферы) — проекция (например, UTM зона Беларуси) или геодезия
  PostGIS. Правила — Coordinate Reference System Standard.

## 2. Пространственные объекты (единые идентификаторы UUID)

| Объект | Ключевые поля | Геометрия | Источник |
|--------|---------------|-----------|----------|
| Station (станция/часть) | id, name, serviceType (`ServiceType`), address, phone | Point | gis-service |
| ResponseZone (зона ответственности) | id, name, serviceType | Polygon | gis-service |
| GeoAddress (адрес) | id, нормализованные компоненты, координаты | Point | gis-service |
| AdminBoundary (адм. граница) | id, уровень, наименование | Polygon | gis (справочник) |
| Settlement (населённый пункт) | id, наименование | Point/Polygon | gis (справочник) |
| Street (улица) | id, наименование, населённый пункт | LineString | gis (справочник) |
| Building (здание) | id, адрес, номер | Polygon/Point | gis (справочник) |
| Unit (техника/наряд) | id, callSign, type (`UnitType`), status (`UnitStatus`), координаты | Point | dispatch-service |
| TelemetryTrack (трек) | id, unitId, точки (время/позиция) | LineString | resource-tracking |

`ServiceType` (gis): FIRE, MEDICAL, POLICE, GAS, RESCUE.
`UnitType` (dispatch): FIRE_TRUCK, LADDER_TRUCK, AMBULANCE, RESCUE_SQUAD, POLICE_PATROL,
GAS_SERVICE, HAZMAT_UNIT, WATER_RESCUE, COMMAND_VEHICLE.
`UnitStatus`: AVAILABLE, DISPATCHED, EN_ROUTE, ON_SCENE, RETURNING, OUT_OF_SERVICE.

## 3. Адресный реестр (6.1/6.2)
Компоненты адреса (нормализованные): регион, район, населённый пункт, улица, дом, корпус,
координаты. История изменений адресов — append-only (Address Registry Standard).

## 4. Маршруты и телеметрия (6.5/6.8)
- **Route:** origin/destination, геометрия LineString, ETA, расстояние, ограничения/объезды
  (единый сервис, ADR-014).
- **TelemetryTrack/Position:** позиция (Point), скорость/курс, время (UTC), источник (адаптер,
  ADR-015). Трек неизменяем (запрет удаления истории перемещений).

## 5. Правила данных (обязательные)
- Единые идентификаторы (UUID) у всех пространственных объектов.
- Изменение координат объекта — только с фиксацией (версия/история/аудит).
- Историю перемещений удалять запрещено (append-only).
- Персональные/чувствительные гео-данные — минимизация и защита (Geospatial Privacy Standard).
- Индексы: GiST для геометрий, `pg_trgm` для адресов (GIS Performance Standard, ADR-003).

## 6. Управление
Изменения модели/справочников — через RFC/ADR при влиянии на контракты/схему (Geospatial Data
Governance Standard, DGS); синхронизация с кодом и контрактами обязательна.
