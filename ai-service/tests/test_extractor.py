from app.services.extractor import extract, extract_address, extract_casualties, extract_phone


def test_extract_address_street_and_house():
    address = extract_address("Пожар по адресу улица Немига 3, второй этаж")
    assert address is not None
    assert "Немига" in address


def test_extract_phone_belarus():
    assert extract_phone("Мой номер +375291234567 звоните") == "+375291234567"


def test_extract_casualties_digit():
    assert extract_casualties("на месте 3 пострадавших") == 3


def test_extract_casualties_word():
    assert extract_casualties("двое ранены") == 2


def test_extract_casualties_implicit():
    assert extract_casualties("есть пострадавший") == 1


def test_extract_combined():
    result = extract("ДТП на проспект Независимости 10, двое пострадавших, тел 80291112233")
    assert result.address is not None and "Независимости" in result.address
    assert result.casualties_count == 2
    assert result.phone is not None


def test_fallback_phone_used():
    result = extract("Пожар в доме", fallback_phone="+375170000000")
    assert result.phone == "+375170000000"
