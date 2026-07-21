<!-- ЕАСУР — шаблон Pull Request (Git Strategy, Definition of Done) -->

## Задача
- Backlog ID: <CTX>-000
- Связанные ADR/RFC (при наличии):

## Описание изменения
Кратко: что и зачем. Влияние на систему.

## Тип изменения
- [ ] feat — новая функциональность
- [ ] fix — исправление дефекта
- [ ] refactor — рефакторинг без изменения поведения
- [ ] docs — документация
- [ ] build/ci — сборка/конвейер
- [ ] test — тесты
- [ ] chore — прочее

## Checklist (Definition of Done)
- [ ] Код соответствует Coding/Naming/Logging/Configuration Standards
- [ ] Архитектура не нарушена (соответствие ADR/RFC/Baseline); при изменении — RFC+ADR
- [ ] Тесты требуемых уровней написаны и проходят (Testing Strategy)
- [ ] Пройдены Quality Gates (build, test, lint, security)
- [ ] API соответствует OpenAPI; схема БД — миграциям (API/Database First)
- [ ] Документация синхронизирована (Vision/Architecture/ADR/API/DB/Security/Testing)
- [ ] Секретов в изменениях нет; обратная совместимость соблюдена или оформлена через RFC
- [ ] Обновлены соответствующие README/руны при необходимости

## Как проверить
Шаги проверки/команды (`./scripts/verify.sh`, `test.sh`, ручная проверка).
