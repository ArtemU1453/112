# Common Libraries Guide — ЕАСУР (platform-commons)

- **Status:** APPROVED · Stage 4 · Подчиняется Coding Standards, Shared Domain Kernel Standard.

Руководство по общим библиотекам. Полный перечень классов — `libraries/platform-commons/README.md`.

## Пакеты и назначение
| Пакет | Ключевые типы | Использование |
|-------|---------------|---------------|
| `error` | `ErrorCode`, `ProblemDetail`, `ProblemDetails`, `FieldViolation` | Единый формат ошибок (RFC 7807) |
| `exception` | `PlatformException` и наследники (404/409/403/401/400) | Доменно-нейтральные ошибки |
| `dto` | `PageRequest`, `PageResponse`, `Sort`, `ApiResult` | Пагинация/сортировка (API Guidelines) |
| `validation` | `ValidationResult`, `Validators` | Сбор всех нарушений, не «первая ошибка» |
| `mapping` | `Mapper` | Структурное преобразование типов |
| `time` | `TimeProvider`, `SystemTimeProvider`, `TimeZones` | Единое время (UTC), тестируемость |
| `localization` | `MessageResolver`, `SupportedLocales` | Локализация сообщений/ошибок |
| `logging` | `CorrelationContext`, `LogFields` | Корреляция в MDC (Logging Standards) |
| `security` | `PlatformPrincipal`, `SecurityContext`, `Roles` | Субъект и роли (RBAC) |
| `id` | `Ids` | UUID/correlationId |

## Правила использования
- Не дублировать инфраструктурный код — использовать библиотеки (ARTICLE 7).
- Библиотеки не содержат бизнес-логики (Constitution ARTICLE 3; запрет Stage 4).
- Изменения библиотек — с сохранением обратной совместимости (Dependency/Shared Domain Kernel
  Standards); несовместимые — мажорная версия + миграция.

## Тестирование
JUnit 5 + AssertJ (`PlatformCommonsTest`). Каждый переиспользуемый механизм покрыт тестами.
