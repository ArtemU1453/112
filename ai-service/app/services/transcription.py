"""Транскрибация аудиозаписи вызова через Whisper.

Модель Whisper загружается лениво и только при enable_whisper=True. Если запись
недоступна или транскрибация отключена — возвращается пустой результат, а анализ
выполняется по уже имеющемуся тексту (переданному диспетчером/оператором).
"""
from __future__ import annotations

import tempfile
from functools import lru_cache

import httpx

from app.config import settings
from app.models.schemas import TranscriptionResult


@lru_cache(maxsize=1)
def _load_whisper():
    import whisper  # локальный импорт

    return whisper.load_model(settings.whisper_model)


async def transcribe_url(recording_url: str) -> TranscriptionResult:
    if not settings.enable_whisper or not recording_url:
        return TranscriptionResult(text="", language="ru", duration_seconds=0.0)

    async with httpx.AsyncClient(timeout=60.0) as client:
        response = await client.get(recording_url)
        response.raise_for_status()
        audio_bytes = response.content

    with tempfile.NamedTemporaryFile(suffix=".wav", delete=True) as tmp:
        tmp.write(audio_bytes)
        tmp.flush()
        return transcribe_file(tmp.name)


def transcribe_file(path: str) -> TranscriptionResult:
    model = _load_whisper()
    result = model.transcribe(path, language="ru")
    segments = result.get("segments", [])
    duration = segments[-1]["end"] if segments else 0.0
    return TranscriptionResult(
        text=result.get("text", "").strip(),
        language=result.get("language", "ru"),
        duration_seconds=float(duration),
    )
