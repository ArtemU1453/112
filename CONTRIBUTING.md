# Правила разработки

## Структура коммитов

Используем Conventional Commits:

```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

### Типы коммитов

- `feat`: Новая функция
- `fix`: Исправление бага
- `docs`: Изменения документации
- `style`: Форматирование кода (без изменения функциональности)
- `refactor`: Переструктурирование кода
- `perf`: Улучшения производительности
- `test`: Добавление/изменение тестов
- `chore`: Обновление зависимостей, конфигурации

### Примеры

```
feat(auth-service): add JWT token refresh
fix(incident-service): resolve memory leak
docs: update deployment guide
```

## Git Workflow

1. Создайте ветку от `main`: `git checkout -b feature/xyz`
2. Коммитьте регулярно с понятными сообщениями
3. Создайте PR для review
4. После approval мержьте в `main`

## Кодовые стандарты

### TypeScript

- Используйте строгую типизацию
- Документируйте public API
- Пишите юнит тесты для бизнес-логики
- Используйте ESLint и Prettier

### Архитектура сервиса

```
src/
├── modules/          # Бизнес-логика по доменам
├── controllers/      # REST endpoints
├── services/         # Business logic
├── entities/         # DB entities
├── dto/              # Data Transfer Objects
├── middleware/       # Express middleware
└── main.ts          # Entry point
```

## Тестирование

- Минимум 80% покрытие кода
- Используйте Jest
- Пишите интеграционные тесты для API

```bash
npm run test
npm run test:cov
```

## Развёртывание

1. Все PR должны проходить CI/CD
2. Тесты должны быть зелёные
3. Code review обязателен
4. После merge в main запускается автоматическое развёртывание

## Обмен информацией между сервисами

### Синхронно (REST API)

```typescript
import { HttpClient } from '@easur/common';

const client = new HttpClient('http://incident-service:3000');
const incident = await client.get('/incidents/123');
```

### Асинхронно (Event Bus)

```typescript
import { EventBus } from '@easur/common';
import { IncidentCreatedEvent } from '@easur/events';

await eventBus.publish(new IncidentCreatedEvent({...}));
```

## Локальная разработка

```bash
# Установка
npm run install-all

# Запуск всех сервисов
npm run dev

# Запуск одного сервиса
cd services/auth-service
npm run dev

# Тесты
npm run test

# Линтинг
npm run lint
```

## Переменные окружения

Каждый сервис должен иметь `.env.example`:

```
# .env.example
DATABASE_URL=postgresql://user:pass@localhost:5432/dbname
REDIS_URL=redis://localhost:6379
JWT_SECRET=your-secret-key
LOG_LEVEL=info
```

## Мониторинг и логирование

- Все важные события логируйте через Logger
- Добавьте метрики через Prometheus
- Используйте трассировку через Jaeger для отладки

```typescript
import { Logger, Metrics } from '@easur/common';

const logger = new Logger('ServiceName');
logger.info('Operation completed', { duration: 123 });

Metrics.histogram('operation_duration_ms', 123);
```
