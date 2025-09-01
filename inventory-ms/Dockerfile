# Use official OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy built JAR file
COPY target/booking-ms-1.0.0.jar app.jar

# Expose port (check your application.properties port for booking-ms)
EXPOSE 9192

# Command to run the app
ENTRYPOINT ["java","-jar","app.jar"]
