"""Классификация типа и приоритета происшествия по тексту вызова.

Основной механизм — взвешенный лексический классификатор по ключевым словам домена МЧС РБ.
Работает без внешних моделей, детерминирован и покрыт тестами.
"""
from __future__ import annotations

import re
from dataclasses import dataclass

# Тип происшествия -> ключевые слова (леммы/корни в нижнем регистре)
TYPE_KEYWORDS: dict[str, list[str]] = {
    "FIRE": ["пожар", "гори", "задымлен", "дым", "возгоран", "огонь", "пламя", "тлеет"],
    "MEDICAL": ["скорая", "сердц", "без сознан", "кровотеч", "задыха", "инфаркт",
                "инсульт", "перелом", "рожа", "приступ", "плохо с", "давлен", "температур"],
    "TRAFFIC_ACCIDENT": ["дтп", "авари", "столкнов", "сбил", "машин", "автомобил",
                          "мотоцикл", "наезд", "перевернул"],
    "GAS_LEAK": ["газ", "утечк газа", "пахнет газом", "запах газа", "баллон"],
    "POLICE": ["драк", "грабеж", "кража", "нападени", "стрельб", "хулиган",
               "угроз", "избива", "вор"],
    "WATER_RESCUE": ["тонет", "утоплен", "провалил", "лед", "река", "озеро", "вода"],
    "HAZMAT": ["химическ", "ядовит", "радиац", "утечк веществ", "разлив", "кислот"],
    "TECHNOLOGICAL": ["обрушен", "взрыв", "прорыв", "авария на", "лифт застрял"],
    "NATURAL": ["наводнен", "ураган", "буря", "паводок", "гроза повалил", "смерч"],
}

# Слова, повышающие приоритет
CRITICAL_MARKERS = ["взрыв", "без сознан", "не дыш", "много пострадав", "дет", "ребен",
                    "заблокирован", "не может выйти", "погиб", "смертельн", "кровь"]
HIGH_MARKERS = ["пострадав", "ранен", "сильн", "быстро", "срочно", "распространя"]


@dataclass
class Classification:
    incident_type: str
    priority: str
    confidence: float


def classify(text: str) -> Classification:
    lowered = text.lower()

    scores: dict[str, int] = {}
    for itype, keywords in TYPE_KEYWORDS.items():
        score = sum(1 for kw in keywords if kw in lowered)
        if score:
            scores[itype] = score

    if not scores:
        return Classification("OTHER", "MEDIUM", 0.3)

    best_type = max(scores, key=scores.get)
    max_score = scores[best_type]
    total = sum(scores.values())
    confidence = round(0.5 + 0.5 * (max_score / total), 3)

    priority = _priority(lowered, best_type)
    return Classification(best_type, priority, confidence)


def _priority(lowered: str, incident_type: str) -> str:
    if any(marker in lowered for marker in CRITICAL_MARKERS):
        return "CRITICAL"
    if incident_type in ("FIRE", "GAS_LEAK", "HAZMAT", "TECHNOLOGICAL"):
        return "CRITICAL" if _casualty_hint(lowered) else "HIGH"
    if any(marker in lowered for marker in HIGH_MARKERS):
        return "HIGH"
    if incident_type in ("MEDICAL", "TRAFFIC_ACCIDENT", "WATER_RESCUE"):
        return "HIGH"
    return "MEDIUM"


def _casualty_hint(lowered: str) -> bool:
    return bool(re.search(r"пострадав|ранен|люд|человек|жител", lowered))
