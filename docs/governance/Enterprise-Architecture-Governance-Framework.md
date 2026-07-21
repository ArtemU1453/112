# Enterprise Architecture Governance Framework (EAGF) — ЕАСУР

- **Status:** APPROVED · Baseline: v1.0

Верхнеуровневый документ управления архитектурой, инженерными стандартами, жизненным циклом
проекта и процессом принятия технических решений. Не заменяет существующие документы, а
определяет их роли, взаимосвязи и правила применения.

## Пирамида документов (порядок приоритета)

**Уровень 1 — Управление проектом**
- Vision · Project Constitution

**Уровень 2 — Архитектурное управление**
- Architecture Baseline · Architecture.md · Enterprise Architecture Governance Framework

**Уровень 3 — Управление изменениями**
- ADR · RFC

**Уровень 4 — Инженерные стандарты**
- Engineering Handbook · Repository Governance Standard · Dependency Governance Standard ·
  Release Governance Standard · Toolchain Governance Standard · Environment Governance Standard ·
  Requirements Management Standard · Technology Abstraction Policy

**Уровень 5 — Предметная область**
- Domain Model (DMS) · API Governance Standard · Database Governance Standard

**Уровень 6 — Реализация**
- исходный код · миграции · тесты · инфраструктурный код · документация модулей

Каждый нижний уровень обязан соответствовать вышестоящим. При противоречии действует порядок
приоритета Single Source of Truth (Project Constitution).

## Governance Principles

1. Architecture First. 2. Documentation First. 3. Capability First. 4. Security by Design.
5. Privacy by Design. 6. Testability by Design. 7. Observability by Design.
8. Automation by Default. 9. Backward Compatibility by Default. 10. Incremental Delivery.

## Architecture Review Board (виртуальный)

Определён виртуальный Architecture Review Board. В процессе разработки с ИИ его функции
исполняет архитектурный агент/ревью. Обязанности: проверка RFC; утверждение ADR; контроль
архитектурного дрейфа; контроль технического долга; анализ рисков; контроль соответствия Baseline.

## Governance Checkpoints

Обязательные контрольные точки соответствия утверждённым документам:
- завершение этапа; завершение Epic; подготовка релиза; внедрение новой технологии;
  изменение архитектуры; изменение требований; изменение модели данных.

## Трассируемость (сквозная)

```
Vision → Requirement → Epic → Feature → Story → Task → Commit → Pull Request → Code → Test → Documentation → Release
```

Нарушение цепочки означает, что изменение неполное (см. Requirements Management Standard,
Backlog Structure).

## Политика эволюции

Развитие проекта сохраняет: архитектурную согласованность; обратную совместимость (если иное
не утверждено); полноту документации; воспроизводимость сборки; управляемость изменений.

## Роль ИИ

ИИ — исполнитель инженерного процесса. Обязан: соблюдать все стандарты; не обходить процессы;
не принимать архитектурные решения вне компетенции; останавливаться при противоречиях;
предлагать RFC вместо самовольного изменения архитектуры (Project Constitution, ARTICLE 20).

## Definition of Done

- ✓ Определены уровни документов и правила приоритета.
- ✓ Описаны контрольные точки и процесс архитектурного управления.
- ✓ Обеспечена сквозная трассируемость.
- ✓ Документ согласован со всеми ранее созданными стандартами.
