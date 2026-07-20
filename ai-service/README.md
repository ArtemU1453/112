# ai-service

ИИ-анализ экстренных вызовов системы 112 (FastAPI, Python 3.12).

## Возможности

- **Транскрибация** речи — Whisper (`/api/v1/ai/transcribe`, `/analyze-audio`)
- **NER** — spaCy (`ru_core_news_md`) с регулярным резервом
- **Классификация** типа происшествия и приоритета — лексический доменный классификатор МЧС
- **Извлечение**: адрес, номер телефона, число пострадавших
- **Геокодирование** адреса через gis-service
- **Рекомендации** по составу выезжающих подразделений
- **Kafka-мост**: consumer `call.received` → анализ → producer `call.analyzed`

## Порт

`8090`. OpenAPI: `GET /docs`. Метрики: `GET /metrics`.

## Переменные окружения

| Переменная | По умолчанию | Назначение |
|---|---|---|
| `KAFKA_BOOTSTRAP_SERVERS` | `localhost:9094` | Kafka |
| `ENABLE_KAFKA` | `true` | Включить мост Kafka |
| `ENABLE_WHISPER` | `false` | Загружать модель Whisper |
| `ENABLE_SPACY` | `false` | Загружать модель spaCy |
| `WHISPER_MODEL` | `small` | Размер модели Whisper |
| `SPACY_MODEL` | `ru_core_news_md` | Модель spaCy |
| `GIS_SERVICE_URL` | `http://localhost:8085` | gis-service |

Классификация, извлечение и рекомендации работают без ML-моделей (детерминированные алгоритмы),
поэтому сервис функционален даже при отключённых Whisper/spaCy.

## Запуск и тесты

```bash
pip install -r requirements.txt -r requirements-dev.txt
python -m spacy download ru_core_news_md   # опционально, для NER на spaCy
uvicorn app.main:app --port 8090
pytest
```
