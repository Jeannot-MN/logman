FROM openjdk:17.0.2-slim-buster
EXPOSE 8080
WORKDIR /usr/app
COPY target/logman-0.0.1-SNAPSHOT.jar /usr/app
CMD ["java", "-jar", "logman-0.0.1-SNAPSHOT.jar"]