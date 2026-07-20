"""REST-эндпоинты ИИ-анализа."""
from __future__ import annotations

import tempfile

from fastapi import APIRouter, File, UploadFile

from app.models.schemas import AnalysisResult, AnalyzeTextRequest, TranscriptionResult
from app.services import analyzer, transcription

router = APIRouter(prefix="/api/v1/ai", tags=["ai"])


@router.post("/analyze", response_model=AnalysisResult, summary="Анализ текста вызова")
async def analyze(request: AnalyzeTextRequest) -> AnalysisResult:
    return await analyzer.analyze_text(request.text, request.call_id, request.caller_phone)


@router.post("/transcribe", response_model=TranscriptionResult,
             summary="Транскрибация аудиофайла (Whisper)")
async def transcribe(file: UploadFile = File(...)) -> TranscriptionResult:
    with tempfile.NamedTemporaryFile(suffix=".wav", delete=True) as tmp:
        tmp.write(await file.read())
        tmp.flush()
        return transcription.transcribe_file(tmp.name)


@router.post("/analyze-audio", response_model=AnalysisResult,
             summary="Транскрибация + анализ аудиофайла")
async def analyze_audio(file: UploadFile = File(...)) -> AnalysisResult:
    with tempfile.NamedTemporaryFile(suffix=".wav", delete=True) as tmp:
        tmp.write(await file.read())
        tmp.flush()
        result = transcription.transcribe_file(tmp.name)
    return await analyzer.analyze_text(result.text)
