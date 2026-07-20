from app.services.classifier import classify


def test_fire_high_priority():
    result = classify("Горит квартира на пятом этаже, сильное задымление")
    assert result.incident_type == "FIRE"
    assert result.priority in ("HIGH", "CRITICAL")
    assert result.confidence > 0.5


def test_fire_with_trapped_people_is_critical():
    result = classify("Пожар в доме, люди заблокированы, не могут выйти, есть дети")
    assert result.incident_type == "FIRE"
    assert result.priority == "CRITICAL"


def test_medical_case():
    result = classify("Человеку плохо с сердцем, нужна скорая, без сознания")
    assert result.incident_type == "MEDICAL"
    assert result.priority == "CRITICAL"


def test_traffic_accident():
    result = classify("Произошло ДТП, машина сбила пешехода, есть пострадавший")
    assert result.incident_type == "TRAFFIC_ACCIDENT"
    assert result.priority in ("HIGH", "CRITICAL")


def test_gas_leak():
    result = classify("Сильно пахнет газом в подъезде, запах газа")
    assert result.incident_type == "GAS_LEAK"
    assert result.priority in ("HIGH", "CRITICAL")


def test_unknown_defaults_to_other():
    result = classify("Здравствуйте, хотел уточнить информацию")
    assert result.incident_type == "OTHER"
    assert result.priority == "MEDIUM"
