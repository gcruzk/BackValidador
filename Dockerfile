# Etapa 1: Build com Maven
<<<<<<< HEAD
FROM maven:3.2-eclipse-temurin-17 AS build
=======
FROM maven:3.9-eclipse-temurin-17 AS build
>>>>>>> f67019e9d04634ca5a7f8017c0374882fbdac52f
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Runtime com JDK leve
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
