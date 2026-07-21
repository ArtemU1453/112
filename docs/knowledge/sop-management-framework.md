# SOP Management Framework — ЕАСУР

- **Status:** APPROVED · Stage 13 · Рамочная модель управления SOP. Реализует SOP Lifecycle Standard и
  SOP Governance Guide; согласован с ADR-028, домен реагирования (Stage 5–6).

## 1. Назначение
Единая рамка управления стандартными операционными процедурами на всём жизненном цикле: от разработки
до вывода, с привязкой к типам происшествий и контролем применения.

## 2. Компоненты рамки
- **Authoring:** создание SOP по шаблону (структура, основания, контрольные точки).
- **Review & Approval:** согласование заинтересованных и утверждение уполномоченным органом.
- **Publication & Distribution:** публикация и обязательное ознакомление (Knowledge Publication/Distribution).
- **Binding:** привязка SOP к типам происшествий (IncidentType) и приоритетам.
- **Execution Support:** совещательные рекомендации и контрольные точки в поддержке решений (13.7).
- **Analytics & Improvement:** оценка эффективности SOP (Knowledge Analytics), актуализация.
- **Lifecycle Control:** версии, статусы, депрекация, архив (история неизменяема).

## 3. Модель жизненного цикла
`Draft → Review → Approved → Published → Maintained → Deprecated → Archived`. Применяются только
Published-версии; изменения — через утверждённый процесс; удаление истории запрещено.

## 4. Контроль применения
Обязательные контрольные точки отслеживаются; отклонения фиксируются (аудит, обезличенная аналитика).
Система **не изменяет рабочий процесс автоматически** — окончательные действия подтверждает человек;
изменение самих процессов — через RFC→ARB→ADR.

## 5. Роли
Авторы SOP, согласующие, утверждающие, публикаторы, владельцы доменов реагирования, аналитики, ARB.

## 6. Связи
SOP Lifecycle Standard · SOP Governance Guide · Rules Catalog Specification · Decision Support Layer ·
Knowledge Publication/Distribution · Incident Classification/Command (Stage 5–6).
