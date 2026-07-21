# kubernetes/ — Kustomize base + overlays

Скелет манифестов для окружений (ADR-005). Прикладное развёртывание выполняется Helm-чартом
`../helm/emergency-112`; данный каталог задаёт окружение-специфичные оверлеи и общую базу.

```
kubernetes/
  base/                 # общие ресурсы (namespace, метки, общие настройки)
  overlays/
    development/
    test/
    stage/
    production/
```
Конкретные версии инструментов — `../config/toolchain/versions.yaml`.
