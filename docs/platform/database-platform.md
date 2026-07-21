# Database Platform — ЕАСУР

- **Status:** APPROVED · Stage 3 · ADR-003 · Подчиняется Database Governance Standard.

## Кластеры
PostgreSQL + PostGIS (`CAP-DB-001/002`); database-per-service. HA — первичный + реплики.
Развёртывание в зоне данных (namespace `emergency-112-data`).

## Миграции
Только Liquibase (`ddl-auto: validate`); стратегия несовместимых изменений — expand → migrate →
contract (DGS). Проверка на Staging до Production (EGS).

## Резервирование и репликация
Реплики для чтения/отказоустойчивости; бэкапы — Backup Policy; восстановление — DR Plan/Runbook.

## Мониторинг
Метрики подключений/репликации/производительности (Prometheus); алерты (пул соединений, отставание
реплики); диагностика — Runbook `database-operations`.

## Кэш
Redis (`CAP-CACHE-001`) — кэш чтения и rate limiting; в зоне данных.
