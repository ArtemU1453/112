# Installation Guide — ЕАСУР

Установка инструментария разработки. Конкретные версии — в каталоге версий
(`config/toolchain/versions.yaml`); ниже — состав, без номеров (TVMS).

## Обязательные инструменты
- **Git** — контроль версий.
- **Docker Engine + Docker Compose** — контейнеры и локальная среда.
- **JDK (LTS)** — сборка Java-сервисов (или через Dev Container).
- **Node.js (LTS) + npm** — сборка frontend.
- **Python** — ai-service и вспомогательные инструменты.
- **Maven** — сборка Java (или wrapper).
- **Helm**, **kubectl**, **kustomize** — для работы с Kubernetes (по необходимости).

## Рекомендуемый способ: Dev Container
Открыть репозиторий в VS Code → «Reopen in Container». Среда `.devcontainer/` устанавливает
зафиксированные версии инструментов и зависимости автоматически — не требуется ручная установка.

## Проверка окружения
```bash
./scripts/verify-versions.sh    # соответствие установленных инструментов каталогу версий
```

## IDE
- **VS Code:** установить рекомендованные расширения (`.vscode/extensions.json`).
- **IntelliJ IDEA:** импортировать профиль форматирования (Checkstyle-совместимый),
  включить EditorConfig.

## Платформы
Поддержка Windows/Linux/macOS — см. `config/toolchain/supported-platforms.yaml` (Windows —
через Dev Container/WSL2).
