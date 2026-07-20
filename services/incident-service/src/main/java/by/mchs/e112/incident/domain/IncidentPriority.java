package by.mchs.e112.incident.domain;

public enum IncidentPriority {
    CRITICAL,   // Угроза жизни, массовые ЧС — реакция немедленно
    HIGH,       // Угроза здоровью/имуществу — до 5 минут
    MEDIUM,     // Без прямой угрозы — до 15 минут
    LOW         // Справочные, некритичные — до 60 минут
}
