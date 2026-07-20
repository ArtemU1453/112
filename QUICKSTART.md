# Быстрый старт

## Требования

- Node.js >= 18.0.0
- npm >= 9.0.0
- Docker & Docker Compose
- Git

## 1. Клонирование репозитория

```bash
git clone https://github.com/ArtemU1453/112.git
cd 112
```

## 2. Установка зависимостей

```bash
npm run install-all
```

Это установит зависимости для всех пакетов в monorepo используя Lerna.

## 3. Запуск инфраструктуры

```bash
docker-compose up -d
```

Это запустит:
- PostgreSQL (порт 5432)
- Redis (порт 6379)
- MongoDB (порт 27017)
- RabbitMQ (порт 5672, управление: 15672)
- Prometheus (порт 9090)
- Grafana (порт 3000)
- Elasticsearch (порт 9200)
- Kibana (порт 5601)
- Jaeger (портал: 16686)

## 4. Запуск сервисов в режиме разработки

### Все сервисы сразу

```bash
npm run dev
```

### Отдельный сервис

```bash
cd services/auth-service
npm run dev
```

## 5. Проверка статуса

```bash
# Тесты
npm run test

# Линтинг
npm run lint

# Сборка
npm run build
```

## Структура проекта

```
├── services/           # Микросервисы
├── shared/            # Общие библиотеки
├── infrastructure/    # Инфра конфигурации
├── docs/              # Документация
├── docker-compose.yml # Инфраструктура
├── package.json       # Workspace конфигурация
└── lerna.json        # Lerna конфигурация
```

## Использованные технологии

### Backend
- Node.js
- TypeScript
- Nest.js
- PostgreSQL / MongoDB
- Redis
- RabbitMQ / Kafka

### DevOps
- Docker & Docker Compose
- Kubernetes
- Terraform
- Prometheus & Grafana

### Мониторинг
- Prometheus (метрики)
- ELK Stack (логирование)
- Jaeger (трассировка)

## Разработка нового сервиса

1. Создайте директорию в `services/`
2. Инициализируйте `package.json`
3. Используйте shared библиотеки из `shared/`
4. Добавьте конфигурацию в `infrastructure/`

## Документация

- [Архитектура](./docs/README.md)
- [Правила разработки](./CONTRIBUTING.md)
- [API документация](./docs/api/)
- [Развёртывание](./docs/deployment/)

## Общие команды

```bash
# Установка всего
npm run install-all

# Запуск в dev режиме
npm run dev

# Тесты
npm run test

# Линтинг
npm run lint

# Сборка
npm run build

# Docker сборка
npm run docker:build

# Docker push
npm run docker:push
```

## Обмен информацией между сервисами

### REST API
Синхронные вызовы через HTTP

### Event Bus
Асинхронные события через Message Broker (RabbitMQ/Kafka)

## Логирование и Мониторинг

- Логи: Elasticsearch + Kibana (порт 5601)
- Метрики: Prometheus (9090) + Grafana (3000)
- Трассировка: Jaeger (16686)

## Решение проблем

### Порты уже заняты

```bash
docker-compose down -v
docker-compose up -d
```

### Очистить node_modules

```bash
rm -rf node_modules
npm run install-all
```

### Пересборка Docker образов

```bash
docker-compose build --no-cache
docker-compose up -d
```

## Поддержка

- Откройте Issue на GitHub
- Свяжитесь с командой разработки
- Проверьте документацию в `/docs`

## Лицензия

MIT
