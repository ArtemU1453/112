# Integration Security Architecture — ЕАСУР

- **Status:** APPROVED · Stage 9 · ADR-023 · Подчиняется Security Architecture, Data Exchange
  Security Standard. Zero Trust.

## Zero Trust / Least Privilege
Доверие не предполагается по умолчанию. Каждый обмен аутентифицируется и авторизуется; права —
минимальны (scope на интерфейс/операцию/систему).

## Механизмы (9.6)
- **Взаимная аутентификация** — mTLS между платформой и внешними системами.
- **Авторизация** — OAuth2 client credentials (Keycloak, ADR-007); scope из реестра внешних систем.
- **Цифровая подпись сообщений** (JWS/detached), где применяется — целостность и неотказуемость.
- **Контроль целостности** — проверка подписи/контрольной суммы на приёме.
- **Защита от повторной отправки** — nonce + timestamp + идемпотентность (ключи).
- **Аудит доступа** — неизменяемый (Immutable Audit; Cross-System Audit Standard).
- **Управление сертификатами** — ведомственная PKI; ключи/секреты — Vault (ADR-011); ротация.

## Данные
Чувствительные данные передаются по защищённому каналу (mTLS/TLS); минимизация; суверенно
(self-hosted PKI, AGSDS). Обмен без аудита/контроля безопасности запрещён (запрет этапа).

## Связанные документы
Data Exchange Security / Cross-System Audit Standards · National Integration Architecture ·
Security Architecture · ADR-023/011/007.
