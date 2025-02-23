# Usa uma imagem do OpenJDK para rodar a aplicação
#FROM eclipse-temurin:17-jdk-alpine

# Define o diretório de trabalho
#WORKDIR /app

# Copia o arquivo JAR gerado pelo build do projeto (certifique-se de que está na raiz do projeto)
#COPY /target/rbt-app-0.0.1-SNAPSHOT.jar rbt-app-0.0.1-SNAPSHOT.jar

# Expõe a porta usada pelo Spring Boot
#EXPOSE 8081

# Comando para rodar a aplicação
#ENTRYPOINT ["java", "-jar", "rbt-app-0.0.1-SNAPSHOT.jar"]

FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM eclipse-temurin:17-alpine
COPY --from=build /usr/src/app/target/rbt-app-0.0.1-SNAPSHOT.jar /app/rbt-app-0.0.1-SNAPSHOT.jar
EXPOSE 8081
CMD ["java","-jar","/app/rbt-app-0.0.1-SNAPSHOT.jar"]
ENV TZ=America/Sao_Paulo