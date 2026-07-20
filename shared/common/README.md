# Shared Common

Общие утилиты, хелперы и конфигурации для всех сервисов.

## Модули

### Logger
Централизованное логирование

```typescript
import { Logger } from '@easur/common';

const logger = new Logger('ServiceName');
logger.info('Message');
logger.error('Error', error);
```

### Config
Управление конфигурацией

```typescript
import { Config } from '@easur/common';

const dbUrl = Config.get('DATABASE_URL');
```

### Database
ORM и подключение к БД

### Cache
Redis кэширование

### EventBus
Pub/sub для событий

### HTTP Client
HTTP клиент для межсервисного взаимодействия

## Структура

```
common/
├── logger/
├── config/
├── database/
├── cache/
├── event-bus/
├── http-client/
├── decorators/
├── exceptions/
├── guards/
├── middleware/
└── index.ts
```

## Использование

```typescript
import { Logger, Config, EventBus } from '@easur/common';
```
