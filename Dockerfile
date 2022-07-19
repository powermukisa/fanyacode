FROM adoptopenjdk/openjdk11:alpine-jre
#ADD target/fanyacode-0.0.1-SNAPSHOT.jar app.jar
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

#FROM adoptopenjdk/openjdk11:alpine-jre
#ADD target/spring-postgres-docker-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java","-jar","app.jar"]