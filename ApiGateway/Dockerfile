FROM openjdk:8-jdk-alpine
COPY target/ApiGateway-0.0.1-SNAPSHOT.jar ApiGateway.jar
COPY service_healthcheck.sh /service_healthcheck.sh
RUN chmod +x /service_healthcheck.sh
ENTRYPOINT ["/service_healthcheck.sh"]
#ENTRYPOINT ["java",  "-jar", "ApiGateway.jar"]
EXPOSE 8013