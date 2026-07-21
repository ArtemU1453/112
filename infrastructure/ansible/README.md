# infrastructure/ansible — подготовка узлов и офлайн-операции (ADR-010, AGSDS)

Ansible-роли и плейбуки для подготовки инфраструктуры платформы в on-premise/air-gapped контуре.

## Плейбуки
- `site.yml` — полная подготовка (common → container-runtime → kubernetes-node → local-registry).
- `offline-update.yml` — импорт офлайн-пакета обновления с проверкой целостности и журналированием (AGSDS).

## Роли
- `common` — часовой пояс, NTP (локальные источники), sysctl, отключение swap.
- `container-runtime` — containerd + systemd cgroups + локальное зеркало реестра.
- `kubernetes-node` — kubelet/kubeadm/kubectl, фиксация версий (TVMS).
- `local-registry` — локальный OCI-реестр образов (изолированный контур).

## Использование
```bash
cd infrastructure/ansible
ansible-inventory --graph            # проверить инвентарь
ansible-playbook site.yml            # подготовить платформу
ansible-playbook offline-update.yml  # офлайн-обновление
```
Инвентарь и секреты — из защищённого источника, не из git (EGS, ADR-011). Версии — TVMS.
