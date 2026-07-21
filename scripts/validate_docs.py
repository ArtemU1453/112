#!/usr/bin/env python3
"""Проверка документации (Documentation Standards, SSOT).

Проверяет:
  - наличие обязательных документов (Vision, Architecture, governance, standards, ADR, RFC);
  - разрешимость относительных ссылок между markdown-документами в docs/.
Запускается в CI (documentation-validation) и локально (scripts/verify.sh).
"""
from __future__ import annotations

import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
DOCS = ROOT / "docs"

REQUIRED_DOCS = [
    "docs/README.md",
    "docs/Vision.md",
    "docs/Architecture.md",
    "docs/architecture/principles.md",
    "docs/architecture/system-structure.md",
    "docs/architecture/technology-mapping.md",
    "docs/adr/README.md",
    "docs/rfc/README.md",
    "docs/rfc/RFC-template.md",
    "docs/governance/project-constitution.md",
    "docs/governance/architecture-baseline.md",
    "docs/governance/engineering-handbook.md",
    "docs/governance/Enterprise-Architecture-Governance-Framework.md",
    "docs/standards/Repository-Governance-Standard.md",
    "docs/standards/Dependency-Governance-Standard.md",
    "docs/standards/Release-Governance-Standard.md",
    "docs/standards/Toolchain-Governance-Standard.md",
    "docs/standards/Environment-Governance-Standard.md",
    "docs/standards/Technology-Abstraction-Policy.md",
    "docs/standards/logging-standards.md",
    "docs/standards/configuration-standards.md",
    "docs/standards/testing-strategy.md",
    "docs/standards/naming-standards.md",
    "docs/standards/security-standards.md",
]

# ADR-001..009 обязательны
REQUIRED_ADRS = [f"docs/adr/ADR-00{i}-" for i in range(1, 10)]

LINK_RE = re.compile(r"\]\(([^)]+\.md)(#[^)]*)?\)")


def fail(msg: str) -> None:
    print(f"::error::{msg}")


def main() -> int:
    errors = 0

    for rel in REQUIRED_DOCS:
        if not (ROOT / rel).is_file():
            fail(f"Отсутствует обязательный документ: {rel}")
            errors += 1

    adr_dir = ROOT / "docs" / "adr"
    for prefix in REQUIRED_ADRS:
        stem = Path(prefix).name
        if not any(p.name.startswith(stem) for p in adr_dir.glob("ADR-*.md")):
            fail(f"Отсутствует ADR с префиксом: {stem}")
            errors += 1

    # Проверка относительных ссылок в docs/
    if DOCS.is_dir():
        for md in DOCS.rglob("*.md"):
            text = md.read_text(encoding="utf-8")
            for m in LINK_RE.finditer(text):
                target = m.group(1)
                if target.startswith("http"):
                    continue
                resolved = (md.parent / target).resolve()
                if not resolved.exists():
                    fail(f"Битая ссылка в {md.relative_to(ROOT)} -> {target}")
                    errors += 1

    if errors:
        print(f"Валидация документации: обнаружено ошибок — {errors}")
        return 1
    print("Валидация документации: OK")
    return 0


if __name__ == "__main__":
    sys.exit(main())
