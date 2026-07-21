# platform-commons — общие библиотеки Core Platform (Stage 4)

Реиспользуемый инфраструктурный код для всех сервисов ЕАСУР. **Не содержит бизнес-логики**
(Constitution ARTICLE 3; запрет прикладной логики Stage 4).

## Состав (`by.mchs.e112.platform.commons`)
| Пакет | Назначение | Стандарт |
|-------|-----------|----------|
| `error` | RFC 7807 ProblemDetail, ErrorCode, нарушения полей | Error Handling Framework |
| `exception` | Иерархия PlatformException (404/409/403/401/400) | Error Handling Framework |
| `dto` | PageRequest/PageResponse, Sort, ApiResult | API Guidelines |
| `validation` | ValidationResult, Validators | Validation Framework |
| `mapping` | Mapper | Common Libraries: Mapping |
| `time` | TimeProvider (UTC), TimeZones | Time Service |
| `localization` | MessageResolver, SupportedLocales | Localization Platform |
| `logging` | CorrelationContext (MDC), LogFields | Logging Standards |
| `security` | PlatformPrincipal, SecurityContext, Roles | Identity/Authorization |
| `id` | Ids (UUID/correlation) | Utilities |

## Использование
Подключается через Platform SDK (`platform-sdk`) либо напрямую как зависимость. Версии — BOM
(`libraries/build/bom`) и каталог версий (TVMS). Тесты — JUnit 5 + AssertJ.
