# Emergency Data Dictionary — ЕАСУР

- **Status:** APPROVED · Stage 5 · Подчиняется Canonical Data Model Standard, DGS. Синхронизирован
  с реализацией. Единый словарь операционных данных.

## 1. Типы происшествий — `IncidentType`
| Код | Значение |
|-----|----------|
| FIRE | Пожар |
| MEDICAL | Медицинская помощь |
| POLICE | Правопорядок/милиция |
| GAS_LEAK | Утечка газа |
| TRAFFIC_ACCIDENT | ДТП |
| WATER_RESCUE | Спасение на воде |
| HAZMAT | Опасные вещества |
| TECHNOLOGICAL | Техногенное |
| NATURAL | Природное ЧС |
| FALSE_ALARM | Ложный вызов |
| OTHER | Иное |

## 2. Приоритеты — `IncidentPriority`
CRITICAL, HIGH, MEDIUM, LOW. Приоритизация — Incident Classification Standard.

## 3. Статусы
- Происшествие — `IncidentStatus`: RECEIVED, CLASSIFIED, DISPATCHED, IN_PROGRESS, RESOLVED,
  CLOSED, CANCELLED (переходы — Operational State Machine).
- Вызов — `CallStatus`: RINGING, ACTIVE, ON_HOLD, COMPLETED, MISSED, TRANSCRIBED, ANALYZED.
- Наряд — `UnitStatus`: AVAILABLE, DISPATCHED, EN_ROUTE, ON_SCENE, RETURNING, OUT_OF_SERVICE.

## 4. Извлекаемые сущности (5.4)
| Сущность | Тип значения | Обязательные атрибуты |
|----------|--------------|------------------------|
| address (адрес) | строка | value, source, probability, confirmation |
| settlement (населённый пункт) | строка | value, source, probability, confirmation |
| street (улица) | строка | value, source, probability, confirmation |
| house_number (номер дома) | строка | value, source, probability, confirmation |
| landmark (ориентир) | строка | value, source, probability, confirmation |
| phone (телефон) | строка (+375XXXXXXXXX) | value, source, probability, confirmation |
| caller_name (имя заявителя) | строка | value, source, probability, confirmation |
| incident_type (тип происшествия) | `IncidentType` | value, source, probability, confirmation |
| casualties (возможные пострадавшие) | целое | value, source, probability, confirmation |
| hazardous_materials (опасные вещества) | строка/список | value, source, probability, confirmation |
| vehicles (транспорт) | строка/список | value, source, probability, confirmation |
| coordinates (координаты) | lat/lon | value, source, probability, confirmation |
| additional (доп. признаки) | строка | value, source, probability, confirmation |

Атрибуты сущности:
- **value** — значение;
- **source** — источник (STT | AI | OPERATOR);
- **probability** — вероятность 0..1 (для OPERATOR = 1.0);
- **confirmation** — статус подтверждения (EXTRACTED | CONFIRMED | REJECTED | EDITED).

## 5. Транскрипт (5.3)
Сегмент: `text`, `speaker` (если включено архитектурой), `startMs`, `endMs`, `language`,
`confidence` (0..1). Транскрипт допускает повторную обработку записи.

## 6. Форматы
- Время — UTC ISO-8601 (Time Service); отображение — Europe/Minsk.
- Телефон — `+375XXXXXXXXX`.
- Координаты — WGS84 (lat/lon), PostGIS (ADR-003).
- Идентификаторы — UUID.

## 7. Управление словарём
Изменения — через RFC/ADR при влиянии на контракты/схему (Canonical Data Model Standard, DGS);
синхронизация с кодом и контрактами обязательна.
