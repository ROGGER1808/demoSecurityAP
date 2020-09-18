FROM openjdk:8-jre-alpine
EXPOSE 8080
WORKDIR /app
COPY target/securityDemo-0.0.1-SNAPSHOT.jar .

ENTRYPOINT [ "java", "-jar", "securityDemo-0.0.1-SNAPSHOT.jar" ]