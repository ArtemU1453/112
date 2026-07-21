# Operational Procedures (SOP) — ЕАСУР

- **Status:** APPROVED · Stage 3 · Подчиняется Platform Operations Framework, Environment
  Governance Standard, Release Governance Standard.

Стандартные операционные процедуры (SOP) эксплуатации платформы. Детальные пошаговые действия —
в Runbooks (`runbooks/`).

## 1. Развёртывание платформы «с нуля»
1. Подготовка узлов: `infrastructure/ansible/site.yml`.
2. Платформенный слой: `terraform apply` в `infrastructure/terraform/environments/<env>`.
3. Манифесты платформы: `kubectl apply -k kubernetes/platform`.
4. Платформенные компоненты (ingress-nginx, cert-manager, External Secrets+Vault, наблюдаемость,
   объектное хранилище).
5. Приложения: Helm-чарт (вне объёма Stage 3).
Автоматизация: `infrastructure/scripts/platform-bootstrap.sh <env>`.

## 2. Плановые работы (maintenance window)
- Согласовать окно (Availability Management); уведомить заинтересованных.
- Change Request (POF): оценка → одобрение → выполнение → проверка → закрытие.
- Выполнять на Staging до Production (EGS).

## 3. Обновление
- Онлайн: через CI/CD (Release Governance Standard), rolling update.
- Офлайн (air-gapped): подготовка пакета `build-offline-bundle.sh` → проверка целостности/подписи
  → импорт (`offline-update.yml`) → проверка → возможность отката (AGSDS).

## 4. Откат
- Приложения: `helm rollback <release> <revision>`.
- Платформа: повторное применение предыдущей ревизии IaC/манифестов из VCS.
- Критерии/шаги — Release Governance Standard, Runbook отката.

## 5. Резервное копирование и восстановление
- Проверка выполнения бэкапов (CronJob); регулярная проверка восстановления
  (`verify-recovery.sh`). Процедуры — Backup Policy, DR Plan, Runbook.

## 6. Реагирование на инциденты
Регистрация → классификация → приоритизация → назначение → устранение → проверка → закрытие →
RCA (Incident Management, POF). Диагностика — Runbooks.

## 7. Проверка соответствия (перед вводом/периодически)
- Соответствие архитектуре/стандартам/безопасности; актуальность документации; готовность к
  восстановлению (Audit, POF).
- Чек-лист air-gapped (AGSDS «Проверка соответствия»).

## 8. Связанные документы
Platform Operations Framework · Deployment Architecture · Backup Policy · Disaster Recovery Plan ·
Runbooks · Air-Gapped Standard.
