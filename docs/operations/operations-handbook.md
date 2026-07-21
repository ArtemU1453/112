# Operations Handbook — ЕАСУР

- **Status:** APPROVED · Stage 10 · Реализует подэтап 10.8. Согласован с Platform Operations Framework,
  Operational Procedures (Stage 3), Production Support Standard, Maintenance Window Standard.

## 1. Назначение
Настольное руководство эксплуатирующего персонала: как поддерживать промышленную работу ЕАСУР 24/7.
Не дублирует платформенные [Operational Procedures](../platform/operational-procedures.md) и
[Runbooks](../platform/runbooks/README.md), а связывает их в эксплуатационный распорядок.

## 2. Ежедневные операции
- Проверка здоровья сервисов (health/readiness), дашбордов SLA/SLO, очередей, интеграций.
- Обзор алертов и открытых инцидентов; передача смены (handover) с фиксацией состояния.
- Контроль резервного копирования (успешность заданий), свободных ресурсов, сертификатов/секретов.

## 3. Наблюдаемость
Метрики (Prometheus), логи (Loki), трассы (Tempo/OTel), дашборды (Grafana), алерты. Ключевые
индикаторы критического пути: приём вызова, регистрация инцидента, диспетчеризация, интеграции.

## 4. Регламентные работы
Обновления/патчи — по [Maintenance Window Standard](../standards/Maintenance-Window-Standard.md);
ротация секретов/сертификатов (Vault/PKI); проверка бэкапов и DR-учения по графику.

## 5. Инциденты и эскалация
Классификация и эскалация — [Incident Escalation Standard](../standards/Incident-Escalation-Standard.md);
процедуры восстановления — [Production Runbook](production-runbook.md) и платформенные runbooks;
крупные сбои — DR Plan.

## 6. Изменения
Любые изменения — через релизный процесс (Release Acceptance) и, при затрагивании архитектуры, RFC/ADR.
Прямые правки в продуктиве запрещены.

## 7. Роли и связь
Дежурная смена, координатор инцидентов, владельцы сервисов, безопасность, DR-координатор; каналы
уведомления и статус-коммуникации.
