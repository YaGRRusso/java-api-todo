# BUILD
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copie o arquivo POM e o arquivo de origem do projeto
COPY pom.xml .
COPY src ./src

# Compile o projeto
RUN mvn clean package

# PRODUÇÂO
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copie o arquivo JAR gerado no estágio de build para o estágio de produção
COPY --from=build /app/target/todolist-1.0.0.jar app.jar

# Exponha a porta do aplicativo (se necessário)
EXPOSE 8080

# Comando para iniciar a aplicação Spring Boot
CMD ["java", "-jar", "app.jar"]
