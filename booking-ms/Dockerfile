# Use official OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy built JAR file
COPY target/booking-ms-0.0.1-SNAPSHOT.jar app.jar

# Expose port (check your application.properties port for booking-ms)
EXPOSE 9192

# Command to run the app
ENTRYPOINT ["java","-jar","app.jar"]
