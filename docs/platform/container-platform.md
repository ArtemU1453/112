# Container Platform — ЕАСУР

- **Status:** APPROVED · Stage 3 · Подчиняется ADR-005, Security Architecture, CI/CD.

## Базовые образы
Минимальные образы из доверенного локального реестра; многостадийные сборки; non-root runtime,
где возможно (Coding Standards / Dockerfile).

## Стандарты Dockerfile
Явные версии базовых образов (согласно каталогу версий); `.dockerignore`; кэшируемые слои;
health-пробы согласованы с приложением. Линт — hadolint (config/quality).

## Registry policy
Только доверенный реестр; в air-gapped — локальное зеркало (`CAP-REGISTRY-001`, AGSDS).

## Image signing / scanning
Подпись образов и сканирование уязвимостей — в CI/CD (security/dependency-scan); блок при
critical/high (DepGS).

## Image lifecycle
Теги = SHA + канал (`stable`); удаление устаревших; хранение релизных образов дольше (Release
Governance Standard).
