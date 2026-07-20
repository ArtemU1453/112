package by.mchs.e112.incident.domain;

/**
 * Типы происшествий по классификатору МЧС РБ.
 */
public enum IncidentType {
    FIRE,               // Пожар
    MEDICAL,            // Медицинская помощь
    POLICE,             // Правонарушение
    GAS_LEAK,           // Утечка газа
    TRAFFIC_ACCIDENT,   // ДТП
    WATER_RESCUE,       // Происшествие на воде
    HAZMAT,             // ЧС с опасными веществами
    TECHNOLOGICAL,      // Техногенная авария
    NATURAL,            // Природная ЧС
    FALSE_ALARM,        // Ложный вызов
    OTHER               // Прочее
}
