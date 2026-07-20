"""Извлечение именованных сущностей (NER).

Если модель spaCy доступна и включена — используется она; иначе применяется
регулярный резервный экстрактор (адрес, телефон, числа), чтобы сервис оставался работоспособным.
"""
from __future__ import annotations

import re
from functools import lru_cache

from app.config import settings
from app.models.schemas import Entity
from app.services.extractor import PHONE_RE, STREET_RE

_NUMBER_RE = re.compile(r"\b\d+\b")


@lru_cache(maxsize=1)
def _load_spacy():
    import spacy  # локальный импорт, чтобы не требовать модель при отключённом NLP

    return spacy.load(settings.spacy_model)


def extract_entities(text: str) -> list[Entity]:
    if settings.enable_spacy:
        try:
            return _spacy_entities(text)
        except Exception:
            return _regex_entities(text)
    return _regex_entities(text)


def _spacy_entities(text: str) -> list[Entity]:
    nlp = _load_spacy()
    doc = nlp(text)
    return [
        Entity(label=ent.label_, text=ent.text, start=ent.start_char, end=ent.end_char)
        for ent in doc.ents
    ]


def _regex_entities(text: str) -> list[Entity]:
    entities: list[Entity] = []
    for match in STREET_RE.finditer(text):
        entities.append(Entity(label="LOC", text=match.group(1).strip(),
                               start=match.start(1), end=match.end(1)))
    for match in PHONE_RE.finditer(text):
        entities.append(Entity(label="PHONE", text=match.group(1),
                               start=match.start(1), end=match.end(1)))
    for match in _NUMBER_RE.finditer(text):
        entities.append(Entity(label="NUM", text=match.group(0),
                               start=match.start(), end=match.end()))
    return entities
