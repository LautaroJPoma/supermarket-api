# Supermarket API
Este es un proyecto de práctica de una API RESTful para la gestión de un supermercado, donde puedes realizar operaciones CRUD para productos, categorías, clientes y órdenes de compra. La API está desarrollada en Java con Spring Boot y permite interactuar con una base de datos MySQL.

# Características
- Gestión de productos: CRUD para productos en el supermercado.
- Gestión de categorías: CRUD para categorías de productos.
- Gestión de clientes: CRUD para información de los clientes.
- Gestión de órdenes de compra: Crear y administrar órdenes de compra asociadas a clientes.
- Documentación Swagger: Acceso a la documentación interactiva de la API.

# Requisitos
- Java 8 o superior
- MySQL 
- Maven (para compilar el proyecto)

# Instalación
1. Clona el repositorio en tu máquina local: git clone https://github.com/tu_usuario/supermarket-api.git
   cd supermarket-api
  

2. Configura las credenciales de la base de datos en el archivo `src/main/resources/application.properties`:
   - Asegúrate de que los valores de `spring.datasource.url`, `spring.datasource.username`, y `spring.datasource.password` estén correctamente configurados para tu entorno local.

3. Construye el proyecto usando Maven: mvn clean install


4. Ejecuta la aplicación: mvn spring-boot:run
 

5. La API estará disponible en `http://localhost:8080`.

## Documentación
Puedes acceder a la documentación interactiva de la API utilizando Swagger en:
http://localhost:8080/swagger-ui.html

Gracias por tomarse la molestia de leer este readme y tambien por utilizar esta API. Espero que les sea util o que les de ideas para otros proyectos.
