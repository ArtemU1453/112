"""Точка входа ai-service (FastAPI): анализ экстренных вызовов системы 112."""
from __future__ import annotations

import logging
from contextlib import asynccontextmanager

from fastapi import FastAPI, Response
from prometheus_client import CONTENT_TYPE_LATEST, Counter, generate_latest

from app.kafka_bridge import KafkaBridge
from app.routers import analyze

logging.basicConfig(level=logging.INFO)

ANALYSIS_COUNTER = Counter("ai_analysis_total", "Число выполненных ИИ-анализов вызовов")

kafka_bridge = KafkaBridge()


@asynccontextmanager
async def lifespan(app: FastAPI):
    await kafka_bridge.start()
    yield
    await kafka_bridge.stop()


app = FastAPI(
    title="ai-service — Система-112",
    description="Транскрибация (Whisper), NER (spaCy), классификация и рекомендации по вызовам",
    version="1.0.0",
    lifespan=lifespan,
)
app.include_router(analyze.router)


@app.get("/health", tags=["ops"], summary="Проверка живости")
async def health() -> dict[str, str]:
    return {"status": "UP"}


@app.get("/metrics", tags=["ops"], summary="Метрики Prometheus")
async def metrics() -> Response:
    return Response(generate_latest(), media_type=CONTENT_TYPE_LATEST)
