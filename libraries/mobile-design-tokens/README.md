# mobile-design-tokens — токены единой дизайн-системы (Stage 7)

Единый источник дизайн-токенов для **всех** мобильных приложений ЕАСУР (Mobile Design System):
цвета состояний (приоритеты/статусы), семантические цвета, темы (light/dark/high-contrast),
типографика, отступы, радиусы, размеры сенсорных целей (в т. ч. режим перчаток).

## Использование
Приложения (`apps/mobile-*`) импортируют `tokens.json` через Mobile SDK и **не хардкодят**
значения. Изменения токенов — с сохранением семантики; несовместимые — мажорная версия
(Mobile Lifecycle Management Standard).

## Соответствие
Mobile Design System, Mobile Accessibility Standard, UX-требования Stage 7.
