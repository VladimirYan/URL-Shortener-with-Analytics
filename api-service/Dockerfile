FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY . .
RUN mvn -B -pl api-service -am clean package -DskipTests
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/api-service/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]