# Integration Architecture — ЕАСУР

- **Status:** APPROVED · Stage 4 · Подчиняется Integration Governance Standard, Canonical Data
  Model Standard.

## Универсальная интеграция
Единый контракт `IntegrationConnector` (порт, `platform-abstractions`) поверх транспортов:
**REST, gRPC, Message Bus (Kafka), файловый обмен, пакетный обмен**. Конкретный транспорт скрыт
за реализацией-адаптером (TAP).

## Каноническая модель данных
Обмен между подсистемами — через каноническую модель (Canonical Data Model Standard): единые
представления общих сущностей, версионируемые; преобразование вход/выход — на границах адаптеров.

## Синхронная vs асинхронная
- Синхронно — REST/gRPC по контрактам (`contracts/`, API First).
- Асинхронно — события Kafka (предпочтительно; ADR-008, Messaging Architecture).
- Файловый/пакетный обмен — для интеграций с внешними системами (в т. ч. офлайн, AGSDS).

## Governance
Интеграции заводятся через Integration Governance Standard (контракт, владелец, безопасность,
мониторинг); внешние — через RFC/ADR. Идемпотентность и обработка ошибок — Messaging Architecture.

## Соответствие
API Guidelines, Event Governance Standard, Security Architecture. Без прикладной логики (Stage 4).
