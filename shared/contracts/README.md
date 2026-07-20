# Shared Contracts

Общие типы данных и API контракты для всех сервисов.

## Структура

```
contracts/
├── incident.ts          # Типы инцидентов
├── user.ts             # Типы пользователя
├── dispatch.ts         # Типы диспетчеризации
├── notification.ts     # Типы уведомлений
├── gis.ts              # Типы геоданных
├── common.ts           # Общие типы
└── index.ts            # Экспорт всех типов
```

## Использование

```typescript
import { Incident, IncidentStatus } from '@easur/contracts';

const incident: Incident = {
  id: '123',
  title: 'Пожар',
  status: IncidentStatus.OPEN,
  // ...
};
```

## Правила

- Все типы должны быть в этой директории
- Одна тема - один файл
- Экспортировать всё через index.ts
- Документировать все public типы
