# Use Java 21 base image
FROM eclipse-temurin:21-jdk-alpine

# Set a working directory
WORKDIR /app

# Copy the project jar (after build)
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","/app.jar"]