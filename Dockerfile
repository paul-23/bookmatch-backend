FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
RUN ls /target
COPY target/*.war app.war
ENTRYPOINT ["java", "-jar", "/app.war"]
EXPOSE 8080
