# telephony-service

Регистрация и жизненный цикл экстренных вызовов 112.

- Порт: **8084**, БД: `telephony_db`
- Статусная модель вызова: RINGING → ACTIVE → (ON_HOLD) → COMPLETED → TRANSCRIBED → ANALYZED
- При завершении вызова публикует `call.received` (recordingUrl) для ai-service
- Kafka consumer `call.analyzed`: сохраняет транскрипт и связывает с карточкой происшествия
- Привязка вызова к карточке происшествия (`incidentId`)

## Тесты

`mvn verify` — unit-тесты домена/сервиса + IT (Testcontainers PostgreSQL).
