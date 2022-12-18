FROM openjdk:11

ARG JAR_FILE=./build/libs/auth-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

EXPOSE 6000

ENV TZ Asia/Seoul

ENTRYPOINT ["java", "-jar", "app.jar"]