# 빌드 단계
FROM gradle:jdk17 as builder

WORKDIR /app

COPY . .

RUN gradle clean build --no-daemon

# 실행 단계
FROM openjdk:17-jdk-alpine

WORKDIR /app

# tzdata 패키지 설치 및 타임존 설정
RUN apk add --no-cache tzdata \
    && ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime \
    && echo "Asia/Seoul" > /etc/timezone

# 빌드된 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 어플리케이션 실행
CMD ["java", "-jar", "app.jar"]