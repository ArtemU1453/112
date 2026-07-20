# notification-service

Отправка уведомлений (SMS/Email/Push) с журналом доставки.

- Порт: **8087**, БД: `notification_db`
- Гексагональная архитектура: порт `ChannelSender`, адаптеры Email (SMTP), SMS (HTTP-шлюз), Push
- Kafka consumers: `notification.requested` (прямые запросы), `dispatch.assigned` (push экипажу)
- Журнал статусов PENDING/SENT/FAILED с текстом ошибки

## Тесты

`mvn verify` — IT на Testcontainers PostgreSQL (маршрутизация по каналам, журнал).
