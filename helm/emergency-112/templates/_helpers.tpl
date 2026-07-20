{{/* Полное имя релиза */}}
{{- define "e112.fullname" -}}
{{- printf "%s" .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/* Стандартные метки */}}
{{- define "e112.labels" -}}
app.kubernetes.io/part-of: emergency-112
app.kubernetes.io/managed-by: {{ .Release.Service }}
helm.sh/chart: {{ .Chart.Name }}-{{ .Chart.Version }}
{{- end -}}

{{/* Полный образ сервиса */}}
{{- define "e112.image" -}}
{{- printf "%s/%s:%s" .root.Values.global.registry .svc.image .root.Values.global.imageTag -}}
{{- end -}}
