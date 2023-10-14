FROM ubuntu:latest AS BUILD

RUN apt update
RUN apt install openjdk-17-jdk -y

COPY . .

RUN apt install maven -y
RUN mvn clean install

FROM openjdk:17-jdk-slim

EXPOSE 8080

RUN ls target

COPY --from=build /target/todolist-0.0.1-SNAPSHOT app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]