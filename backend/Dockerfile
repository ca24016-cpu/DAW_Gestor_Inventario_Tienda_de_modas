# Etapa 1 (Build): Descargar dependencias y compilar el código fuente
FROM eclipse-temurin:21-jdk-alpine AS build

# Instalar Maven
RUN apk add --no-cache maven

WORKDIR /app

# Copiar pom.xml y el código fuente
COPY pom.xml .
COPY src ./src

# Compilar y empaquetar omitiendo pruebas
RUN mvn clean package -DskipTests

# Etapa 2 (Runtime): Imagen final ligera con el JRE
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiar el archivo .jar compilado desde la etapa anterior
COPY --from=build /app/target/inventario-1.0.0.jar app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
