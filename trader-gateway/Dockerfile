FROM openjdk:8-jdk-alpine

MAINTAINER hyqhjhlh

ADD target/*.jar app.jar

EXPOSE 8790

ENTRYPOINT ["java","-Xms2048m","-Xmx2048m","-XX:NewSize=1024m","-XX:MaxNewSize=1024m","-jar","/app.jar"]