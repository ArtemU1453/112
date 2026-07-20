"""Рекомендации по составу выезжающих подразделений на основе типа и приоритета."""
from __future__ import annotations

from app.models.schemas import UnitRecommendation

# Тип происшествия -> базовый наряд сил и средств
BASE_DISPATCH: dict[str, list[tuple[str, int, str]]] = {
    "FIRE": [("FIRE_TRUCK", 2, "Тушение и подача воды"),
             ("LADDER_TRUCK", 1, "Спасание с высоты"),
             ("AMBULANCE", 1, "Медицинское прикрытие")],
    "MEDICAL": [("AMBULANCE", 1, "Оказание медицинской помощи")],
    "TRAFFIC_ACCIDENT": [("RESCUE_SQUAD", 1, "Деблокирование пострадавших"),
                         ("AMBULANCE", 1, "Медицинская помощь"),
                         ("POLICE_PATROL", 1, "Оформление ДТП")],
    "GAS_LEAK": [("GAS_SERVICE", 1, "Локализация утечки"),
                 ("FIRE_TRUCK", 1, "Противопожарное прикрытие")],
    "POLICE": [("POLICE_PATROL", 1, "Пресечение правонарушения")],
    "WATER_RESCUE": [("WATER_RESCUE", 1, "Спасание на воде"),
                     ("AMBULANCE", 1, "Медицинская помощь")],
    "HAZMAT": [("HAZMAT_UNIT", 1, "Химическая разведка и дегазация"),
               ("FIRE_TRUCK", 1, "Прикрытие"),
               ("AMBULANCE", 1, "Медицинская помощь")],
    "TECHNOLOGICAL": [("RESCUE_SQUAD", 2, "Аварийно-спасательные работы"),
                      ("AMBULANCE", 1, "Медицинская помощь")],
    "NATURAL": [("RESCUE_SQUAD", 1, "Ликвидация последствий")],
    "OTHER": [("RESCUE_SQUAD", 1, "Уточнение обстановки на месте")],
}


def recommend(incident_type: str, priority: str, casualties: int) -> list[UnitRecommendation]:
    base = BASE_DISPATCH.get(incident_type, BASE_DISPATCH["OTHER"])
    recommendations = [
        UnitRecommendation(unit_type=unit, count=count, reason=reason)
        for unit, count, reason in base
    ]

    # Усиление наряда при критическом приоритете
    if priority == "CRITICAL":
        for rec in recommendations:
            if rec.unit_type in ("FIRE_TRUCK", "RESCUE_SQUAD"):
                rec.count += 1
                rec.reason += " (усиление по критическому приоритету)"

    # Дополнительные бригады скорой по числу пострадавших (1 бригада на 2 пострадавших)
    if casualties > 2:
        extra = (casualties + 1) // 2
        merged = False
        for rec in recommendations:
            if rec.unit_type == "AMBULANCE":
                rec.count = max(rec.count, extra)
                rec.reason = f"Медпомощь: {casualties} пострадавш."
                merged = True
        if not merged:
            recommendations.append(UnitRecommendation(
                unit_type="AMBULANCE", count=extra,
                reason=f"Медпомощь: {casualties} пострадавш."))

    return recommendations
