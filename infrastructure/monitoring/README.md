# Monitoring

Мониторинг, логирование и трассировка для всей системы.

## Компоненты

### Prometheus
Метрики и алерты

```
monitoring/prometheus/
├── prometheus.yml       # Конфигурация
├── alerts.rules        # Правила алертов
└── docker-compose.yml
```

### Grafana
Визуализация метрик

```
monitoring/grafana/
├── dashboards/
├── datasources/
└── provisioning/
```

### ELK Stack (Elasticsearch, Logstash, Kibana)
Централизованное логирование

```
monitoring/elk/
├── elasticsearch/
├── logstash/
├── kibana/
└── docker-compose.yml
```

### Jaeger
Распределённая трассировка

```
monitoring/jaeger/
├── jaeger-config.yml
└── docker-compose.yml
```

## Запуск локально

```bash
cd monitoring
docker-compose up -d
```

## Доступ

- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000
- Kibana: http://localhost:5601
- Jaeger UI: http://localhost:16686
