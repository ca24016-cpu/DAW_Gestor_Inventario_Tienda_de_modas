# DAW_Gestor_Inventario_Tienda_de_modas
Implementacion de un sistema de gestion de inventario (Producto de venta) orientado hacia empresas o emprendimientos en el rubro de la moda 

## Integrantes:

- Andrea Isabel Chávez Mejía CM24080
- Ana Cristina Martinez Salas - MS24088 
- Jose Israel Lemus Salguero LS24009
- Rolando Estuardo Salguero Borja SB21023
- Joel Isaac Chavez Arevalo CA24016

##  Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- [Java JDK 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)  
- [Maven](https://maven.apache.org/)  
- [PostgreSQL](https://www.postgresql.org/download/) y [pgAdmin](https://www.pgadmin.org/download/)  
- [Git](https://git-scm.com/downloads)  
- Navegador web para acceder a la API y documentación  

Opcional:
- IDE recomendado: Visual Studio Code, IntelliJ IDEA o Eclipse  


## Estructura del Repositorio
- **`backend/`** → Código Java con Entities, Repositories, Services y DTOs.  
- **`database/`** → Scripts SQL, schema de PostgreSQL y datos de prueba.  
- **`frontend/`** → Carpeta para la interfaz gráfica y recursos web.  
- **`media/`** → Diagramas y documentación (incluye el modelo ERD).  
- **`README.md`** → Documentación principal del proyecto.

## Modelo ERD - DB:
![ERD FashionTrack](./media/Fashiontrack_DB_ERD.png)


## Tecnologías Utilizadas
- **Lenguaje:** Java (Spring Boot, JPA/Hibernate)  
- **Base de Datos:** PostgreSQL + pgAdmin  
- **Documentación:** Swagger/OpenAPI   
- **Control de versiones:** GitHub

# 💻 Guía de Inicialización del Proyecto

El proyecto está dividido en dos módulos principales:

- **Frontend:** Interfaz de usuario
- **Backend:** Servidor y base de datos

---

# 🖥️ Inicializar el Frontend

La interfaz de usuario está construida con React y utiliza **pnpm** para una gestión de dependencias rápida y eficiente.

Actualmente, los datos presentados son simulados (*Mock Data*) para validar las operaciones CRUD y el diseño responsivo.

## 📋 Requisitos Previos

- Node.js
- pnpm

## ▶️ Pasos de Ejecución

### 1. Abrir la terminal

### 2. Navegar al directorio del frontend

```bash
cd frontend
```

### 3. Instalar dependencias

```bash
pnpm install
```

### 4. Iniciar el servidor de desarrollo

```bash
pnpm run dev
```

### 5. Abrir la aplicación en el navegador

Dirígete a la URL indicada en la terminal, normalmente:

```txt
http://localhost:5173/
```

---

# ⚙️ Inicializar el Backend

El backend está desarrollado en Spring Boot y expone los endpoints necesarios para la gestión del inventario.

## 📋 Requisitos Previos

- Java 17 o superior
- PostgreSQL configurado localmente

## ▶️ Pasos de Ejecución

### 1. Abrir una nueva terminal

### 2. Navegar al directorio del backend

```bash
cd backend/Inventario/inventario
```

### 3. Ejecutar la aplicación

#### Linux / macOS

```bash
./mvnw spring-boot:run
```

#### Windows

```cmd
mvnw.cmd spring-boot:run
```

---

# 📚 Documentación de la API

Una vez iniciado el backend, puedes consultar la documentación de la API en Swagger UI:

```txt
http://localhost:8083/swagger-ui/index.html
```

> El puerto puede variar dependiendo de la configuración definida en `application.properties`.

---

# 📌 Operaciones CRUD Simuladas

Para propósitos de validación de interfaz, el sistema permite simular operaciones CRUD sin depender de persistencia real en el backend.

## ✅ GET
Renderizado dinámico de productos en:

- Tarjetas *(Mobile)*
- Tablas *(Desktop)*

## ➕ POST
Inserción de nuevos productos mediante el formulario de **Agregar Producto**.

## ✏️ PUT
Edición de información de registros existentes.

## ❌ DELETE
Eliminación de productos previa confirmación mediante un modal de seguridad.


