# Configuration Platform — ЕАСУР

- **Status:** APPROVED · Stage 3 · Подчиняется Configuration Standards, EGS.

## Централизованное управление
Источник истины — Git (`config/`, `environments/`) + Kubernetes ConfigMap/Secret. Приложения
получают параметры через переменные окружения (`${ENV:default}`).

## Реестр и аудит
Все изменения конфигурации — через VCS с review и журналом (POF Configuration Management).
Контроль дрейфа — сверка фактического состояния кластера с декларированным (IaC/манифесты).

## Секреты
Только через ExternalSecret/Vault (ADR-011); значений в git нет.

## Профили и feature flags
Профили окружений (`local/dev/test/stage/prod/k8s`); feature flags — часть конфигурации окружения
с владельцем и сроком жизни (Configuration Standards).

## Примечание о config-server
Отдельный config-server (плейсхолдер, `services/config-server`) на текущей Baseline не требуется;
его введение — через ADR/RFC.
