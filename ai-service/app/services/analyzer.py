"""Оркестратор ИИ-анализа вызова: объединяет классификацию, NER, извлечение и рекомендации."""
from __future__ import annotations

import httpx

from app.config import settings
from app.models.schemas import AnalysisResult
from app.services import classifier, extractor, ner, recommender


async def analyze_text(text: str, call_id: str | None = None,
                       caller_phone: str | None = None) -> AnalysisResult:
    classification = classifier.classify(text)
    extraction = extractor.extract(text, fallback_phone=caller_phone)
    entities = ner.extract_entities(text)
    recommendations = recommender.recommend(
        classification.incident_type, classification.priority, extraction.casualties_count)

    latitude, longitude = None, None
    if extraction.address:
        latitude, longitude = await _geocode(extraction.address)

    auto_create = classification.priority in ("CRITICAL", "HIGH") and classification.confidence >= 0.5

    return AnalysisResult(
        call_id=call_id,
        incident_type=classification.incident_type,
        priority=classification.priority,
        confidence=classification.confidence,
        address=extraction.address,
        latitude=latitude,
        longitude=longitude,
        caller_phone=extraction.phone,
        casualties_count=extraction.casualties_count,
        entities=entities,
        recommendations=recommendations,
        transcript=text,
        auto_create_incident=auto_create,
    )


async def _geocode(address: str) -> tuple[float | None, float | None]:
    """Обращается к gis-service за координатами адреса. При недоступности — без координат."""
    try:
        async with httpx.AsyncClient(timeout=5.0) as client:
            response = await client.post(
                f"{settings.gis_service_url}/api/v1/gis/geocode",
                json={"address": address},
            )
            if response.status_code == 200:
                data = response.json()
                return data.get("latitude"), data.get("longitude")
    except Exception:
        return None, None
    return None, None
