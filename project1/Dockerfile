# Use Java 21
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy project files
COPY . /app

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build the Spring Boot jar inside Docker
RUN ./mvnw clean package -DskipTests

# Expose default Spring Boot port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","/app/target/project1-0.0.1-SNAPSHOT.jar"]