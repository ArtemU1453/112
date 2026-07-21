# Runbook — Операции с секретами (Vault)

- **Подчиняется:** ADR-011, Security Architecture, Secrets Management.

## Проверка состояния
```bash
kubectl -n emergency-112-platform get pods -l app=vault
kubectl -n emergency-112 get externalsecret,secretstore
```

## Unseal (после рестарта Vault)
Выполняется по регламенту с использованием ключей unseal, хранящихся по процедуре разделения
секрета (Shamir). Ключи НЕ хранятся в git и НЕ логируются. Требуется кворум держателей ключей.

## Ротация секрета
1. Обновить значение в Vault (по политике доступа).
2. External Secrets синхронизирует в K8s Secret (`refreshInterval`).
3. Перезапустить потребителей при необходимости (rolling restart).

## Диагностика доступа

| Симптом | Действия |
|---------|----------|
| ExternalSecret SyncError | Проверить SecretStore/аутентификацию SA; политику Vault |
| Приложение 401 к БД | Проверить актуальность Secret; ротацию; перезапуск пода |

## DR
Снапшоты Vault — по Backup Policy; восстановление и unseal — Disaster Recovery Plan.
Доступ к секретам — под аудитом (Vault audit device, Operational Security).
