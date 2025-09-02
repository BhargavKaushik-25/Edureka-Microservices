# Use official OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy built JAR file
COPY target/inventory-ms-0.0.1-SNAPSHOT.jar

# Expose port (check your application.properties port for booking-ms)
EXPOSE 9192

# Command to run the app
ENTRYPOINT ["java","-jar","app.jar"]
