1 - run : mvn clean package

2 - create Dockerfile :
# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file to the container
COPY target/bank-server-1.0.0.jar /app/bankserver.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/bankserver.jar"]

3 - docker build -t bankserver .
 
4 - docker run -d --name bankserver-container -p 8080:8080 bankserver
 