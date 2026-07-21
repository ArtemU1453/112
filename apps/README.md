# apps/ — клиентские приложения

Клиентские приложения системы. Web-АРМ диспетчера — `../frontend/dispatcher` (React, ADR-004).
Мобильная экосистема (Stage 7) — единый кросс-платформенный стек (ADR-016), общий Mobile SDK
(`../docs/mobile/mobile-sdk-guide.md`) и дизайн-система (`../libraries/mobile-design-tokens`).

| Приложение | Статус | Роль |
|-----------|--------|------|
| frontend-dispatcher (`../frontend/dispatcher`) | Реализовано (Stage 0) | АРМ диспетчера (web) |
| mobile-crew | Архитектура/контракты (Stage 7) | Field Responder — подразделения (7.1) |
| mobile-commander | Архитектура/контракты (Stage 7) | Commander — начальник караула (7.2) |
| mobile-command | Архитектура/контракты (Stage 7) | Incident Command — руководитель ликвидации ЧС (7.3) |
| mobile-citizen | Планируется | Обращения граждан |

Все мобильные приложения используют единую платформу, единый SDK, единые API и политики безопасности
(запрет app-специфичных API и дублирования бизнес-логики; offline-first; секреты не в коде).
