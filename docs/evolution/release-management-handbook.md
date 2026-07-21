# Release Management Handbook — ЕАСУР

- **Status:** APPROVED · Stage 11 · Реализует Release Governance Standard (Stage 2), Release Acceptance
  Standard (Stage 10), Long-Term Support Policy (подэтап 11.8). Долгосрочная стратегия выпусков.

## 1. Назначение
Долгосрочная стратегия и правила выпусков ЕАСУР: типы релизов, версионирование, окна поддержки,
совместимость и порядок выпуска.

## 2. Типы релизов
| Тип | Содержание | Совместимость | Порядок |
|-----|-----------|---------------|---------|
| **Major** | Значимые изменения (возможно несовместимые) | Через версионирование + миграцию + депрекацию | RFC→ARB→ADR, поэтапно |
| **Minor** | Новые возможности, обратно совместимо | Обратная совместимость | Стандартный релизный цикл |
| **Patch** | Исправления дефектов | Полная совместимость | Стандартный цикл |
| **Hotfix** | Срочное исправление критдефекта | Полная совместимость | Ускоренный, с последующим бэкпортом |
| **Extended Support Release (ESR)** | Стабилизированная линия для длительной эксплуатации | Только фиксы/безопасность | По плану поддержки |
| **Long-Term Support (LTS) Release** | Опорная линия на длительный срок | Только фиксы/безопасность | LTS-окно (Long-Term Support Policy) |

## 3. Версионирование
Семантическое версионирование (MAJOR.MINOR.PATCH); версии инструментов — по каталогу (TVMS); версии
API — по API Versioning Standard. Артефакты неизменяемы, подписаны, с SBOM.

## 4. Правила выпуска
- Каждый релиз проходит Release Acceptance (Quality Gates, безопасность, совместимость, план отката).
- Major/несовместимые изменения — через RFC→ARB→ADR и депрекацию старого (Deprecation Policy).
- Hotfix — с обязательным бэкпортом в поддерживаемые линии и записью в Lessons Learned при SEV-1/2.
- Развёртывание — по Deployment Program/Go-Live Playbook; окна — Maintenance Window Standard.

## 5. Поддержка линий
LTS/ESR-линии сопровождаются по [Long-Term Support Policy](long-term-support-policy.md) и Long-Term
Maintenance Standard; окончание поддержки линии — по Deprecation Policy с планом миграции.

## 6. Связи
Release Governance / Release Acceptance / Deprecation Policy / Upgrade Compatibility Standards ·
Long-Term Support Policy · Deployment Program · каталог версий.
