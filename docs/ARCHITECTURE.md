# Архитектура EASUR Platform

## Обзор

EASUR Platform — это интегрированная система для управления чрезвычайными ситуациями и диспетчеризации ресурсов.

Система использует **микросервисную архитектуру** на базе Node.js/TypeScript с асинхронной обработкой событий.

## Архитектурные уровни

### 1. Presentation Layer
- REST API (Express/Nest.js)
- WebSocket (Real-time updates)
- GraphQL (опционально)

### 2. Application Layer
- Business Logic
- Service Orchestration
- Event Handlers

### 3. Domain Layer
- Domain Models
- Domain Services
- Event Sourcing

### 4. Infrastructure Layer
- Database (PostgreSQL, MongoDB)
- Cache (Redis)
- Message Broker (RabbitMQ, Kafka)
- File Storage (S3, Local)

## Компоненты системы

### Core Services

#### 1. Auth Service (Порт 3000)
- Аутентификация и авторизация
- Управление пользователями
- Управление ролями и разрешениями

#### 2. Incident Service (Порт 3001)
- Управление инцидентами
- Отслеживание статуса
- История изменений

#### 3. Dispatch Service (Порт 3002)
- Распределение ресурсов
- Оптимизация маршрутов
- Управление очередью

#### 4. Notification Service (Порт 3003)
- Отправка уведомлений (email, SMS, push)
- Шаблоны уведомлений
- История отправок

#### 5. GIS Service (Порт 3004)
- Геолокация
- Карты
- Геопространственный анализ

#### 6. Real-time Service (Порт 3005)
- WebSocket подключения
- Real-time обновления
- Broadcast сообщений

#### 7. Telephony Service (Порт 3006)
- Интеграция с VoIP
- Управление звонками
- CTI интеграция

#### 8. AI Service (Порт 3007)
- ML модели для анализа
- Предсказания
- Рекомендации

#### 9. Audit Service (Порт 3008)
- Логирование действий
- История операций
- Compliance tracking

### Shared Components

#### contracts/
Общие типы данных и интерфейсы
```typescript
export interface Incident {
  id: string;
  title: string;
  status: IncidentStatus;
  priority: Priority;
}
```

#### events/
Event-driven коммуникация между сервисами
```typescript
export class IncidentCreatedEvent {
  constructor(public data: { incidentId: string; ... }) {}
}
```

#### common/
Утилиты и инструменты:
- Logger
- Config Manager
- Database Client
- Cache Client
- HTTP Client
- Event Bus

## Паттерны взаимодействия

### 1. Синхронное (Request-Response)

```typescript
// Service A -> Service B (REST)
const httpClient = new HttpClient('http://service-b:3000');
const result = await httpClient.get('/api/resource/123');
```

**Когда использовать:**
- Когда нужен результат немедленно
- Для GET запросов
- Для критических операций

### 2. Асинхронное (Event-Driven)

```typescript
// Service A -> Event Bus -> Service B
const event = new IncidentCreatedEvent({ ... });
await eventBus.publish(event);

// Service B
eventBus.subscribe(IncidentCreatedEvent, async (event) => {
  await handleIncident(event.data);
});
```

**Когда использовать:**
- Для лучше производительности
- Для независимых операций
- Для обработки побочных эффектов
- Для отказоустойчивости

## Данные и хранилище

### PostgreSQL
Основная база для структурированных данных:
- Users, Roles, Permissions
- Incidents, Dispatches
- Audit Logs

### MongoDB
Для слабо структурированных данных:
- Document storage
- Dynamic schemas
- Flexible queries

### Redis
Для кэширования и сессий:
- Session storage
- Cache layer
- Real-time data

### Message Broker (RabbitMQ/Kafka)
Для асинхронной обработки:
- Event publishing
- Message queues
- Task scheduling

## Развёртывание

### Локальная разработка
```bash
docker-compose up -d
npm run dev
```

### Kubernetes (Production)
```bash
kubectl apply -k infrastructure/kubernetes/overlays/prod
```

### Infrastructure as Code (Terraform)
```bash
cd infrastructure/terraform/environments/prod
terraform apply
```

## Мониторинг и Наблюдаемость

### Метрики (Prometheus + Grafana)
- Request latency
- Error rates
- CPU/Memory usage
- Business metrics

### Логирование (ELK Stack)
- Centralized logging
- Full-text search
- Log aggregation

### Трассировка (Jaeger)
- Distributed tracing
- Performance debugging
- Request flow visualization

## Безопасность

### Authentication
- JWT токены
- Refresh токены
- Multi-factor authentication

### Authorization
- Role-Based Access Control (RBAC)
- Fine-grained permissions
- Policy-based access

### Data Protection
- TLS/SSL encryption
- Encrypted passwords (bcrypt)
- Secrets management (Vault)

## Масштабируемость

### Horizontal Scaling
- Stateless services
- Load balancing
- Database replication

### Vertical Scaling
- Increased resources
- Database optimization
- Caching strategies

### Database Scaling
- Read replicas
- Sharding
- Connection pooling

## Резистентность

### Circuit Breaker
- Fault tolerance
- Graceful degradation
- Fallback strategies

### Retry Logic
- Exponential backoff
- Max retry attempts
- Dead letter queues

### Bulkheads
- Resource isolation
- Thread pools
- Connection limits

## Диаграмма архитектуры

```
┌─────────────────────────────────────────────────────────────┐
│                    Client Applications                      │
│              (Web, Mobile, Desktop, Third-party)            │
└────────────────────────┬────────────────────────────────────┘
                         │ HTTP/WebSocket
┌────────────────────────▼────────────────────────────────────┐
│                     API Gateway                             │
│            (Authentication, Rate Limiting, Routing)         │
└────┬──────────┬──────────┬──────────┬──────────┬────────────┘
     │          │          │          │          │
┌────▼──┐  ┌────▼──┐  ┌────▼──┐  ┌────▼──┐  ┌──▼─────┐
│ Auth  │  │Incident│  │ GIS  │  │ Real- │  │   AI   │
│Service│  │Service │  │Service│  │time   │  │ Service│
│       │  │        │  │       │  │Service│  │        │
└────┬──┘  └───┬────┘  └───┬───┘  └───┬───┘  └──┬─────┘
     │         │           │          │         │
     └─────────┼───────────┼──────────┼─────────┘
               │           │          │
        ┌──────▼───────────▼──────────▼───────┐
        │       Message Broker (RabbitMQ)     │
        └──────┬───────────┬──────────┬───────┘
               │           │          │
        ┌──────▼────────────▼──────────▼────────────┐
        │         Shared Libraries & Components      │
        │  (Contracts, Events, Common, Logging)     │
        └──────────────────────────────────────────┘
        
        ┌──────────────────────────────────────────┐
        │        Infrastructure Layer               │
        ├──────────────────────────────────────────┤
        │  DB: PostgreSQL │ MongoDB │ Redis        │
        │  Storage: S3 │ Local File System          │
        │  Monitoring: Prometheus │ Grafana        │
        │  Logging: ELK Stack                      │
        │  Tracing: Jaeger                         │
        └──────────────────────────────────────────┘
```

## Ссылки

- [README](./README.md)
- [Быстрый старт](./QUICKSTART.md)
- [Правила разработки](./CONTRIBUTING.md)
- [API Документация](./docs/api/)
