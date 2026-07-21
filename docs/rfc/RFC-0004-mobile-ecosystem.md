# RFC-0004 — Мобильная экосистема

- **Статус:** Closed (Approved → Implemented → Verified)
- **Автор:** Mobile/Architecture Review (Stage 7)
- **Дата:** 2026-07-21
- **Связанные ADR:** ADR-016 (стек), ADR-017 (offline-sync), ADR-018 (MDM)
- **Связанные документы:** Technology Mapping (`CAP-MOBILE-001`, `CAP-SYNC-001`, `CAP-MDM-001`),
  Mobile/Synchronization Architecture, стандарты Stage 7

## 1. Причина изменения
Stage 7 создаёт единую мобильную экосистему (полевые приложения и приложения руководителей) с
offline-first, единым SDK/дизайн-системой, безопасностью и MDM. Требуется утвердить стек и
абстракции без нарушения Architecture Freeze.

## 2. Существующее решение
Web-АРМ диспетчера (ADR-004); плейсхолдеры `apps/mobile-crew`, `apps/mobile-citizen`. Мобильная
платформа/SDK/offline-sync/MDM отсутствовали.

## 3. Предлагаемое решение
Единый кросс-платформенный стек + общий Mobile SDK (ADR-016); offline-first дельта-синхронизация с
версионированием (ADR-017); абстракция MDM (ADR-018). Общие платформенные API для sync/messaging/
forms/media/telemetry/MDM (без app-специфичных API).

## 4. Impact Analysis
- Модули/сервисы: без изменения кода существующих backend-сервисов (Stage 7 — мобильный слой +
  контракты + доменные документы).
- API: новые общие контракты `contracts/internal/*` (mobile-sync, messaging, forms, media,
  field-telemetry, mdm), API First; переиспользуются существующими сервисами платформы.
- Frontend: добавлены приложения `apps/{mobile-crew,mobile-commander,mobile-command}` + Mobile SDK.
- Безопасность: шифрование хранилища, защищённые ключи, MDM, журнал безопасности (Mobile/Device
  Security Standards).

## 5. Compatibility Check
Обратная совместимость сохранена; миграция данных не требуется; версии API — новые; клиенты —
через контракты/SDK; документация синхронизирована.

## 6. Риски
Сложность offline-sync и разрешения конфликтов (риск потери данных). Снижение — версионирование,
outbox, проверка целостности, ручное разрешение критичных конфликтов (Offline Synchronization
Standard).

## 7. Migration Plan
Подготовка (ADR-016..018, каталог) → реализация (контракты, SDK, дизайн-токены, скаффолды) →
проверка (валидаторы/OpenAPI) → внедрение приложений через MDM → мониторинг → завершение.

## 8. Rollback Plan
Откат контрактов/скаффолдов/SDK из VCS; существующие сервисы и web-АРМ не затрагиваются.

## 9. Document Synchronization
ADR-016..018, ADR Index, RFC Index, Technology Mapping, каталог версий, docs/README, apps/README.

## 10. Definition of Done для RFC
- [x] ADR созданы (016, 017, 018)
- [x] Impact Analysis выполнен
- [x] Migration/Rollback планы готовы
- [x] Документация синхронизирована
- [x] Реализовано и проверено (Verified)
