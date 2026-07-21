# Service Discovery — ЕАСУР

- **Status:** APPROVED · Stage 3 · Подчиняется Platform/Network Architecture.

## Механизм
Обнаружение сервисов — через встроенный DNS Kubernetes (Service → ClusterIP). Клиенты обращаются
по стабильным именам сервисов внутри кластера; внешние клиенты — только через Ingress/Gateway.

## Обоснование
На текущей Baseline отдельный discovery-компонент не требуется (KISS/YAGNI): DNS Kubernetes и
Gateway покрывают потребности. Плейсхолдер `services/discovery` зарезервирован; введение
выделенного discovery — через ADR/RFC.

## Надёжность
Readiness-пробы исключают неготовые поды из ротации Service; сетевые политики ограничивают
доступ (Network Architecture).
