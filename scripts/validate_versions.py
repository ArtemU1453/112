#!/usr/bin/env python3
"""Проверка каталога версий (Toolchain Version Management Standard).

Убеждается, что каталог версий существует, корректно парсится и содержит обязательные разделы.
Запускается в CI (architecture-validation) и локально (scripts/verify-versions.sh).
"""
from __future__ import annotations

import sys
from pathlib import Path

try:
    import yaml
except ImportError:  # pragma: no cover
    print("PyYAML не установлен; установите: pip install pyyaml", file=sys.stderr)
    sys.exit(2)

ROOT = Path(__file__).resolve().parent.parent
CATALOG = ROOT / "config" / "toolchain" / "versions.yaml"
REQUIRED_FILES = [
    "config/toolchain/versions.yaml",
    "config/toolchain/compatibility-matrix.yaml",
    "config/toolchain/supported-platforms.yaml",
    "config/toolchain/deprecated-versions.yaml",
    "config/toolchain/migration-policy.md",
    "config/toolchain/README.md",
]
REQUIRED_SECTIONS = [
    "languagesRuntimes",
    "buildSystems",
    "containerization",
    "orchestration",
    "data",
    "messaging",
    "identity",
    "frameworks",
    "testing",
    "qualityTools",
    "security",
    "ci",
]
VALID_CATEGORIES = {
    "LTS", "Supported", "Preview", "Experimental", "Deprecated", "EndOfLife",
}


def fail(msg: str) -> None:
    print(f"::error::{msg}")


def main() -> int:
    errors = 0
    for rel in REQUIRED_FILES:
        if not (ROOT / rel).exists():
            fail(f"Отсутствует обязательный файл каталога версий: {rel}")
            errors += 1

    if not CATALOG.exists():
        return 1

    data = yaml.safe_load(CATALOG.read_text(encoding="utf-8"))
    if not isinstance(data, dict):
        fail("versions.yaml: корень должен быть объектом")
        return 1

    for section in REQUIRED_SECTIONS:
        if section not in data:
            fail(f"versions.yaml: отсутствует обязательный раздел '{section}'")
            errors += 1

    # Проверка категорий у записей инструментов
    for section, tools in data.items():
        if not isinstance(tools, dict):
            continue
        for tool, spec in tools.items():
            if isinstance(spec, dict) and "category" in spec:
                if spec["category"] not in VALID_CATEGORIES:
                    fail(f"versions.yaml: {section}.{tool} — недопустимая категория "
                         f"'{spec['category']}' (допустимо: {sorted(VALID_CATEGORIES)})")
                    errors += 1

    if errors:
        print(f"Каталог версий: обнаружено ошибок — {errors}")
        return 1
    print("Каталог версий: OK")
    return 0


if __name__ == "__main__":
    sys.exit(main())
