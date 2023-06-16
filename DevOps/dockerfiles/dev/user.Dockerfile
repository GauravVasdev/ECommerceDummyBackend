FROM openjdk:17

WORKDIR /

ADD ./services/user-service/user-service-app/build/libs/user-service-app.jar user-service-app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "user-service-app.jar"]