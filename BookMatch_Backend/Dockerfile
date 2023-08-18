FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.war /app/app.war

EXPOSE $PORT

CMD ["java", "-Dserver.port=$PORT", "-jar", "app.war"]
