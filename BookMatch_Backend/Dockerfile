FROM eclipse-temurin:17-jdk-alpine

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo .war desde el contexto de construcción al directorio de trabajo
COPY target/*.war /app/app.war

# Exponer el puerto definido por Render
EXPOSE $PORT

# Comando de inicio
CMD ["java", "-Dserver.port=$PORT", "-jar", "app.war"]
