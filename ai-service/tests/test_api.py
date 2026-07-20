from fastapi.testclient import TestClient

from app.config import settings

settings.enable_kafka = False

from app.main import app  # noqa: E402

client = TestClient(app)


def test_health():
    response = client.get("/health")
    assert response.status_code == 200
    assert response.json()["status"] == "UP"


def test_metrics():
    response = client.get("/metrics")
    assert response.status_code == 200


def test_analyze_endpoint():
    response = client.post("/api/v1/ai/analyze", json={
        "text": "Пожар в здании на улица Ленина 5, сильное задымление, один пострадавший",
        "call_id": "c-1",
    })
    assert response.status_code == 200
    data = response.json()
    assert data["incident_type"] == "FIRE"
    assert data["casualties_count"] == 1
    assert len(data["recommendations"]) > 0
