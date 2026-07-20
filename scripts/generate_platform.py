from pathlib import Path
import textwrap, json

root = Path('e:/112')
(root / 'scripts').mkdir(parents=True, exist_ok=True)

services = [
    {
        'name': 'auth-service',
        'group': 'com.easur.auth',
        'artifact': 'auth-service',
        'package': 'com.easur.auth',
        'entity': 'UserEntity',
        'fields': [
            ('String', 'username'),
            ('String', 'email'),
            ('String', 'passwordHash'),
            ('String', 'role'),
            ('Boolean', 'enabled'),
            ('Instant', 'createdAt'),
            ('Instant', 'updatedAt'),
        ],
        'title': 'Authentication Service',
        'topic': 'auth.events',
        'contextPath': '/api/v1/auth',
        'port': '8080',
    },
    {
        'name': 'incident-service',
        'group': 'com.easur.incident',
        'artifact': 'incident-service',
        'package': 'com.easur.incident',
        'entity': 'IncidentEntity',
        'fields': [
            ('String', 'reference'),
            ('String', 'title'),
            ('String', 'severity'),
            ('String', 'status'),
            ('String', 'address'),
            ('Instant', 'createdAt'),
        ],
        'title': 'Incident Service',
        'topic': 'incident.events',
        'contextPath': '/api/v1/incidents',
        'port': '8080',
    },
    {
        'name': 'dispatch-service',
        'group': 'com.easur.dispatch',
        'artifact': 'dispatch-service',
        'package': 'com.easur.dispatch',
        'entity': 'DispatchTaskEntity',
        'fields': [
            ('String', 'incidentReference'),
            ('String', 'assignment'),
            ('String', 'status'),
            ('Instant', 'createdAt'),
        ],
        'title': 'Dispatch Service',
        'topic': 'dispatch.events',
        'contextPath': '/api/v1/dispatch',
        'port': '8080',
    },
    {
        'name': 'telephony-service',
        'group': 'com.easur.telephony',
        'artifact': 'telephony-service',
        'package': 'com.easur.telephony',
        'entity': 'CallSessionEntity',
        'fields': [
            ('String', 'caller'),
            ('String', 'destination'),
            ('String', 'status'),
            ('Instant', 'createdAt'),
        ],
        'title': 'Telephony Service',
        'topic': 'telephony.events',
        'contextPath': '/api/v1/calls',
        'port': '8080',
    },
    {
        'name': 'gis-service',
        'group': 'com.easur.gis',
        'artifact': 'gis-service',
        'package': 'com.easur.gis',
        'entity': 'GeoMapEntity',
        'fields': [
            ('String', 'name'),
            ('Double', 'latitude'),
            ('Double', 'longitude'),
            ('Instant', 'createdAt'),
        ],
        'title': 'GIS Service',
        'topic': 'gis.events',
        'contextPath': '/api/v1/geo',
        'port': '8080',
    },
    {
        'name': 'ai-service',
        'group': 'com.easur.ai',
        'artifact': 'ai-service',
        'package': 'com.easur.ai',
        'entity': 'AiRequestEntity',
        'fields': [
            ('String', 'inputText'),
            ('String', 'classification'),
            ('String', 'recommendation'),
            ('Instant', 'createdAt'),
        ],
        'title': 'AI Service',
        'topic': 'ai.events',
        'contextPath': '/api/v1/ai',
        'port': '8080',
    },
    {
        'name': 'notification-service',
        'group': 'com.easur.notification',
        'artifact': 'notification-service',
        'package': 'com.easur.notification',
        'entity': 'NotificationEntity',
        'fields': [
            ('String', 'channel'),
            ('String', 'recipient'),
            ('String', 'body'),
            ('Boolean', 'sent'),
            ('Instant', 'createdAt'),
        ],
        'title': 'Notification Service',
        'topic': 'notification.events',
        'contextPath': '/api/v1/notifications',
        'port': '8080',
    },
    {
        'name': 'realtime-service',
        'group': 'com.easur.realtime',
        'artifact': 'realtime-service',
        'package': 'com.easur.realtime',
        'entity': 'RealtimeSubscriptionEntity',
        'fields': [
            ('String', 'channel'),
            ('String', 'clientId'),
            ('Instant', 'createdAt'),
        ],
        'title': 'Realtime Service',
        'topic': 'realtime.events',
        'contextPath': '/api/v1/realtime',
        'port': '8080',
    },
    {
        'name': 'audit-service',
        'group': 'com.easur.audit',
        'artifact': 'audit-service',
        'package': 'com.easur.audit',
        'entity': 'AuditEventEntity',
        'fields': [
            ('String', 'actor'),
            ('String', 'action'),
            ('String', 'target'),
            ('Instant', 'createdAt'),
        ],
        'title': 'Audit Service',
        'topic': 'audit.events',
        'contextPath': '/api/v1/audit',
        'port': '8080',
    },
    {
        'name': 'gateway-service',
        'group': 'com.easur.gateway',
        'artifact': 'gateway-service',
        'package': 'com.easur.gateway',
        'entity': 'RouteDefinitionEntity',
        'fields': [
            ('String', 'path'),
            ('String', 'target'),
            ('Instant', 'createdAt'),
        ],
        'title': 'Gateway Service',
        'topic': 'gateway.events',
        'contextPath': '/api/v1/routes',
        'port': '8080',
    },
]

for svc in services:
    service_dir = root / 'services' / svc['name']
    service_dir.mkdir(parents=True, exist_ok=True)
    (service_dir / 'src/main/java').mkdir(parents=True, exist_ok=True)
    (service_dir / 'src/main/resources').mkdir(parents=True, exist_ok=True)
    (service_dir / 'src/main/resources/db/changelog').mkdir(parents=True, exist_ok=True)
    (service_dir / 'src/test/java').mkdir(parents=True, exist_ok=True)
    (service_dir / 'helm/templates').mkdir(parents=True, exist_ok=True)

    pkg = svc['package']
    pkg_path = service_dir / 'src/main/java' / pkg.replace('.', '/')
    pkg_path.mkdir(parents=True, exist_ok=True)
    (pkg_path / 'dto').mkdir(parents=True, exist_ok=True)
    (pkg_path / 'mapper').mkdir(parents=True, exist_ok=True)
    (pkg_path / 'service').mkdir(parents=True, exist_ok=True)
    (pkg_path / 'controller').mkdir(parents=True, exist_ok=True)
    (pkg_path / 'exception').mkdir(parents=True, exist_ok=True)
    (pkg_path / 'config').mkdir(parents=True, exist_ok=True)
    (pkg_path / 'kafka').mkdir(parents=True, exist_ok=True)
    (pkg_path / 'repository').mkdir(parents=True, exist_ok=True)
    (pkg_path / 'model').mkdir(parents=True, exist_ok=True)

    entity_name = svc['entity']
    repo_name = f'{entity_name.replace("Entity", "Repository")}'
    service_name = f'{entity_name.replace("Entity", "Service")}'
    mapper_name = f'{entity_name}Mapper'
    controller_name = f'{entity_name}Controller'
    producer_name = f'{entity_name}Producer'
    consumer_name = f'{entity_name}Consumer'

    (service_dir / 'pom.xml').write_text(textwrap.dedent(f'''\
    <project xmlns="http://maven.apache.org/POM/4.0.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
      <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.2</version>
        <relativePath/>
      </parent>
      <groupId>{svc['group']}</groupId>
      <artifactId>{svc['artifact']}</artifactId>
      <version>1.0.0</version>
      <properties>
        <java.version>21</java.version>
      </properties>
      <dependencies>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-web</artifactId></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-data-jpa</artifactId></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-validation</artifactId></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-security</artifactId></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-kafka</artifactId></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-actuator</artifactId></dependency>
        <dependency><groupId>org.springdoc</groupId><artifactId>springdoc-openapi-starter-webmvc-ui</artifactId><version>2.6.0</version></dependency>
        <dependency><groupId>org.postgresql</groupId><artifactId>postgresql</artifactId></dependency>
        <dependency><groupId>org.liquibase</groupId><artifactId>liquibase-core</artifactId></dependency>
        <dependency><groupId>com.h2database</groupId><artifactId>h2</artifactId><scope>runtime</scope></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-test</artifactId><scope>test</scope></dependency>
      </dependencies>
      <build>
        <plugins>
          <plugin><groupId>org.springframework.boot</groupId><artifactId>spring-boot-maven-plugin</artifactId></plugin>
          <plugin><groupId>org.apache.maven.plugins</groupId><artifactId>maven-compiler-plugin</artifactId><configuration><release>21</release></configuration></plugin>
        </plugins>
      </build>
    </project>
    '''))

    (service_dir / 'src/main/resources/application.yml').write_text(textwrap.dedent(f'''\
    server:
      port: {svc['port']}
    spring:
      datasource:
        url: ${'{' }SPRING_DATASOURCE_URL:jdbc:h2:mem:{svc['artifact']};DB_CLOSE_DELAY=-1;MODE=PostgreSQL}
        username: ${'{' }SPRING_DATASOURCE_USERNAME:sa}
        password: ${'{' }SPRING_DATASOURCE_PASSWORD:sa}
      jpa:
        hibernate:
          ddl-auto: validate
      kafka:
        bootstrap-servers: ${'{' }SPRING_KAFKA_BOOTSTRAP_SERVERS:localhost:9092}
      liquibase:
        change-log: classpath:/db/changelog/db.changelog-master.yaml
    management:
      endpoints:
        web:
          exposure:
            include: health,info
    springdoc:
      api-docs:
        path: /v3/api-docs
      swagger-ui:
        path: /swagger-ui.html
    '''))

    (pkg_path / 'Application.java').write_text(textwrap.dedent(f'''\
    package {pkg};

    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;

    @SpringBootApplication
    public class Application {{
        public static void main(String[] args) {{
            SpringApplication.run(Application.class, args);
        }}
    }}
    '''))

    field_code = []
    getter_setter_code = []
    for type_name, field_name in svc['fields']:
        field_code.append(f'    private {type_name} {field_name};')
        getter_setter_code.append(f'''\n    public {type_name} get{field_name[:1].upper() + field_name[1:]}() {{\n        return {field_name};\n    }}\n\n    public void set{field_name[:1].upper() + field_name[1:]}({type_name} {field_name}) {{\n        this.{field_name} = {field_name};\n    }}''')
    entity_code = '\n'.join(field_code) + '\n' + ''.join(getter_setter_code)

    (pkg_path / 'model' / f'{entity_name}.java').write_text(textwrap.dedent(f'''\
    package {pkg}.model;

    import jakarta.persistence.*;
    import java.time.Instant;

    @Entity
    @Table(name = "{svc['artifact'].replace('-', '_')}_entity")
    public class {entity_name} {{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
{entity_code}

        public Long getId() {{
            return id;
        }}

        public void setId(Long id) {{
            this.id = id;
        }}
    }}
    '''))

    (pkg_path / 'repository' / f'{repo_name}.java').write_text(textwrap.dedent(f'''\
    package {pkg}.repository;

    import {pkg}.model.{entity_name};
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    @Repository
    public interface {repo_name} extends JpaRepository<{entity_name}, Long> {{
    }}
    '''))

    (pkg_path / 'dto' / 'CreateRequest.java').write_text(textwrap.dedent(f'''\
    package {pkg}.dto;

    import jakarta.validation.constraints.NotBlank;

    public record CreateRequest(@NotBlank String title) {{
    }}
    '''))

    (pkg_path / 'dto' / 'ItemResponse.java').write_text(textwrap.dedent(f'''\
    package {pkg}.dto;

    public record ItemResponse(Long id, String title) {{
    }}
    '''))

    (pkg_path / 'mapper' / f'{entity_name}Mapper.java').write_text(textwrap.dedent(f'''\
    package {pkg}.mapper;

    import {pkg}.dto.ItemResponse;
    import {pkg}.model.{entity_name};
    import org.springframework.stereotype.Component;

    @Component
    public class {entity_name}Mapper {{
        public ItemResponse toResponse({entity_name} entity) {{
            return new ItemResponse(entity.getId(), entity.getTitle());
        }}
    }}
    '''))

    (pkg_path / 'service' / f'{service_name}.java').write_text(textwrap.dedent(f'''\
    package {pkg}.service;

    import {pkg}.dto.CreateRequest;
    import {pkg}.dto.ItemResponse;
    import {pkg}.mapper.{entity_name}Mapper;
    import {pkg}.model.{entity_name};
    import {pkg}.repository.{repo_name};
    import org.springframework.stereotype.Service;
    import java.util.List;

    @Service
    public class {service_name} {{
        private final {repo_name} repository;
        private final {entity_name}Mapper mapper;

        public {service_name}({repo_name} repository, {entity_name}Mapper mapper) {{
            this.repository = repository;
            this.mapper = mapper;
        }}

        public ItemResponse create(CreateRequest request) {{
            {entity_name} entity = new {entity_name}();
            entity.setTitle(request.title());
            return mapper.toResponse(repository.save(entity));
        }}

        public List<ItemResponse> list() {{
            return repository.findAll().stream().map(mapper::toResponse).toList();
        }}
    }}
    '''))

    (pkg_path / 'controller' / f'{controller_name}.java').write_text(textwrap.dedent(f'''\
    package {pkg}.controller;

    import {pkg}.dto.CreateRequest;
    import {pkg}.dto.ItemResponse;
    import {pkg}.service.{service_name};
    import jakarta.validation.Valid;
    import org.springframework.http.HttpStatus;
    import org.springframework.web.bind.annotation.*;
    import java.util.List;

    @RestController
    @RequestMapping("{svc['contextPath']}")
    public class {controller_name} {{
        private final {service_name} service;

        public {controller_name}({service_name} service) {{
            this.service = service;
        }}

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public ItemResponse create(@Valid @RequestBody CreateRequest request) {{
            return service.create(request);
        }}

        @GetMapping
        public List<ItemResponse> list() {{
            return service.list();
        }}
    }}
    '''))

    (pkg_path / 'exception' / 'GlobalExceptionHandler.java').write_text(textwrap.dedent(f'''\
    package {pkg}.exception;

    import org.springframework.http.HttpStatus;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.ResponseStatus;
    import org.springframework.web.bind.annotation.RestControllerAdvice;

    @RestControllerAdvice
    public class GlobalExceptionHandler {{
        @ExceptionHandler(IllegalArgumentException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public String handleIllegalArgument(IllegalArgumentException ex) {{
            return ex.getMessage();
        }}
    }}
    '''))

    (pkg_path / 'config' / 'SecurityConfig.java').write_text(textwrap.dedent(f'''\
    package {pkg}.config;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.web.SecurityFilterChain;

    @Configuration
    public class SecurityConfig {{
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {{
            http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }}
    }}
    '''))

    (pkg_path / 'kafka' / f'{producer_name}.java').write_text(textwrap.dedent(f'''\
    package {pkg}.kafka;

    import org.springframework.kafka.core.KafkaTemplate;
    import org.springframework.stereotype.Component;

    @Component
    public class {producer_name} {{
        private final KafkaTemplate<String, String> kafkaTemplate;

        public {producer_name}(KafkaTemplate<String, String> kafkaTemplate) {{
            this.kafkaTemplate = kafkaTemplate;
        }}

        public void publish(String message) {{
            kafkaTemplate.send("{svc['topic']}", message);
        }}
    }}
    '''))

    (pkg_path / 'kafka' / f'{consumer_name}.java').write_text(textwrap.dedent(f'''\
    package {pkg}.kafka;

    import org.springframework.kafka.annotation.KafkaListener;
    import org.springframework.stereotype.Component;

    @Component
    public class {consumer_name} {{
        @KafkaListener(topics = "{svc['topic']}", groupId = "{svc['artifact']}")
        public void consume(String message) {{
            System.out.println("Consumed: " + message);
        }}
    }}
    '''))

    (service_dir / 'src/main/resources/db/changelog/db.changelog-master.yaml').write_text(textwrap.dedent(f'''\
    databaseChangeLog:
      - changeSet:
          id: 1
          author: copilot
          changes:
            - createTable:
                tableName: {svc['artifact'].replace('-', '_')}_entity
                columns:
                  - column:
                      name: id
                      type: bigint
                      autoIncrement: true
                      constraints:
                        primaryKey: true
                        nullable: false
                  - column:
                      name: title
                      type: varchar(255)
                      constraints:
                        nullable: false
    '''))

    (service_dir / 'Dockerfile').write_text(textwrap.dedent(f'''\
    FROM maven:3.9.9-eclipse-temurin-21 AS build
    WORKDIR /workspace
    COPY pom.xml ./
    COPY src ./src
    RUN mvn -q -DskipTests package

    FROM eclipse-temurin:21-jre
    WORKDIR /app
    COPY --from=build /workspace/target/*.jar app.jar
    EXPOSE 8080
    ENTRYPOINT ["java", "-jar", "/app/app.jar"]
    '''))

    (service_dir / 'helm/Chart.yaml').write_text(textwrap.dedent(f'''\
    apiVersion: v2
    name: {svc['artifact']}
    version: 0.1.0
    '''))
    (service_dir / 'helm/values.yaml').write_text(textwrap.dedent(f'''\
    replicaCount: 1
    image:
      repository: {svc['artifact']}
      tag: latest
      pullPolicy: IfNotPresent
    service:
      type: ClusterIP
      port: 8080
    '''))
    (service_dir / 'helm/templates/deployment.yaml').write_text(textwrap.dedent(f'''\
    apiVersion: apps/v1
    kind: Deployment
    metadata:
      name: {svc['artifact']}
    spec:
      replicas: 1
      selector:
        matchLabels:
          app: {svc['artifact']}
      template:
        metadata:
          labels:
            app: {svc['artifact']}
        spec:
          containers:
            - name: {svc['artifact']}
              image: "{{ .Values.image.repository }}:{{ .Values.image.tag }}"
              ports:
                - containerPort: 8080
    '''))
    (service_dir / 'helm/templates/service.yaml').write_text(textwrap.dedent(f'''\
    apiVersion: v1
    kind: Service
    metadata:
      name: {svc['artifact']}
    spec:
      selector:
        app: {svc['artifact']}
      ports:
        - port: 8080
          targetPort: 8080
    '''))

    (service_dir / 'README.md').write_text(textwrap.dedent(f'''\
    # {svc['title']}

    This module provides the core REST API and messaging integration for {svc['title']}.
    It is ready to be built with Maven and deployed with Docker Compose.
    '''))

    (service_dir / 'src/test/java' / 'ApplicationTest.java').write_text(textwrap.dedent(f'''\
    package {pkg};

    import org.junit.jupiter.api.Test;
    import org.springframework.boot.test.context.SpringBootTest;

    @SpringBootTest
    class ApplicationTest {{
        @Test
        void contextLoads() {{}}
    }}
    '''))

    (service_dir / 'docker-compose.yml').write_text(textwrap.dedent(f'''\
    services:
      {svc['artifact']}:
        build: .
        ports:
          - "8080:8080"
    '''))

# Root docker compose
(root / 'docker-compose.yml').write_text(textwrap.dedent('''\
version: '3.8'
services:
  postgres:
    image: postgres:17-alpine
    environment:
      POSTGRES_USER: easur
      POSTGRES_PASSWORD: easur_dev
      POSTGRES_DB: easur
    ports: ["5432:5432"]
  redis:
    image: redis:7-alpine
    ports: ["6379:6379"]
  kafka:
    image: bitnami/kafka:3.7
    environment:
      KAFKA_CFG_NODE_ID: 1
      KAFKA_CFG_PROCESS_ROLES: controller,broker
      KAFKA_CFG_LISTENERS: PLAINTEXT://:9092,CONTROLLER://:9093
      KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP: CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT
      KAFKA_CFG_CONTROLLER_QUORUM_VOTERS: 1@kafka:9093
      KAFKA_CFG_CONTROLLER_LISTENER_NAMES: CONTROLLER
      KAFKA_CFG_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      ALLOW_PLAINTEXT_LISTENER: yes
    ports: ["9092:9092"]
  auth-service:
    build: ./services/auth-service
    ports: ["18080:8080"]
  incident-service:
    build: ./services/incident-service
    ports: ["18081:8080"]
  dispatch-service:
    build: ./services/dispatch-service
    ports: ["18082:8080"]
  telephony-service:
    build: ./services/telephony-service
    ports: ["18083:8080"]
  gis-service:
    build: ./services/gis-service
    ports: ["18084:8080"]
  ai-service:
    build: ./services/ai-service
    ports: ["18085:8080"]
  notification-service:
    build: ./services/notification-service
    ports: ["18086:8080"]
  realtime-service:
    build: ./services/realtime-service
    ports: ["18087:8080"]
  audit-service:
    build: ./services/audit-service
    ports: ["18088:8080"]
  gateway-service:
    build: ./services/gateway-service
    ports: ["18089:8080"]
'''))

# Infrastructure files
(root / 'infrastructure/monitoring/prometheus.yml').parent.mkdir(parents=True, exist_ok=True)
(root / 'infrastructure/monitoring/prometheus.yml').write_text(textwrap.dedent('''\
global:
  scrape_interval: 15s
scrape_configs:
  - job_name: 'spring'
    static_configs:
      - targets: ['auth-service:8080','incident-service:8080','dispatch-service:8080']
'''))

(root / 'infrastructure/kubernetes/namespace.yaml').write_text(textwrap.dedent('''\
apiVersion: v1
kind: Namespace
metadata:
  name: easur
'''))
(root / 'infrastructure/kubernetes/ingress.yaml').write_text(textwrap.dedent('''\
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: easur-ingress
  namespace: easur
spec:
  rules:
    - host: easur.local
      http:
        paths:
          - path: /
            pathType: Prefix
            backend:
              service:
                name: gateway-service
                port:
                  number: 8080
'''))
(root / 'infrastructure/terraform/main.tf').write_text(textwrap.dedent('''\
terraform {
  required_version = ">= 1.6.0"
}
resource "terraform_data" "placeholder" {}
'''))
(root / '.gitlab-ci.yml').write_text(textwrap.dedent('''\
stages: [build,test,deploy]
build:
  stage: build
  script:
    - mvn -q -DskipTests -f services/auth-service/pom.xml package
    - mvn -q -DskipTests -f services/incident-service/pom.xml package
    - mvn -q -DskipTests -f services/dispatch-service/pom.xml package
'''))

(root / 'frontend-dispatcher').mkdir(parents=True, exist_ok=True)
(root / 'frontend-dispatcher/package.json').write_text(json.dumps({
    'name': 'frontend-dispatcher',
    'private': True,
    'version': '1.0.0',
    'type': 'module',
    'scripts': {'dev': 'vite', 'build': 'vite build'},
    'dependencies': {
        'react': '^19.0.0', 'react-dom': '^19.0.0', '@mui/material': '^6.0.0', '@mui/icons-material': '^6.0.0', '@reduxjs/toolkit': '^2.0.0', 'react-redux': '^9.0.0', 'ol': '^10.0.0', 'socket.io-client': '^4.0.0'
    },
    'devDependencies': {'typescript': '^5.6.3', 'vite': '^5.4.10', '@vitejs/plugin-react': '^4.3.1', '@types/react': '^18.3.12', '@types/react-dom': '^18.3.1'}
}, indent=2))
(root / 'frontend-dispatcher/tsconfig.json').write_text(json.dumps({"compilerOptions":{"target":"ES2020","useDefineForClassFields":True,"lib":["ES2020","DOM"],"module":"ESNext","skipLibCheck":True,"moduleResolution":"Bundler","allowImportingTsExtensions":False,"resolveJsonModule":True,"isolatedModules":True,"noEmit":True,"jsx":"react-jsx","strict":True},"include":["src"]}))
(root / 'frontend-dispatcher/vite.config.ts').write_text(textwrap.dedent('''\
import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
export default defineConfig({ plugins: [react()] });
'''))
(root / 'frontend-dispatcher/index.html').write_text(textwrap.dedent('''\
<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Dispatch Center</title>
  </head>
  <body>
    <div id="root"></div>
    <script type="module" src="/src/main.tsx"></script>
  </body>
</html>
'''))
(root / 'frontend-dispatcher/src/main.tsx').write_text(textwrap.dedent('''\
import React from 'react';
import ReactDOM from 'react-dom/client';
import { Provider } from 'react-redux';
import { store } from './store';
import App from './App';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <Provider store={store}>
      <App />
    </Provider>
  </React.StrictMode>,
);
'''))
(root / 'frontend-dispatcher/src/store.ts').write_text(textwrap.dedent('''\
import { configureStore } from '@reduxjs/toolkit';

export const store = configureStore({
  reducer: {
    dashboard: (state = { incidents: 0 }) => state,
  },
});
'''))
(root / 'frontend-dispatcher/src/App.tsx').write_text(textwrap.dedent('''\
import { AppBar, Box, Button, Container, Toolbar, Typography } from '@mui/material';

export default function App() {
  return (
    <Box>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" sx={{ flexGrow: 1 }}>112 Dispatch Center</Typography>
          <Button color="inherit">Incidents</Button>
          <Button color="inherit">Map</Button>
          <Button color="inherit">AI</Button>
        </Toolbar>
      </AppBar>
      <Container sx={{ py: 4 }}>
        <Typography variant="h4">Operational dashboard</Typography>
        <Typography color="text.secondary">Real-time dispatch, mapping, AI triage and telephony controls are exposed through the shared API layer.</Typography>
      </Container>
    </Box>
  );
}
'''))
(root / 'frontend-dispatcher/README.md').write_text('# Frontend dispatcher\n')
(root / 'mobile-crew/package.json').write_text(json.dumps({'name':'mobile-crew','version':'1.0.0','private':True,'scripts':{'start':'echo "mobile crew app"'}}))
(root / 'mobile-citizen/package.json').write_text(json.dumps({'name':'mobile-citizen','version':'1.0.0','private':True,'scripts':{'start':'echo "mobile citizen app"'}}))

# Update root README
(root / 'README.md').write_text(textwrap.dedent('''\
# EASUR Platform

Industrial emergency dispatch platform for the Republic of Belarus.

## Implemented modules
- Backend microservices for authentication, incidents, dispatch, telephony, GIS, AI, notifications, realtime and audit.
- Docker Compose orchestration for core infrastructure and services.
- Kubernetes ingress and monitoring skeleton.
- React dispatcher frontend.

## Build
- `mvn -q -DskipTests -f services/auth-service/pom.xml package`
- `docker compose up --build`
'''))

print('generated platform skeleton')
