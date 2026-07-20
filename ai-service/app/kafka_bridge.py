"""Мост Kafka: слушает call.received, выполняет анализ и публикует call.analyzed."""
from __future__ import annotations

import asyncio
import json
import logging

from aiokafka import AIOKafkaConsumer, AIOKafkaProducer

from app.config import settings
from app.services import analyzer, transcription

logger = logging.getLogger("ai-service.kafka")


class KafkaBridge:
    def __init__(self) -> None:
        self._consumer: AIOKafkaConsumer | None = None
        self._producer: AIOKafkaProducer | None = None
        self._task: asyncio.Task | None = None
        self._running = False

    async def start(self) -> None:
        if not settings.enable_kafka:
            logger.info("Kafka отключена (enable_kafka=false)")
            return
        self._producer = AIOKafkaProducer(
            bootstrap_servers=settings.kafka_bootstrap_servers,
            value_serializer=lambda v: json.dumps(v).encode("utf-8"),
        )
        self._consumer = AIOKafkaConsumer(
            settings.topic_call_received,
            bootstrap_servers=settings.kafka_bootstrap_servers,
            group_id="ai-service",
            value_deserializer=lambda v: json.loads(v.decode("utf-8")),
            auto_offset_reset="earliest",
        )
        await self._producer.start()
        await self._consumer.start()
        self._running = True
        self._task = asyncio.create_task(self._consume_loop())
        logger.info("Kafka bridge запущен")

    async def stop(self) -> None:
        self._running = False
        if self._task:
            self._task.cancel()
        if self._consumer:
            await self._consumer.stop()
        if self._producer:
            await self._producer.stop()

    async def _consume_loop(self) -> None:
        assert self._consumer is not None
        try:
            async for message in self._consumer:
                await self._handle(message.value)
        except asyncio.CancelledError:
            pass
        except Exception:
            logger.exception("Ошибка в цикле потребления Kafka")

    async def _handle(self, event: dict) -> None:
        try:
            call_id = event.get("callId")
            caller_phone = event.get("callerPhone")
            recording_url = event.get("recordingUrl", "")

            transcript_result = await transcription.transcribe_url(recording_url)
            text = transcript_result.text or event.get("transcript", "")
            if not text:
                logger.info("Нет текста для анализа вызова %s", call_id)
                return

            result = await analyzer.analyze_text(text, call_id, caller_phone)
            payload = result.model_dump()
            payload["callId"] = call_id
            payload["incidentType"] = result.incident_type
            payload["autoCreateIncident"] = result.auto_create_incident
            await self._producer.send_and_wait(
                settings.topic_call_analyzed, payload, key=(call_id or "").encode("utf-8"))
            logger.info("Опубликован результат анализа вызова %s", call_id)
        except Exception:
            logger.exception("Ошибка обработки события call.received: %s", event)
