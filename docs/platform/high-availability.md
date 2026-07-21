# High Availability — ЕАСУР

- **Status:** APPROVED · Stage 3 · Подчиняется Platform Architecture, DR Plan, POF.

## Отказоустойчивость
- Control plane Kubernetes — HA (≥3 узла); worker-узлы — с запасом ёмкости.
- Приложения — несколько реплик (Helm `replicas`), PodDisruptionBudget (при необходимости).
- Данные — репликация БД, HA для Vault/объектного хранилища/Kafka.

## Балансировка
Service (ClusterIP) распределяет трафик по готовым подам; Ingress балансирует внешние запросы.

## Автоматическое восстановление
Самовосстановление Kubernetes (перезапуск/перепланирование подов); readiness/liveness-пробы;
HPA — масштабирование по нагрузке (в т. ч. шторм вызовов, R-Sc1).

## Масштабирование
Горизонтальное (HPA/узлы) для stateless-сервисов; вертикальное — по квотам/limit ranges.
Планирование — Capacity Management (POF).

## Связь с DR
При отказе площадки — переключение на DR (Disaster Recovery Plan). Цели RPO/RTO — Backup Policy.
