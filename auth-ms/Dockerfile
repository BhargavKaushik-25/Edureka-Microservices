# Use OpenJDK 17 base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven built jar
COPY target/auth-ms-1.0.0.jar app.jar

# Expose port (as per application.properties)
EXPOSE 8081

# Run the jar
ENTRYPOINT ["java","-jar","app.jar"]
