FROM openjdk:21-jdk
ARG JAR_FILE=target/keycloak-registration-service-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} /keycloak-registration-service/app.jar
ENTRYPOINT ["java","-jar","/keycloak-registration-service/app.jar"]