# Use an official Java runtime as a base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file from the target directory to the container's /app directory
COPY target/WalletManager-0.0.1-SNAPSHOT.jar /app/WalletManager.jar

# Expose the port the app will run on (8080)
EXPOSE 8080

# Define the entrypoint to run the application
ENTRYPOINT ["java", "-jar", "/app/WalletManager.jar"]
