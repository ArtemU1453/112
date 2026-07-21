# Runbook — Операции с кластером

- **Подчиняется:** Platform Operations Framework, Operational Procedures.

## Проверка состояния
```bash
kubectl get nodes -o wide
kubectl get pods -A --field-selector=status.phase!=Running
kubectl -n emergency-112 get deploy,hpa,pdb
kubectl top nodes ; kubectl top pods -n emergency-112
```

## Симптомы и действия

| Симптом | Диагностика | Действия | Эскалация |
|---------|-------------|----------|-----------|
| Узел NotReady | `kubectl describe node <n>`; kubelet/containerd | Cordon+drain, проверка узла (Ansible), возврат в строй | System Administrator |
| Pod Pending | `kubectl describe pod`; ресурсы/квоты | Проверить ResourceQuota/узлы; масштабировать | SRE |
| Pod CrashLoopBackOff | Логи, `describe`, конфиг/БД | См. Runbook сервиса; проверить конфиг/секреты | Владелец сервиса |
| Нехватка ресурсов | `kubectl top`, квоты | Capacity Management; масштабирование | SRE |

## Масштабирование
```bash
kubectl -n emergency-112 scale deploy/<svc> --replicas=<N>   # временно; постоянно — через HPA/Helm
```

## Слив и обслуживание узла
```bash
kubectl cordon <node> ; kubectl drain <node> --ignore-daemonsets --delete-emptydir-data
# обслуживание (Ansible) ; затем:
kubectl uncordon <node>
```
