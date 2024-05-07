FROM gradle:jdk17 as builder

WORKDIR /app

COPY . .

RUN gradle clean build --no-daemon

FROM openjdk:17-jdk-alpine

WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 어플리케이션 실행
CMD ["java", "-jar", "app.jar"]