# Contribution Guide — ЕАСУР

Правила внесения изменений. Полные процессы — Engineering Handbook, Git Strategy, EAGF.

## Перед началом (Definition of Ready)
- Задача в Backlog с идентификатором, критериями приёмки, DoR (Backlog Structure, DoR).
- Изменение укладывается в утверждённую архитектуру; иначе — сначала RFC (EAGF, Constitution).

## Рабочий цикл
1. Ветка от `develop`: `feature/<TICKET>-<short-desc>` (Git Strategy).
2. Разработка по стандартам (Coding/Naming/Logging/Configuration/Testing).
3. Тесты требуемых уровней (Testing Strategy); документация синхронизирована (ARTICLE 9).
4. Локальная проверка: `./scripts/verify.sh` (архитектура/версии/документация), `lint`, `test`.
5. Атомарные коммиты (Conventional Commits).
6. Pull Request по шаблону `.github/pull_request_template.md`.

## Требования к Pull Request (Definition of Done)
- Зелёный CI (build/test/lint/security), пройдены Quality Gates.
- Code Review (мин. 1 одобрение; архитектурно значимое — архитектурный ревью).
- Обновлена документация; при изменении архитектуры — RFC + ADR.
- Обратная совместимость соблюдена или оформлена через RFC.

## Что запрещено
- Бизнес-логика/API вне утверждённых контрактов; секреты в git; TODO/заглушки/мёртвый код в
  основной ветке (Constitution ARTICLE 3).
- Изменение архитектуры «через код» без RFC/ADR.

## Поведение
См. `CODE_OF_CONDUCT.md`. Сообщения об уязвимостях — `SECURITY.md`. Вопросы поддержки — `SUPPORT.md`.
