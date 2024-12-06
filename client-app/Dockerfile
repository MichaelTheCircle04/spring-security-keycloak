FROM openjdk:21-jdk
ARG JAR_FILE=target/client-app-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} /client-app/app.jar
ENTRYPOINT ["java","-jar","/client-app/app.jar"]