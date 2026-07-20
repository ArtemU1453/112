# realtime-service

Трансляция доменных событий в АРМ диспетчера через WebSocket (STOMP).

- Порт: **8088**, эндпоинт: `/ws` (SockJS + нативный WebSocket)
- Мост Kafka → STOMP: топики `/topic/incidents`, `/topic/dispatch`, `/topic/units`, `/topic/calls`
- Слушает Kafka: `incident.created`, `incident.updated`, `dispatch.assigned`, `unit.status-changed`, `call.analyzed`
- Маршрутизируется через gateway по пути `/ws/**`

## Тесты

`mvn test` — проверка формирования RealtimeEvent и маршрутизации по топикам.
