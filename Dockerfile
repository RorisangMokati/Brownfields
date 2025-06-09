FROM ubuntu

FROM maven:latest

WORKDIR /app

COPY pom.xml .
COPY src ./src

ADD target/robot_worlds-1.0-SNAPSHOT-jar-with-dependencies.jar /app/robot_worlds_server.jar

# Build the project
# RUN mvn clean package

WORKDIR /app
EXPOSE 5050
CMD ["java", "-jar", "robot_worlds_server.jar"]

VOLUME /data


