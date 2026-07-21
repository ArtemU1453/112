# platform-sdk — внутренний SDK Core Platform (Stage 4)

Единая точка подключения общих возможностей платформы для **каждого** сервиса ЕАСУР
(Internal SDK Governance Standard). Агрегирует `platform-commons` и `platform-abstractions`.

## Предоставляемые модули (`PlatformModule`)
Security · Logging · Configuration · Audit · Error Handling · Monitoring · Tracing.

## Использование
Сервис подключает `platform-sdk` как зависимость (версии — BOM `libraries/build/bom`, каталог
версий TVMS) и получает единообразные: контекст безопасности, время (UTC), локализацию, формат
ошибок (RFC 7807), корреляцию логов, порты интеграций/уведомлений/хранилища/поиска/ИИ/документов.

## Требование (Service Maturity Model)
Каждый новый сервис обязан использовать SDK и единые библиотеки — не дублировать инфраструктурный
код (Shared Domain Kernel Standard, Constitution ARTICLE 7). Прикладная логика в SDK/библиотеках
запрещена (Stage 4).
