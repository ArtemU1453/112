# Simulation Data Model — ЕАСУР

- **Status:** APPROVED · Stage 12 · Подчиняется Digital Twin / Simulation Architecture, ADR-026,
  Synthetic Data / Scenario Versioning / Exercise Data Governance Standards.

## 1. Назначение
Модель данных платформы двойника и моделирования: сущности двойника, сценариев, прогонов и результатов.
Обеспечивает воспроизводимость, версионирование и изоляцию (все данные — синтетические/обезличенные).

## 2. Основные сущности
| Сущность | Ключевые атрибуты | Связи |
|----------|-------------------|-------|
| `TwinVersion` | id, версия, датаснимка, статус | → TwinEntity |
| `TwinEntity` | id, тип (подразделение/техника/объект/территория/зона/инфраструктура), гео, атрибуты | ← TwinVersion |
| `Scenario` | id (`SCN-*`), версия, тип ЧС, начальные условия | → ScenarioEvent, → Run |
| `ScenarioEvent` | id, время, тип события, параметры, триггеры/вводные | ← Scenario |
| `Run` | id, scenarioVersion, twinVersion, params, seed, время старта, статус | → RunResult, → RunEvent |
| `RunEvent` | id, отметка времени (модельная), событие, состояние | ← Run |
| `RunResult` | id, метрики эффективности, агрегаты, статус валидации | ← Run |
| `Experiment` | id, тип (A/B, оптимизация, ИИ), связка прогонов, гипотеза | → Run, → Evaluation |
| `Evaluation` | id, критерии, оценки, объяснимость (для ИИ), вывод | ← Experiment |
| `Dataset` | id, origin=synthetic, версия, lineage | ← Run/Experiment |

## 3. Инварианты
- Каждый `Run` детерминирован по (`twinVersion`, `scenarioVersion`, `params`, `seed`, `Dataset`).
- `Dataset.origin = synthetic` обязателен; реальные оперативные данные не хранятся.
- Версии сценариев/двойника **неизменяемы**; изменения — новые версии.
- Записи помечены средой (`origin=simulation`); смешение с реальными инцидентами исключено.

## 4. Хранение
Изолированное хранилище контура двойника; история экспериментов версионируется и удерживается по
ретенции (Exercise Data Governance). Экспорт — контролируемый, с маркировкой источника.

## 5. Связи
Digital Twin / Simulation / Training Architecture · Simulation Analytics · стандарты Stage 12 ·
доменные модели Stage 5–6 (как основа структуры, без переноса реальных данных).
