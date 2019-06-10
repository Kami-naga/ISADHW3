FROM openjdk:8-jdk-alpine

MAINTAINER hyqhjhlh

ADD target/*.jar gateway.jar

EXPOSE 8100

ENTRYPOINT ["java","-jar","/gateway.jar"]