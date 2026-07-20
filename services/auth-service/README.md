# Auth Service

Сервис аутентификации и авторизации для платформы EASUR.

## Функциональность

- Управление пользователями
- Аутентификация (локальная, LDAP, OAuth2)
- Управление ролями и разрешениями
- Управление API ключами
- MFA (multi-factor authentication)

## Технический стек

- Node.js / Nest.js (или другой выбранный фреймворк)
- PostgreSQL / MongoDB
- Redis (для сессий и кэша)
- JWT для токенов

## Структура проекта

```
auth-service/
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

## Переменные окружения

```
DATABASE_URL=
REDIS_URL=
JWT_SECRET=
JWT_EXPIRY=
```

## API Endpoints

[Будет добавлено]

## Запуск

```bash
npm install
npm run dev
```
