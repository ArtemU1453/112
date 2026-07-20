# Shared Events

Event-driven архитектура и типы событий для системы.

## Структура

```
events/
├── incident/
│   ├── incident-created.event.ts
│   ├── incident-updated.event.ts
│   └── incident-closed.event.ts
├── dispatch/
│   ├── dispatch-assigned.event.ts
│   └── dispatch-completed.event.ts
├── notification/
│   └── notification-sent.event.ts
├── user/
│   ├── user-created.event.ts
│   └── user-logged-in.event.ts
├── base.event.ts            # Базовый класс Event
└── index.ts
```

## Использование

```typescript
import { IncidentCreatedEvent } from '@easur/events';
import { EventBus } from '@easur/common';

const event = new IncidentCreatedEvent({
  incidentId: '123',
  title: 'Пожар в офисном здании'
});

await eventBus.publish(event);
```

## Принципы

- Все события наследуют BaseEvent
- Имена: {Domain}Event
- Файлы: {domain}.{action}.event.ts
- Содержат только data, без логики
