# Secrets Management — ЕАСУР

- **Status:** APPROVED · Stage 3 · ADR-011 · Подчиняется Security Architecture, EGS.

Централизованное управление секретами: **HashiCorp Vault** (хранилище) + **External Secrets
Operator** (доставка в Kubernetes). Значения секретов в git отсутствуют (RGS, EGS).

## Структура доступа
- Аутентификация нагрузок — Kubernetes auth (ServiceAccount → роль Vault).
- Политики Vault по принципу минимальных привилегий: сервис читает только свои пути
  (`<service>/*`).
- `SecretStore`/`ExternalSecret` — `kubernetes/platform/config/secret-store.yaml` (без значений).

## Политика ротации
- Регулярная ротация секретов (по классу критичности); динамические секреты БД — по возможности.
- После ротации — синхронизация ExternalSecret (`refreshInterval`) и перезапуск потребителей.

## Аудит доступа
- Vault audit device фиксирует все обращения к секретам.
- Доступ администраторов — под контролем (Operational Security, POF).

## Аварийное восстановление
- Снапшоты Vault (Backup Policy); unseal по регламенту (Shamir, кворум держателей ключей).
- Восстановление — Disaster Recovery Plan, Runbook `secrets-operations`.

## Соответствие
Constitution ARTICLE 10; секреты не в git/образах/конфигах; суверенно и self-hosted (AGSDS).
