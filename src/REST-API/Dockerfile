FROM openjdk:23-jdk-slim as builder
WORKDIR /app
COPY src ./src
COPY build.gradle.kts .
COPY gradlew .
COPY gradlew.bat .
COPY settings.gradle.kts .
COPY .env .
COPY gradle/ ./gradle/
RUN ./gradlew build


FROM openjdk:23-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/REST-API-1.0.jar .

EXPOSE 8080

CMD ["java", "-jar", "REST-API-1.0.jar"]