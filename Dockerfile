FROM openjdk:21-jdk
ARG JAR_FILE=target/resource-server-app-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} /resource-server-app/app.jar
ENTRYPOINT ["java","-jar","/resource-server-app/app.jar"]