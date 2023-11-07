FROM openjdk:8-jdk-alpine
COPY target/ConfigServer-0.0.1-SNAPSHOT.jar ConfigServer.jar
ENTRYPOINT ["java",  "-jar", "ConfigServer.jar"]
EXPOSE 8888