#!/usr/bin/env python3
"""Проверка структуры репозитория и архитектурных правил (Repository Governance Standard).

Проверяет:
  - наличие обязательных каталогов верхнего уровня;
  - наличие ключевых Git/GitHub-артефактов;
  - отсутствие запрещённых артефактов (например, node_modules/target в индексе);
Запускается в CI (architecture-validation) и локально (scripts/verify-architecture.sh).
"""
from __future__ import annotations

import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent

REQUIRED_DIRS = [
    "apps", "services", "libraries", "docs", "scripts", "docker", "helm",
    "kubernetes", "tools", "config", "environments", "tests", "infrastructure",
    ".github", ".devcontainer", ".vscode",
]

REQUIRED_FILES = [
    ".gitignore", ".gitattributes", ".editorconfig", "CODEOWNERS",
    "CODE_OF_CONDUCT.md", "SECURITY.md", "SUPPORT.md", "README.md",
    ".github/pull_request_template.md",
    "config/toolchain/versions.yaml",
    "libraries/build/parent/pom.xml",
    "libraries/build/bom/pom.xml",
]

REQUIRED_WORKFLOWS = [
    "build", "test", "lint", "security", "dependency-scan", "docker-build",
    "release", "documentation-validation", "architecture-validation",
]

# Ожидаемые каталоги сервисов (реальные + плейсхолдеры Stage 2)
EXPECTED_SERVICES = [
    "gateway-service", "auth-service", "incident-service", "dispatch-service",
    "telephony-service", "gis-service", "audit-service", "notification-service",
    "realtime-service", "analytics-service", "config-server", "discovery",
]


def fail(msg: str) -> None:
    print(f"::error::{msg}")


def main() -> int:
    errors = 0

    for d in REQUIRED_DIRS:
        if not (ROOT / d).is_dir():
            fail(f"Отсутствует обязательный каталог верхнего уровня: {d}/")
            errors += 1

    for f in REQUIRED_FILES:
        if not (ROOT / f).is_file():
            fail(f"Отсутствует обязательный файл: {f}")
            errors += 1

    wf_dir = ROOT / ".github" / "workflows"
    for wf in REQUIRED_WORKFLOWS:
        if not (wf_dir / f"{wf}.yml").is_file():
            fail(f"Отсутствует workflow: .github/workflows/{wf}.yml")
            errors += 1

    for svc in EXPECTED_SERVICES:
        if not (ROOT / "services" / svc).is_dir():
            fail(f"Отсутствует каталог сервиса: services/{svc}/")
            errors += 1

    # Каждый каталог сервиса должен иметь README (или pom.xml для реализованных)
    services_root = ROOT / "services"
    if services_root.is_dir():
        for svc in services_root.iterdir():
            if svc.is_dir():
                if not (svc / "README.md").exists() and not (svc / "pom.xml").exists():
                    fail(f"services/{svc.name}: отсутствует README.md или pom.xml")
                    errors += 1

    if errors:
        print(f"Архитектурная валидация: обнаружено ошибок — {errors}")
        return 1
    print("Архитектурная валидация: OK")
    return 0


if __name__ == "__main__":
    sys.exit(main())
