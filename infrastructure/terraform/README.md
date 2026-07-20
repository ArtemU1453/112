# Terraform

Инфраструктура как код для облака (AWS, Azure, GCP).

## Структура

```
terraform/
├── modules/
│   ├── vpc/                # Сетевые ресурсы
│   ├── database/           # БД (RDS, CloudSQL и т.д.)
│   ├── cache/              # Redis, Elasticache
│   ├── kubernetes/         # EKS, AKS, GKE
│   ├── storage/            # S3, Blob, GCS
│   └── security/           # IAM, Security Groups
├── environments/
│   ├── dev/
│   │   ├── main.tf
│   │   ├── variables.tf
│   │   └── terraform.tfvars
│   ├── staging/
│   │   ├── main.tf
│   │   ├── variables.tf
│   │   └── terraform.tfvars
│   └── prod/
│       ├── main.tf
│       ├── variables.tf
│       └── terraform.tfvars
├── main.tf
├── variables.tf
├── outputs.tf
└── backend.tf
```

## Инициализация

```bash
cd terraform/environments/dev
terraform init
terraform plan
terraform apply
```

## Требования

- Terraform >= 1.0
- AWS CLI / Azure CLI / gcloud
- Configured credentials
