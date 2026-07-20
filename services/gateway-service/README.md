# gateway-service

API Gateway системы 112 на Spring Cloud Gateway (reactive).

- Порт: **8080** — единая точка входа
- JWT resource server (Keycloak), проброс токена в нижестоящие сервисы
- Маршрутизация по префиксам путей ко всем микросервисам
- CORS для АРМ диспетчера (`localhost:3000`, `dispatch.112.by`)
- Rate limiting через Redis (20 rps, burst 40) по имени пользователя/IP
- WebSocket-маршрут `/ws/**` → realtime-service

## Маршруты

| Путь | Сервис |
|---|---|
| `/api/v1/users/**` | auth-service |
| `/api/v1/incidents/**` | incident-service |
| `/api/v1/units/**`, `/api/v1/assignments/**` | dispatch-service |
| `/api/v1/calls/**` | telephony-service |
| `/api/v1/gis/**` | gis-service |
| `/api/v1/audit/**` | audit-service |
| `/api/v1/notifications/**` | notification-service |
| `/api/v1/ai/**` | ai-service |
| `/ws/**` | realtime-service |
