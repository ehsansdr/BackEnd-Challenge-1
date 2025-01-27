# Use a base image with OpenJDK (since Spring Boot requires Java)
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot jar file into the container (adjust the path if necessary)
COPY target/WalletManager.jar /app/WalletManager.jar

# Expose the port that Spring Boot will run on (default is 8080)
EXPOSE 8080

# Command to run the Spring Boot app
CMD ["java", "-jar", "WalletManager.jar"]
