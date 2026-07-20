FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml mvnw* ./
COPY src ./src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /workspace/target/notification-service-0.1.0.jar ./notification-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/notification-service.jar"]
