# Todo List App with Java Spring Boot

Este é um projeto de exemplo para criar uma aplicação de lista de tarefas (Todo List) com autenticação de usuários usando Java Spring Boot, Maven, H2 Database e Docker.

## Pré-requisitos

Antes de começar, verifique se você tem as seguintes ferramentas instaladas:

- Java Development Kit (JDK)
- Apache Maven
- Docker

## Configuração

### Banco de Dados

Este projeto utiliza o H2Database e salva a informações em memória à cada build.

### Desenvolvimento
Para executar o projeto em modo de desenvolvimento você deve utilizar o Maven:
```shell
mvn spring-boot:run
```

### Build e Execução
Para construir o projeto, você pode usar o Maven:

```shell
mvn clean install
```

Para executar o aplicativo Spring Boot:

```shell
java -jar target/todo-list-app.jar
```

### Docker
Este projeto inclui um Dockerfile para facilitar a criação de uma imagem Docker. Para criar a imagem, execute:

```shell
docker build -t todo-list-app .
```

E para executar a aplicação em um contêiner Docker:

```shell
docker run -p 8080:8080 todo-list-app
```

API Endpoints
Aqui estão os principais endpoints da API:

- `POST` /users -> Criar um novo usuário
- `GET` /users -> Listar usuários
- `POST` /users -> Criar um novo todo (protegido)
- `GET` /todos -> Listar os todos do usuário autenticado (protegido)
- `PUT` /todos -> Alterar um todo (protegido)

*Certifique-se de autenticar-se para acessar os endpoints protegidos.*

## Licença
Este projeto é licenciado sob a MIT License - veja o arquivo LICENSE.md para mais detalhes.