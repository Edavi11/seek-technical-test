# Multi-stage build para optimizar el tamaño de la imagen
FROM eclipse-temurin:17-jdk AS build

# Instalar Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Copiar el código fuente
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Descargar dependencias y compilar
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre

# Crear usuario no-root para seguridad
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Instalar dependencias del sistema
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Copiar el JAR compilado
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Cambiar propiedad del archivo
RUN chown appuser:appuser app.jar

# Cambiar al usuario no-root
USER appuser

# Exponer puerto
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]