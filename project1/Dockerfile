# Use Java 21
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy everything into container
COPY . /app

# Give execute permission to Maven wrapper
RUN chmod +x mvnw

# Build the jar inside Docker
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","/app/target/project1-0.0.1-SNAPSHOT.jar"]