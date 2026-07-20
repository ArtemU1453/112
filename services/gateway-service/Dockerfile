FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml mvnw* ./
COPY src ./src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /workspace/target/gateway-service-0.1.0.jar ./gateway-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/gateway-service.jar"]
