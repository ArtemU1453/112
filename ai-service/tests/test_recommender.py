from app.services.recommender import recommend


def test_fire_dispatch_includes_trucks():
    recs = recommend("FIRE", "HIGH", 0)
    types = {r.unit_type for r in recs}
    assert "FIRE_TRUCK" in types
    assert "LADDER_TRUCK" in types


def test_critical_fire_reinforced():
    normal = recommend("FIRE", "HIGH", 0)
    critical = recommend("FIRE", "CRITICAL", 0)
    normal_trucks = next(r.count for r in normal if r.unit_type == "FIRE_TRUCK")
    critical_trucks = next(r.count for r in critical if r.unit_type == "FIRE_TRUCK")
    assert critical_trucks > normal_trucks


def test_many_casualties_add_ambulances():
    recs = recommend("TRAFFIC_ACCIDENT", "HIGH", 6)
    ambulances = next(r.count for r in recs if r.unit_type == "AMBULANCE")
    assert ambulances >= 3


def test_medical_single_ambulance():
    recs = recommend("MEDICAL", "HIGH", 0)
    assert len(recs) == 1
    assert recs[0].unit_type == "AMBULANCE"
