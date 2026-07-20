from fastapi import FastAPI, UploadFile, File
from fastapi.responses import JSONResponse
from .routers import transcribe, ner, classify

app = FastAPI(title="AI Service", version="0.1.0")

app.include_router(transcribe.router, prefix="/api/v1/ai")
app.include_router(ner.router, prefix="/api/v1/ai")
app.include_router(classify.router, prefix="/api/v1/ai")

@app.get("/health")
async def health():
    return {"status": "ok"}
