# Projeto Spring Boot

Este é um projeto Spring Boot utilizando Java 17, Lombok, Flyway para migrações de banco de dados e Docker para containerização.

## Requisitos

- Java 17
- Docker
- Maven

## Configuração do Banco de Dados

O projeto utiliza PostgreSQL como banco de dados.

A configuração do banco de dados é feita no arquivo docker-compose`src/main/resources/application.properties`. Exemplo:
Docker-compose configurado
```properties
# Banco de Dados
spring.datasource.url=jdbc:postgresql://localhost:5432/fullstack
spring.datasource.username=postgres
spring.datasource.password=admin

# Configuração do Flyway
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

Migrações do Banco de Dados
As tabelas do banco já estão na pasta src/main/resources/db/migration:
V1__Cria_tabela_RbtUser.sql
V2__Cria_tabela_RbtUserMen.sql
O Flyway aplicará essas migrações automaticamente ao iniciar a aplicação.

Executando a Aplicação Backend
Para subir a aplicação backend, execute os seguintes comandos no terminal:
- mvn clean package
- docker build -t rbt-app .
- docker run -d -p 8081:8081 --name meu-app-container rbt-app
Observação: Se alterar a porta do backend, será necessário atualizar as requisições no frontend.
Ao fazer o login, utilize como o primeiro login usuário admin senha admin




