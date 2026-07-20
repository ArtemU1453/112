# Эксплуатация системы 112/МЧС

## Порты сервисов

| Сервис               | Порт  | Health-эндпоинт                    |
|----------------------|-------|------------------------------------|
| gateway-service      | 8080  | `/actuator/health`                 |
| auth-service         | 8081  | `/actuator/health`                 |
| incident-service     | 8082  | `/actuator/health`                 |
| dispatch-service     | 8083  | `/actuator/health`                 |
| telephony-service    | 8084  | `/actuator/health`                 |
| gis-service          | 8085  | `/actuator/health`                 |
| audit-service        | 8086  | `/actuator/health`                 |
| notification-service | 8087  | `/actuator/health`                 |
| realtime-service     | 8088  | `/actuator/health`                 |
| ai-service           | 8090  | `/health`                          |
| frontend-dispatcher  | 3000/80 | `/`                              |

Инфраструктура: PostgreSQL 5432, Redis 6379, Kafka 9092/9094, Keycloak 8180/8080,
Prometheus 9090, Grafana 3001, Loki 3100.

## Локальный запуск (docker-compose)

```bash
docker compose up -d postgres redis kafka keycloak     # инфраструктура
docker compose up -d                                    # все сервисы
docker compose ps                                       # статус
docker compose logs -f incident-service                 # логи сервиса
```

После старта:

- АРМ диспетчера — http://localhost:3000
- Gateway/API — http://localhost:8080
- Keycloak admin — http://localhost:8180 (`admin`/`admin`)
- Grafana — http://localhost:3001
- Prometheus — http://localhost:9090

## Развёртывание в Kubernetes (Helm)

См. `helm/emergency-112/README.md`. Кратко:

```bash
helm upgrade --install emergency-112 helm/emergency-112 \
  --namespace emergency --create-namespace \
  -f helm/emergency-112/values-production.yaml \
  --set global.imageTag=stable \
  --set global.registry=$REGISTRY \
  --set secrets.dbPassword=$DB_PASSWORD \
  --set secrets.keycloakAdminPassword=$KC_ADMIN_PASSWORD
```

Проверка после установки:

```bash
kubectl -n emergency get pods,svc,ingress,hpa
kubectl -n emergency rollout status deploy/incident-service
kubectl -n emergency logs -f deploy/dispatch-service
```

## Учётные записи (заглушки Keycloak)

Realm `emergency-112` (`infrastructure/keycloak/realm-112.json`) содержит тестовых
пользователей. **Пароли — только для dev/staging, в проде обязательна замена.**

| Логин       | Роли                                             | Назначение                        |
|-------------|--------------------------------------------------|-----------------------------------|
| dispatcher1 | ROLE_DISPATCHER                                  | Рядовой диспетчер                 |
| senior1     | ROLE_DISPATCHER, ROLE_SENIOR_DISPATCHER          | Старший диспетчер смены           |
| sysadmin    | ROLE_ADMIN, ROLE_SENIOR_DISPATCHER, ROLE_DISPATCHER | Администрирование пользователей   |

Роли realm: `ROLE_DISPATCHER`, `ROLE_SENIOR_DISPATCHER`, `ROLE_ADMIN`, `ROLE_CREW`, `ROLE_ANALYST`.

## Мониторинг

- **Prometheus** собирает `/actuator/prometheus` со всех Java-сервисов и метрики ai-service.
  Targets проверяются на http://localhost:9090/targets.
- **Grafana** (http://localhost:3001) — дашборды поверх Prometheus и Loki.
- **Loki + Promtail** — централизованные логи; запросы LogQL в Grafana Explore.

Рекомендуемые сигналы для алертов:

- Доступность: `up == 0` для любого сервиса дольше 2 мин.
- Ошибки: рост `http_server_requests_seconds_count{status=~"5.."}`.
- Латентность: p99 `http_server_requests_seconds` по ключевым эндпоинтам.
- Kafka lag консьюмеров (incident/dispatch/ai/audit) — накопление необработанных событий.
- Использование пула соединений Hikari (`hikaricp_connections_active`).

## Резервное копирование и восстановление

### Скрипты

- `infrastructure/backup/backup.sh` — `pg_dump` (custom-формат) всех БД в каталог со штампом
  времени, удержание 14 суток.
- `infrastructure/backup/restore.sh` — восстановление из дампа.

```bash
PGHOST=postgres PGUSER=e112 PGPASSWORD=*** ./infrastructure/backup/backup.sh /backups
```

### В Kubernetes

При `backup.enabled=true` чарт создаёт `CronJob` (по умолчанию ежедневно в 02:00) и `PVC`
для дампов. Расписание и удержание — `backup.schedule`, `backup.retentionDays`.

```bash
kubectl -n emergency get cronjob
kubectl -n emergency create job --from=cronjob/emergency-112-backup manual-backup-1
```

### Восстановление

```bash
pg_restore --clean --if-exists --dbname="$DB" /backups/<STAMP>/<db>.dump
```

## Чек-лист развёртывания

1. Собраны и запушены образы всех 11 сервисов (тег = SHA коммита или `stable`).
2. Развёрнута инфраструктура: PostgreSQL+PostGIS, Kafka, Redis, Keycloak; заведён realm
   `emergency-112`, созданы БД (`infrastructure/postgres/init`).
3. Секреты (`secrets.dbPassword`, `secrets.keycloakAdminPassword`) переданы из защищённого
   хранилища, **не из git**.
4. `helm upgrade --install` выполнен; `kubectl rollout status` зелёный по всем Deployment.
5. Ingress резолвится: `dispatch.112.by` (АРМ) и `api.112.by` (`/api`, `/ws`).
6. Prometheus видит все targets; Grafana-дашборды отображают метрики.
7. Тестовый вход диспетчера в АРМ; создание происшествия; проверка автодиспетчеризации и
   обновлений реального времени (WebSocket).
8. CronJob бэкапа активен; выполнен пробный ручной backup и тест восстановления.

## Диагностика инцидентов

- **Сервис не стартует / CrashLoopBackOff:** проверить `DB_URL`, доступность PostgreSQL,
  соответствие схемы Liquibase (`ddl-auto: validate` падает при рассинхроне).
- **401/403 на API:** проверить `KEYCLOAK_ISSUER_URI`, срок жизни/валидность JWT, роли.
- **Нет обновлений в реальном времени:** проверить `realtime-service`, доступность Kafka,
  проксирование `/ws` на шлюзе/Ingress (таймауты, upgrade заголовки).
- **События не обрабатываются:** проверить Kafka consumer lag и доступность брокеров.
- **Автодиспетчеризация молчит:** событию `incident.created` нужны координаты и приоритет
  CRITICAL/HIGH; проверить наличие доступных подразделений нужного типа в `dispatch_db`.
