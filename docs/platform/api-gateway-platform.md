# API Gateway Platform — ЕАСУР

- **Status:** APPROVED · Stage 3 · Подчиняется Architecture.md, Network/Security Architecture.

## Архитектура шлюза
Единая точка входа: Ingress (`easur-nginx`, IngressClass) → API Gateway приложений
(Spring Cloud Gateway, реализован в Stage 0). Данный документ описывает платформенный слой шлюза,
без бизнес-маршрутов.

## Функции периметра
- TLS-терминация (cert-manager), маршрутизация по хостам/путям.
- Аутентификация JWT (Keycloak issuer), CORS, ограничение частоты (rate limiting).
- Проксирование WebSocket (`/ws`) с корректными таймаутами/upgrade.

## Безопасность
Только доверенный трафик из edge-зоны к шлюзу (NetworkPolicy `allow-ingress-to-gateway`);
default-deny в остальном. Секреты TLS — через Secret/ExternalSecret.

## Наблюдаемость
Метрики/логи/трассы запросов на периметре; корреляция `correlationId`/`traceId`.
