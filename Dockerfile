# Stage 1: Build the Java application
FROM maven:3.9.9-amazoncorretto-17-al2023 AS build-stage

# Set working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml ./
COPY src ./src

# Build the application
RUN mvn clean package

# Stage 2: Run the Java application
FROM openjdk:17-jdk-slim

# Copy the built JAR file from the build stage
COPY --from=build-stage /app/target/bank-server-1.0.0.jar /app/bankserver.jar

# Expose port 8080
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "/app/bankserver.jar"]



# # Use an official OpenJDK runtime as a parent image
# FROM openjdk:17-jdk-slim

# # Set the working directory inside the container
# WORKDIR /app

# # Copy the JAR file to the container
# COPY target/bank-server-1.0.0.jar /app/bankserver.jar

# # Expose the application port
# EXPOSE 8080

# # Command to run the application
# ENTRYPOINT ["java", "-jar", "/app/bankserver.jar"]
