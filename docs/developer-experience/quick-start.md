# Quick Start — ЕАСУР

Быстрый старт разработчика. Цель — от клонирования до готовой среды одной командой.

## Предпосылки
- Установлены: Git, Docker + Docker Compose (версии — `config/toolchain/versions.yaml`).
- Рекомендуется VS Code с Dev Containers или IntelliJ IDEA (Toolchain Governance Standard).

## Шаги
```bash
# 1. Клонировать
git clone <repo-url> && cd 112

# 2. Поднять среду разработки одной командой
./scripts/bootstrap.sh          # проверки + запуск docker/compose.dev + инициализация

# 3. Собрать всё
./scripts/build.sh

# 4. Проверить качество и версии
./scripts/lint.sh
./scripts/verify.sh             # architecture + versions + docs validation (локально)

# 5. Прогнать тесты
./scripts/test.sh
```

Либо открыть проект в **Dev Container** (`.devcontainer/`) — среда соберётся автоматически с
зафиксированными версиями инструментов.

## Что дальше
- Local Development Guide — `local-development.md`
- Debug Guide — `debug.md`
- Contribution Guide — `contribution.md`
- Полная эксплуатация — `../operations.md`
