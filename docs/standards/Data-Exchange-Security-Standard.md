# Data Exchange Security Standard — ЕАСУР

- **Status:** APPROVED · Stage 9 · ADR-023 · Согласован с Integration Security Architecture, Security
  Standards. Zero Trust.

## Правила (9.6)
- **Взаимная аутентификация** — mTLS; авторизация — OAuth2 client credentials, scope минимальных
  привилегий (из реестра внешних систем).
- **Целостность/неотказуемость** — цифровая подпись сообщений (JWS), где применяется; проверка на приёме.
- **Anti-replay** — nonce + timestamp + идемпотентность.
- **Управление сертификатами** — ведомственная PKI; ключи/секреты — Vault (ADR-011); ротация.
- **Аудит доступа** — неизменяемый (Cross-System Audit Standard); обмен без аудита/контроля
  безопасности запрещён.
- Минимизация/защита передаваемых данных (TLS/mTLS); суверенно (self-hosted PKI, AGSDS).

## Definition of Done
Определены взаимная аутентификация/авторизация/подпись/целостность/anti-replay/сертификаты/аудит.
