# Analytics Architecture — ЕАСУР

- **Status:** APPROVED · Stage 8 · Подчиняется Architecture.md, Core Platform Architecture, EAGF,
  ADR-019/020/021. Единое аналитическое пространство.

## 1. Назначение
Единая аналитическая платформа: оперативная аналитика, ситуационная осведомлённость, поддержка
принятия решений, прогнозирование, контроль эффективности, статистика, отчётность, дашборды по
уровням управления. **ИИ — только инструмент анализа/рекомендаций**, не основание для автоматических
управленческих решений (запрет этапа).

## 2. Границы и переиспользование
Реализуется единым `analytics-service` (`services/analytics-service`) — **без дублирующих
аналитических платформ** (запрет этапа). Источники данных: события Kafka (ADR-008) и снапшоты
оперативных БД → **аналитическое хранилище (DWH, ADR-019)**. Оперативный контур (реагирование) не
нагружается аналитикой. Наблюдаемость (Grafana/Prometheus/Loki, Stage 3) переиспользуется для
real-time дашбордов (ADR-020).

## 3. Карта подэтапов 8.1–8.10

| Подэтап | Возможность | Компонент | Стандарт/ADR |
|---------|-------------|-----------|--------------|
| 8.1 Operational Analytics | Анализ вызовов/инцидентов/подразделений/реагирования/времени/нагрузки/очередей/маршрутов | analytics-service + DWH | Analytics Governance, Metrics Catalog |
| 8.2 Executive Dashboards | Панели по уровням управления (real-time/history/drill-down/экспорт) | BI (ADR-020) | Executive Dashboard, Dashboard Design |
| 8.3 KPI Platform | Конфигурируемые KPI (ответ/обработка/выезд/прибытие/ликвидация/нагрузка/нормативы) | analytics-service | KPI Governance, Metrics Catalog |
| 8.4 Decision Intelligence | Тенденции/аномалии/прогноз/рекомендации/перегрузка/зоны риска (с объяснением) | AI-аналитика (ADR-021) | Decision Intelligence, AI Explainability |
| 8.5 Reporting | Шаблоны/регламентные/пользовательские/расписание/версии/подпись/экспорт | analytics-service | Reporting Standard |
| 8.6 Data Warehouse | История/агрегаты/витрины/каталог/качество | DWH (ADR-019) | DWH Architecture, Data Quality/Lineage |
| 8.7 Forecasting | Прогноз вызовов/нагрузки/сил/сезонность/сценарии (с неопределённостью) | AI-аналитика | Forecasting, Forecast Validation |
| 8.8 National Situation Center | Карта страны/обстановка/ЧС/силы/KPI/критические события | BI + карта (Stage 6) | Situation Center Architecture |
| 8.9 Data Quality | Дубликаты/полнота/непротиворечивость/актуальность/происхождение | analytics-service | Data Quality, Data Lineage, Master Data Governance |
| 8.10 AI Analytics | Классификация/кластеризация/закономерности/отклонения/сводки/объяснимость | AI-аналитика (адаптеры) | AI Explainability, Statistical Methodology |

## 4. Потоки данных
```
Оперативные сервисы → Kafka (события) + снапшоты БД → ETL/ELT → DWH (ADR-019)
  → витрины/агрегаты/KPI → BI/дашборды (ADR-020) + отчёты + API дашбордов
  → аналитический ИИ (ADR-021, адаптеры) → рекомендации/прогнозы (с объяснением/неопределённостью)
  → человек (диспетчер/руководитель) принимает решение
```

## 5. Инварианты (обязательные проверки этапа)
- Все **KPI рассчитываются автоматически** (конфигурируемо, KPI Governance Standard).
- **Происхождение каждого показателя прослеживается** (Data Lineage Standard).
- **Отчёты воспроизводимы** (фиксированные срезы/версии; Reporting Standard).
- **Прогнозы имеют оценку достоверности** (неопределённость; Forecasting/Forecast Validation).
- **Каждый вывод ИИ сопровождается объяснением** и перечнем данных (AI Explainability Standard).
- Данные защищены (BI Security Standard, Security Architecture).
- Исторические данные неизменяемы; изменения — только с полным аудитом.

## 6. Связанные документы
BI Architecture · Data Warehouse Architecture · National Situation Center Architecture ·
16 стандартов Stage 8 · контракты `contracts/` · ADR-019/020/021.
