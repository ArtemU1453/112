# RFC-0006 — Национальная интеграционная платформа

- **Статус:** Closed (Approved → Implemented → Verified)
- **Автор:** Integration/Architecture Review (Stage 9)
- **Дата:** 2026-07-21
- **Связанные ADR:** ADR-022 (платформа), ADR-023 (безопасность)
- **Связанные документы:** Technology Mapping (`CAP-INTEG-PLATFORM-001`, `CAP-MSGSIGN-001`),
  National Integration / Integration Security Architecture, стандарты Stage 9

## 1. Причина изменения
Stage 9 создаёт единую платформу межведомственного взаимодействия (внешние системы) с Zero Trust,
версионированием контрактов, канонической моделью и полной трассируемостью. Требуется утвердить
интеграционную платформу и безопасность без нарушения Architecture Freeze; расширяет Stage 4
(IntegrationConnector, Integration/API Lifecycle/Canonical/Event governance).

## 2. Существующее решение
Stage 4: порт `IntegrationConnector`, стандарты Integration Governance / API Lifecycle / Canonical
Data Model / Event Governance. Отсутствовали национальная платформа обмена, реестр внешних систем,
интеграционная безопасность (mTLS/подпись/PKI), партнёрская песочница.

## 3. Предлагаемое решение
Единая интеграционная платформа (ADR-022, все обмены через неё; реестр обязателен); интеграционная
безопасность mTLS/подпись/PKI/anti-replay (ADR-023). Внешние контракты — канонические, версионируемые.

## 4. Impact Analysis
- Модули/сервисы: без изменения кода оперативных сервисов; интеграция — через платформу/адаптеры.
- API: новые контракты `contracts/external|admin/*` (integration-platform, registry, api-catalog,
  data-exchange, reference-data, monitoring, partner-sandbox), API First.
- БД: реестр внешних систем и НСИ — в интеграционной платформе; оперативные БД не затронуты.
- Безопасность: mTLS/подпись/PKI/аудит (Integration Security Architecture); секреты — Vault (ADR-011).

## 5. Compatibility Check
Обратная совместимость сохранена; прямые интеграции не создаются (обход запрещён); версии API —
через API Versioning Standard; документация синхронизирована.

## 6. Риски
Эксплуатация ESB/PKI; совместимость версий контрактов. Снижение — Contract Testing / Certification /
SLA стандарты, партнёрская песочница.

## 7. Migration Plan
Подготовка (ADR-022/023, каталог) → реализация (контракты, реестр, канон, безопасность) → проверка
(валидаторы/OpenAPI, contract testing) → подключение внешних систем через реестр/песочницу →
мониторинг → завершение.

## 8. Rollback Plan
Откат контрактов/конфигураций из VCS; оперативный контур не затрагивается; отзыв доступа внешних
систем через реестр.

## 9. Document Synchronization
ADR-022/023, ADR Index, RFC Index, Technology Mapping, каталог версий, docs/README.

## 10. Definition of Done для RFC
- [x] ADR созданы (022, 023)
- [x] Impact Analysis выполнен
- [x] Migration/Rollback планы готовы
- [x] Документация синхронизирована
- [x] Реализовано и проверено (Verified)
