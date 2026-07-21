# Messaging Architecture — ЕАСУР

- **Status:** APPROVED · Stage 4 · ADR-008 · Подчиняется Event Governance Standard.

## Основа
Apache Kafka (`CAP-MSG-001`, ADR-008) как шина команд и событий. Общая оболочка сообщения —
`Message` (порт `platform-abstractions.messaging`) с идентификатором, типом, `correlationId`,
временем и заголовками.

## Команды и события
- **Команды** — намерение изменить состояние (адресно, один обработчик).
- **События** — свершившийся факт (прошедшее время; fan-out нескольким потребителям).
Именование топиков/событий — Event Governance Standard (`<context>.<event>`, версия суффиксом).

## Гарантии и надёжность
- **Идемпотентность:** `IdempotencyStore` — однократная бизнес-обработка при повторной доставке
  (at-least-once → эффективно once).
- **Retry Policy:** `RetryPolicy` — экспоненциальная задержка с ограничением попыток.
- **Dead Letter Queue:** `DeadLetterQueue` — перенос неустранимо сбойных сообщений для разбора.
- Порядок — в пределах ключа партиции; продюсеры идемпотентны (`acks=all`).

## Обработка ошибок
Повтор по политике → при исчерпании DLQ → алерт (consumer lag/DLQ rate, Operational Telemetry).

## Соответствие
Event Governance Standard, Integration Architecture, Observability. Без прикладной логики (Stage 4).
