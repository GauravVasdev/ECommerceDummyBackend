FROM openjdk:17

WORKDIR /

ADD ./services/edge-service/edge-service-app/build/libs/edge-service-app.jar edge-service-app.jar

EXPOSE 8091

ENTRYPOINT ["java", "-jar", "edge-service-app.jar"]