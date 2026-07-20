from fastapi import APIRouter, UploadFile, File
from fastapi.responses import JSONResponse
import whisper
import tempfile

router = APIRouter()

model = whisper.load_model("small")

@router.post("/transcribe")
async def transcribe_audio(file: UploadFile = File(...)):
    suffix = ".wav"
    with tempfile.NamedTemporaryFile(suffix=suffix, delete=False) as tmp:
        content = await file.read()
        tmp.write(content)
        tmp.flush()
        result = model.transcribe(tmp.name)
    return JSONResponse(result)
