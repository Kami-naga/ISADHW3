FROM openjdk:8-jdk-alpine

MAINTAINER hyqhjhlh

ADD target/*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java","-jar","/app.jar"]