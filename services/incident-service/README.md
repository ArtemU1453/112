# Incident Service

Сервис управления инцидентами.

## Функциональность

- Создание и регистрация инцидентов
- Отслеживание статуса инцидента
- Управление приоритетом
- История изменений
- Интеграция с другими сервисами

## API Контракты

Находятся в `shared/contracts/incident.ts`

## Структура

```
incident-service/
├── src/
│   ├── modules/
│   ├── controllers/
│   ├── services/
│   ├── entities/
│   └── main.ts
├── test/
├── docker/
├── package.json
└── README.md
```

## Запуск

```bash
npm install
npm run dev
```
