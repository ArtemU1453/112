# Knowledge Platform Architecture — ЕАСУР

- **Status:** APPROVED · Stage 13 · Подчиняется Architecture.md, ADR-028, Project Constitution,
  Architecture Baseline, EAGF. Справочный слой; бизнес-логика оперативных модулей не меняется.

## 1. Назначение
Централизованная платформа управления знаниями, нормативными документами, справочниками и правилами
поддержки решений (13.1–13.10). Единый источник (SSOT) нормативной информации для диспетчеров и
руководителей.

## 2. Принципы
API First · Contract First · **SSOT знаний** · только утверждённые версии · неизменяемая история ·
объяснимость правил (обязательное основание) · совещательный ИИ (AI Assistance Policy) · контролируемое
распространение · Technology Abstraction (`CAP-KNOW-001`, `CAP-SEARCH-001` self-hosted) · суверенность (AGSDS).

## 3. Логические компоненты
| Компонент | Функция | Подэтап | Стандарт |
|-----------|---------|---------|----------|
| Knowledge Repository | Единое хранилище единиц знаний | 13.1 | Knowledge Lifecycle |
| Regulatory Document Mgmt | Версии/статусы/утверждение/публикация/архив | 13.2 | Regulatory Document / Document Versioning |
| SOP Management | Жизненный цикл SOP, привязка к типам ЧС | 13.3 | SOP Lifecycle |
| Rules & Decision Catalog | Правила с основаниями и приоритетами | 13.4 | Rule Authoring |
| Reference Information | Классификаторы/словари/коды/значения | 13.5 | Reference Information |
| Knowledge Search | Полнотекст/атрибуты/семантика (адаптер ИИ) | 13.6 | Semantic Search Governance |
| Decision Support Layer | Рекомендации из SOP, объяснимые | 13.7 | Rule Authoring / AI Explainability |
| Knowledge Distribution | Уведомления/ознакомление/журнал | 13.8 | Knowledge Publication |
| Competency & Certification | Обучение/тесты/аттестация | 13.9 | Competency Management |
| Knowledge Analytics | Использование/пробелы/актуализация | 13.10 | Knowledge Analytics |

## 4. Позиционирование в системе
Платформа — **справочный слой знаний** поверх существующей архитектуры (микросервисы, ADR-002).
Оперативные модули (incident/dispatch/gis/…) **читают** утверждённые знания (SOP/правила/справочники)
для совещательных рекомендаций; их бизнес-логика не изменяется. Любое изменение процессов — RFC→ARB→ADR.

## 5. Данные и целостность
Собственная модель реестра знаний (документы/версии/статусы/связи/основания/журнал ознакомления).
История неизменяема (Knowledge Retention Standard). Идентификаторы — канонические (Stage 9).

## 6. Безопасность
RBAC (OIDC, ADR-007); аудит доступа и ознакомления; секреты в Vault; Zero Trust; шифрование при
передаче/хранении. Только чтение утверждённого; публикация — через согласование.

## 7. Связи
Regulatory Document / SOP Governance / Rules Catalog / Reference Data / Knowledge Search / Analytics
документы · стандарты Stage 13 · ADR-028 · AI (ADR-021) · НСИ (Stage 9) · Technology Mapping.
