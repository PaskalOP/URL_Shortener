FROM openjdk:17

WORKDIR /app
COPY build/libs/URL_Shortener-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]