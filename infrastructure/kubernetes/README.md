# Kubernetes

Kubernetes конфигурации для развёртывания сервисов.

## Структура

```
kubernetes/
├── base/                    # Базовые конфигурации
│   ├── auth-service.yaml
│   ├── incident-service.yaml
│   ├── dispatch-service.yaml
│   ├── notification-service.yaml
│   ├── realtime-service.yaml
│   └── ...
├── overlays/
│   ├── dev/                # Development
│   │   └── kustomization.yaml
│   ├── staging/            # Staging
│   │   └── kustomization.yaml
│   └── prod/               # Production
│       └── kustomization.yaml
├── monitoring/             # Prometheus, Grafana
├── ingress/                # Ingress конфигурации
└── helm/                   # Helm чарты (опционально)
```

## Развёртывание

```bash
# Development
kubectl apply -k kubernetes/overlays/dev

# Staging
kubectl apply -k kubernetes/overlays/staging

# Production
kubectl apply -k kubernetes/overlays/prod
```

## Инструменты

- kubectl
- kustomize
- Helm (опционально)
- ArgoCD (для GitOps)
