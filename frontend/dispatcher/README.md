# frontend-dispatcher — АРМ диспетчера

Автоматизированное рабочее место диспетчера 112: React 19 + TypeScript + MUI +
Redux Toolkit + OpenLayers + WebSocket (STOMP).

## Возможности

- **Обстановка**: журнал активных происшествий (MUI DataGrid), карточка с историей,
  сменой статуса, авто-назначением наряда; форма создания карточки
- **Карта** (OpenLayers/OSM): происшествия по приоритету + подразделения по статусу, центр — Минск
- **Подразделения**: таблица сил и средств с автообновлением
- **Аудит**: журнал событий всех сервисов
- **Реальное время**: WebSocket-подписки `/topic/incidents`, `/topic/units`, `/topic/dispatch`
- **Аутентификация**: Keycloak (OIDC, PKCE S256), проброс JWT во все запросы

## Разработка

```bash
npm install
npm run dev        # http://localhost:3000, прокси на gateway :8080
npm run build      # прод-сборка (tsc + vite)
npm run lint
```

Переменные окружения — см. `.env.example`.

## Прод

Docker-образ на nginx (`Dockerfile`), проксирует `/api` и `/ws` на gateway-service.
