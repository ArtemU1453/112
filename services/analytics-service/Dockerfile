FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -q dependency:go-offline
COPY src ./src
RUN mvn -q package -DskipTests

FROM eclipse-temurin:21-jre-alpine
RUN addgroup -S app && adduser -S app -G app
USER app
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
EXPOSE 8089
HEALTHCHECK --interval=15s --timeout=3s --start-period=60s \
  CMD wget -qO- http://localhost:8089/actuator/health/liveness || exit 1
ENTRYPOINT ["java", "-jar", "app.jar"]
