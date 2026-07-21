# RFC-0002 — Платформенная инфраструктура

- **Статус:** Closed (Approved → Implemented → Verified)
- **Автор:** Platform/Architecture Review (Stage 3)
- **Дата:** 2026-07-21
- **Связанные ADR:** ADR-010 (IaC), ADR-011 (секреты), ADR-012 (трассировка), ADR-013 (объектное хранилище)
- **Связанные документы:** Technology Mapping (`CAP-IAC-001`, `CAP-SECRETS-001`, `CAP-TRACE-001`,
  `CAP-STORAGE-001`), Platform/Infrastructure/Security/Observability Architecture

## 1. Причина изменения
Этап 3 создаёт платформенную инфраструктуру. Требуется утвердить конкретные технологии для ранее
предложенных возможностей (`CAP-SECRETS-001`, `CAP-STORAGE-001`, `CAP-TRACE-001` были Proposed) и
ввести возможность IaC (`CAP-IAC-001`), не нарушая Architecture Freeze.

## 2. Существующее архитектурное решение
Baseline (ADR-001..009) определяет стек приложений и CI; платформенные возможности секретов,
трассировки и объектного хранилища были обозначены как Proposed без утверждённой технологии.

## 3. Предлагаемое решение
Утвердить: Terraform + Ansible (IaC), Vault + External Secrets (секреты), OpenTelemetry + Tempo
(трассировка), S3-совместимое self-hosted хранилище (объектное хранилище). Оформить ADR-010..013,
перевести соответствующие возможности в Approved (Technology Mapping).

## 4. Impact Analysis
- **Модули/сервисы:** без изменений кода сервисов (Stage 3 — только платформа).
- **API/БД:** не затронуты.
- **Frontend:** не затронут.
- **DevOps:** добавлены `infrastructure/{terraform,ansible,network,storage,scripts}`,
  `kubernetes/platform/*`, платформенные документы; версии — в каталоге (TVMS).
- **Безопасность:** усиление — централизованные секреты (Vault/ESO), Pod Security, Network Policies,
  аудит; см. Security Architecture.

## 5. Compatibility Check
- Обратная совместимость: сохранена (платформа добавляется, приложения не меняются).
- Миграция данных: не требуется.
- Версия API: не требуется.
- Изменение клиентов: не требуется.
- Обновление документации: ADR Index, Technology Mapping, каталог версий, docs/README.

## 6. Риски
- Эксплуатация новых stateful-компонентов (Vault, объектное хранилище): R-T1/R-S2. Снижение —
  HA, процедуры unseal/DR, включение в Backup Policy.

## 7. Migration Plan
1. Подготовка: ADR-010..013, каталог версий. 2. Реализация: IaC + платформенные манифесты + документы.
3. Тестирование: `helm template`/`kustomize build`/валидаторы. 4. Переключение: применение платформы.
5. Мониторинг. 6. Завершение (Verified).

## 8. Rollback Plan
- Критерий: неработоспособность платформенного компонента без обходного решения.
- Действия: откат манифестов/IaC из VCS; приложения не затронуты.
- Проверка: работоспособность существующих сервисов сохранена.

## 9. Document Synchronization
Обновлены: ADR-010..013, ADR Index, RFC Index, Technology Mapping, каталог версий, docs/README.

## 10. Definition of Done для RFC
- [x] ADR созданы (010..013)
- [x] Impact Analysis выполнен
- [x] Migration/Rollback планы готовы
- [x] Документация синхронизирована
- [x] Реализовано и проверено (Verified)
