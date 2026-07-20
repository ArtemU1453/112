import pytest

from app.services import analyzer


@pytest.mark.asyncio
async def test_analyze_text_full_flow(monkeypatch):
    async def fake_geocode(address):
        return 53.9045, 27.5665

    monkeypatch.setattr(analyzer, "_geocode", fake_geocode)

    result = await analyzer.analyze_text(
        "Горит квартира на улица Немига 3, есть двое пострадавших, дети заблокированы",
        call_id="call-1", caller_phone="+375291234567")

    assert result.incident_type == "FIRE"
    assert result.priority == "CRITICAL"
    assert result.address is not None and "Немига" in result.address
    assert result.latitude == 53.9045
    assert result.casualties_count == 2
    assert result.auto_create_incident is True
    assert any(r.unit_type == "FIRE_TRUCK" for r in result.recommendations)
    assert result.call_id == "call-1"


@pytest.mark.asyncio
async def test_analyze_low_confidence_no_autocreate(monkeypatch):
    async def fake_geocode(address):
        return None, None

    monkeypatch.setattr(analyzer, "_geocode", fake_geocode)
    result = await analyzer.analyze_text("Здравствуйте, просто уточнить вопрос")
    assert result.auto_create_incident is False
