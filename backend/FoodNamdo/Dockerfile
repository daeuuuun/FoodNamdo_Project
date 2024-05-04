FROM openjdk:17-jdk-alpine AS builder

COPY . /usr/src/app
WORKDIR /usr/src/app

RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM openjdk:17-jdk-alpine
COPY --from=builder /usr/src/app/build/libs/*.jar /home/server.jar
ENTRYPOINT ["java", "-jar", "/home/server.jar", "--spring.profiles.active=dev"]
