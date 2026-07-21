# RFC-0001 — Основная CI-платформа: GitHub Actions

- **Статус:** Closed (Approved → Implemented → Verified)
- **Автор:** Architecture Review (Stage 2)
- **Дата:** 2026-07-21
- **Связанные ADR:** ADR-009 (дополняет ADR-006)
- **Связанные документы:** Technology Mapping (`CAP-CI-001`), Toolchain Governance Standard,
  Quality Gates

## 1. Причина изменения
Этап 2 требует полного набора GitHub Actions workflow'ов. ADR-006 (Baseline) зафиксировал
GitLab CI. Репозиторий фактически размещён на GitHub, процесс PR/ревью ведётся там. Требуется
устранить расхождение управляемым образом, не нарушая Architecture Freeze.

## 2. Существующее архитектурное решение
ADR-006: единый конвейер на GitLab CI/CD (`.gitlab-ci.yml`), стадии build/test/package/deploy.

## 3. Предлагаемое решение
Сделать **GitHub Actions** основной CI-платформой, **сохранив** GitLab CI как зеркало.
Возможность `CAP-CI-001` реализуется двумя технологиями; обе обеспечивают одинаковые Quality
Gates. Оформляется новым **ADR-009**, дополняющим ADR-006 (см. таблицу вариантов там).

## 4. Impact Analysis
- **Модули:** без изменений исходного кода сервисов.
- **Сервисы:** не затронуты.
- **API:** не затронуты (совместимость сохранена).
- **База данных:** не затронута.
- **Frontend:** не затронут (сборка теперь и в Actions).
- **DevOps:** добавлены `.github/workflows/*`; `.gitlab-ci.yml` сохранён; версии — из каталога (TVMS).
- **Безопасность:** добавлены workflow'ы security/dependency-scan/SBOM (усиление, не ослабление).

## 5. Compatibility Check
- Обратная совместимость: сохранена (GitLab CI не удалён).
- Миграция данных: не требуется.
- Новая версия API: не требуется.
- Изменение клиентов: не требуется.
- Обновление документации: ADR-009, ADR Index, Technology Mapping, Toolchain Governance Standard.

## 6. Риски
- Рассинхрон двух CI-конфигураций (R-O3). Снижение: единый каталог версий, одинаковые стадии/Quality
  Gates, ревью изменений CI в обеих конфигурациях.

## 7. Migration Plan
1. Подготовка: определить набор workflow'ов, соответствующих стадиям ADR-006.
2. Миграция: добавить `.github/workflows/*`.
3. Тестирование: проверка синтаксиса workflow'ов и сухой прогон в CI.
4. Переключение: GitHub Actions — основной сигнал статуса PR.
5. Мониторинг: наблюдение за прохождением проверок.
6. Завершение: закрытие RFC (Verified).

## 8. Rollback Plan
- Критерий: систематические сбои Actions без обходного решения.
- Действия: отключить/удалить проблемные workflow'ы; вернуться к GitLab CI как основному.
- Восстановление: `.gitlab-ci.yml` остаётся рабочим; данные не затрагиваются.
- Проверка: зелёный конвейер GitLab CI.

## 9. Document Synchronization
Обновлены: ADR-009 (создан), ADR Index, RFC Index, Technology Mapping (`CAP-CI-001`),
Toolchain Governance Standard, каталог версий (`ci.githubActions`).

## 10. Definition of Done для RFC
- [x] ADR создан/обновлён (ADR-009)
- [x] Impact Analysis выполнен
- [x] Migration/Rollback планы готовы
- [x] Документация синхронизирована
- [x] Изменение реализовано и проверено (Verified)
