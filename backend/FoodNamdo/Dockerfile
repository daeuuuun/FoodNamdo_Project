FROM openjdk:17-jdk

ARG JAR_FILE=./build/libs

COPY ${JAR_FILE}/*.jar .

ENTRYPOINT ["java","-jar","FoodNamdo-0.0.1-SNAPSHOT.jar"]