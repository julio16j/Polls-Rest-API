FROM maven:3.8.3-openjdk-17 as build
WORKDIR /workspace/app
COPY src ./src
COPY pom.xml ./
RUN mvn clean install

FROM eclipse-temurin:17-jdk-alpine
COPY --from=build workspace/app/target/poll-rest-api.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]