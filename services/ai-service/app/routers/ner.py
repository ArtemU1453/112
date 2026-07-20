from fastapi import APIRouter
from fastapi import Body
import spacy

router = APIRouter()

nlp = spacy.load("en_core_web_sm")

@router.post("/ner")
async def ner_extract(text: str = Body(..., media_type="text/plain")):
    doc = nlp(text)
    entities = [{"text": ent.text, "label": ent.label_} for ent in doc.ents]
    return {"entities": entities}
