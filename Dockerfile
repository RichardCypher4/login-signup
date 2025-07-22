# ---- Build Stage ----
FROM maven:3.9.5-eclipse-temurin-21 AS build

WORKDIR /app
COPY . .

# Build the application (skip tests if needed)
RUN mvn clean package -DskipTests

# ---- Run Stage ----
FROM eclipse-temurin:21-jre

WORKDIR /app
EXPOSE 8081

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Start the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]