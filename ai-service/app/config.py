"""Конфигурация ai-service из переменных окружения."""
from pydantic_settings import BaseSettings, SettingsConfigDict


class Settings(BaseSettings):
    model_config = SettingsConfigDict(env_file=".env", extra="ignore")

    kafka_bootstrap_servers: str = "localhost:9094"
    whisper_model: str = "small"
    spacy_model: str = "ru_core_news_md"
    gis_service_url: str = "http://localhost:8085"
    topic_call_received: str = "call.received"
    topic_call_analyzed: str = "call.analyzed"
    enable_kafka: bool = True
    enable_whisper: bool = False
    enable_spacy: bool = False


settings = Settings()
