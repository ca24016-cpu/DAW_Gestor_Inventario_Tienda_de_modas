# Etapa 1 (Build): Instalar dependencias y compilar el proyecto
FROM node:22-alpine AS build

WORKDIR /app

# Copiar archivos de dependencias
COPY package*.json ./

# Instalar dependencias limpias
RUN npm ci

# Copiar el resto del código y compilar
COPY . .
RUN npm run build

# Etapa 2 (Servidor): Servir archivos estáticos con Nginx
FROM nginx:alpine

# Copiar el build de la etapa anterior
COPY --from=build /app/dist /usr/share/nginx/html

# Exponer el puerto 80
EXPOSE 80

# Comando para ejecutar Nginx
CMD ["nginx", "-g", "daemon off;"]
