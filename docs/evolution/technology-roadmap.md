# Technology Roadmap — ЕАСУР

- **Status:** APPROVED · Stage 11 · Реализует Enterprise Evolution Strategy. Согласован с Technology
  Mapping (TAP), каталогом версий (TVMS), Modernization/Upgrade Compatibility/Deprecation Standards.

## 1. Назначение
Технологическая дорожная карта: плановая эволюция технологий, версий и возможностей на горизонте
жизненного цикла. Версии — по каталогу (TVMS); технологии — по возможностям (Capability/ADR).

## 2. Принципы планирования
- Технология вводится/заменяется через возможность (Capability) + ADR (TAP); без вендор-лока.
- LTS-ориентация: приоритет поддерживаемым (LTS) линиям; контроль EndOfLife.
- Совместимость и поэтапность обязательны (Upgrade Compatibility / Modernization Standards).

## 3. Направления (иллюстративно, детализация — в Roadmap по горизонтам)
| Направление | Текущее (Capability/ADR) | Вектор эволюции |
|-------------|--------------------------|-----------------|
| Среда выполнения | Java LTS + Spring Boot (ADR-001) | Обновление LTS-линий по TVMS |
| Оркестрация/инфра | Kubernetes/Helm/IaC (ADR-005/010) | Обновление версий, узлов, хранилища |
| Данные | PostgreSQL/PostGIS (ADR-003) | Обновления, при росте — OLAP (ADR-019) |
| Поиск/документы | `CAP-SEARCH-001`/`CAP-DOC-001` (Proposed) | Выбор движка через RFC+ADR |
| ИИ (совещательный) | Адаптеры провайдеров (ADR-021, AI Policy) | Модернизация моделей/методик, объяснимость |
| Интеграции | Платформа + PKI (ADR-022/023) | Расширение каналов/партнёров, версии контрактов |
| Мобильная экосистема | Единый стек (ADR-016) | Обновление стека/SDK, offline-first |

## 4. Управление изменениями
Каждая позиция дорожной карты, затрагивающая архитектуру, реализуется через RFC→ARB→ADR с обновлением
Technology Mapping и каталога версий. Незапланированные потребности — через оценку технологий (Innovation).

## 5. Пересмотр
Ежегодная актуализация совместно с Enterprise Evolution Strategy и Enterprise Evolution Roadmap;
внеплановая — при EndOfLife/критических уязвимостях.

## 6. Связи
Enterprise Evolution Strategy · [Enterprise Evolution Roadmap](enterprise-evolution-roadmap.md) ·
Technology Mapping · каталог версий · Innovation Framework · Modernization Standard.
