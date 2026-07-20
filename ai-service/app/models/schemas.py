"""Pydantic-схемы запросов и ответов ai-service."""
from __future__ import annotations

from typing import Optional

from pydantic import BaseModel, Field


class AnalyzeTextRequest(BaseModel):
    text: str = Field(..., min_length=1, max_length=8000,
                      description="Текст расшифровки экстренного вызова")
    call_id: Optional[str] = Field(None, description="Идентификатор вызова")
    caller_phone: Optional[str] = Field(None, description="Номер телефона звонящего")


class Entity(BaseModel):
    label: str
    text: str
    start: int
    end: int


class UnitRecommendation(BaseModel):
    unit_type: str
    count: int
    reason: str


class AnalysisResult(BaseModel):
    call_id: Optional[str] = None
    incident_type: str
    priority: str
    confidence: float
    address: Optional[str] = None
    latitude: Optional[float] = None
    longitude: Optional[float] = None
    caller_phone: Optional[str] = None
    casualties_count: int = 0
    entities: list[Entity] = Field(default_factory=list)
    recommendations: list[UnitRecommendation] = Field(default_factory=list)
    transcript: str = ""
    auto_create_incident: bool = False


class TranscriptionResult(BaseModel):
    text: str
    language: str
    duration_seconds: float
