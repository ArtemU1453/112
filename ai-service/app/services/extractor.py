"""Извлечение адреса, номера телефона и числа пострадавших из текста вызова."""
from __future__ import annotations

import re
from dataclasses import dataclass

PHONE_RE = re.compile(r"(\+?375\d{9}|8\d{10}|\+?\d{7,15})")

# Адрес: улица/проспект/переулок + название (1-3 слова) + номер дома
STREET_RE = re.compile(
    r"((?:ул(?:ица|\.)?|пр(?:оспект|\.)?|пер(?:еулок|\.)?|б-р|бульвар|проезд|шоссе|"
    r"набережн\w*|площад\w*)\s+[А-ЯЁа-яё\-]+(?:\s+[А-ЯЁа-яё\-]+){0,2}"
    r"(?:\s*,?\s*(?:д(?:ом|\.)?\s*)?\d+[а-яА-Я]?(?:\s*кв\.?\s*\d+)?)?)",
    re.IGNORECASE,
)
HOUSE_RE = re.compile(r"\b(?:д(?:ом|\.)?\s*)?(\d{1,4}[а-яА-Я]?)\b")

CARDINAL = {
    "один": 1, "одного": 1, "одна": 1, "два": 2, "двое": 2, "двух": 2,
    "три": 3, "трое": 3, "трёх": 3, "трех": 3, "четыре": 4, "четверо": 4,
    "пять": 5, "пятеро": 5, "шесть": 6, "семь": 7, "восемь": 8, "девять": 9, "десять": 10,
}
CASUALTY_CONTEXT = re.compile(
    r"(\d+|[а-яё]+)\s+(?:пострадавш\w+|ранен\w+|человек\w*|люд\w+|жертв\w*|погибш\w+|"
    r"тел\w*)",
    re.IGNORECASE,
)


@dataclass
class Extraction:
    address: str | None
    phone: str | None
    casualties_count: int


def extract(text: str, fallback_phone: str | None = None) -> Extraction:
    return Extraction(
        address=extract_address(text),
        phone=extract_phone(text) or fallback_phone,
        casualties_count=extract_casualties(text),
    )


def extract_address(text: str) -> str | None:
    match = STREET_RE.search(text)
    if not match:
        return None
    address = re.sub(r"\s+", " ", match.group(1)).strip(" ,.")
    return address or None


def extract_phone(text: str) -> str | None:
    match = PHONE_RE.search(text.replace(" ", "").replace("-", ""))
    return match.group(1) if match else None


def extract_casualties(text: str) -> int:
    lowered = text.lower()
    best = 0
    for match in CASUALTY_CONTEXT.finditer(lowered):
        token = match.group(1)
        value = int(token) if token.isdigit() else CARDINAL.get(token, 0)
        best = max(best, value)
    if best == 0 and re.search(r"пострадавш|ранен|погиб", lowered):
        best = 1
    return best
