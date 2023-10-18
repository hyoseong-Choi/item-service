FROM openjdk:17-ea-11-jdk-slim
COPY build/libs/item-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]