from fastapi import APIRouter, Body
from transformers import pipeline

router = APIRouter()

clf = pipeline("zero-shot-classification", model="facebook/bart-large-mnli")

@router.post("/classify")
async def classify_text(text: str = Body(..., media_type="text/plain")):
    labels = ["medical", "fire", "police", "traffic", "other"]
    res = clf(text, labels)
    return res
