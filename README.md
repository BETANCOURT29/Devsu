# Prueba Técnica Devsu

Este proyecto implementa dos microservicios en Java con Spring Boot, que gestionan entidades de clientes/personas y cuentas/movimientos, cumpliendo con todos los requerimientos solicitados en la prueba técnica.

## 🧱 Estructura del Proyecto

El proyecto se organiza en los siguientes microservicios:

* **`persona-cliente-service`**: Responsable de la gestión de la información de personas y su rol como clientes dentro del sistema. Incluye la creación, lectura, actualización y eliminación (CRUD) de clientes.
* **`cuenta-movimiento-service`**: Encargado de la gestión de cuentas bancarias (creación, lectura y actualización - CRU) y el registro de movimientos asociados a estas cuentas (creación, lectura, actualización y eliminación - CRUD). También implementa la lógica para generar reportes de estado de cuenta.

La arquitectura del sistema incorpora los siguientes elementos clave:

* **Comunicación Asíncrona**: La comunicación entre los microservicios se realiza de forma asíncrona utilizando **RabbitMQ**, lo que permite desacoplar los servicios y mejorar la resiliencia del sistema.
* **Persistencia de Datos**: Se utiliza **MySQL** como base de datos para la persistencia de la información en ambos microservicios.
* **Contenerización**: El despliegue de la aplicación se facilita mediante **Docker** y **Docker Compose**, asegurando la portabilidad y consistencia del entorno.
* **Calidad del Código**: Se han implementado **validaciones de datos**, **manejo de excepciones** centralizado y **pruebas unitarias** y de **integración** para garantizar la calidad y estabilidad de las funcionalidades.

## 🚀 Tecnologías Utilizadas

El proyecto se ha desarrollado utilizando las siguientes tecnologías y herramientas:

* **Lenguaje de Programación**: Java 17
* **Framework**: Spring Boot 3.4.5
* **Persistencia de Datos**: Spring Data JPA
* **Desarrollo Web**: Spring Web
* **Mensajería Asíncrona**: RabbitMQ
* **Base de Datos**: MySQL 8
* **Automatización de Construcción**: Gradle
* **Contenerización**: Docker & Docker Compose
* **Pruebas de APIs**: Postman
* **Pruebas Unitarias**: JUnit 5

## ▶️ Instrucciones para Ejecutar el Proyecto

Sigue estos pasos para ejecutar el proyecto en tu entorno local utilizando Docker Compose:

1.  **Clonar el Repositorio:**
    ```bash
    git clone [https://github.com/BETANCOURT29/Devsu.git](https://github.com/BETANCOURT29/Devsu.git)
    cd Devsu
    ```

2.  **Ejecutar con Docker Compose:**
    ```bash
    docker-compose up --build
    ```
    Este comando construirá las imágenes de Docker (si es necesario) y levantará los siguientes servicios:
    * MySQL
    * RabbitMQ (con interfaz de usuario accesible en [http://localhost:15672](http://localhost:15672))
    * `persona-cliente-service` accesible en [http://localhost:8080](http://localhost:8080)
    * `cuenta-movimiento-service` accesible en [http://localhost:8081](http://localhost:8081)

## 📂 Endpoints de la API

A continuación, se listan los principales endpoints expuestos por los microservicios:

* **`persona-cliente-service` (http://localhost:8080):**
    * `/clientes`: Permite realizar operaciones CRUD completas sobre la entidad de clientes.

* **`cuenta-movimiento-service` (http://localhost:8081):**
    * `/cuentas`: Permite realizar operaciones CRU (Crear, Leer, Actualizar) sobre las cuentas bancarias.
    * `/movimientos`: Permite realizar operaciones CRUD sobre los movimientos bancarios.
    * `/movimientos/reportes`: Endpoint para generar reportes de estado de cuenta.
    * `/movimientos/reportes`: (Método GET con parámetros) Permite obtener el estado de cuenta de un cliente específico dentro de un rango de fechas determinado. Los parámetros esperados son `clienteId`, `fechaInicio` (YYYY-MM-DD) y `fechaFin` (YYYY-MM-DD).

## 🧪 Pruebas

Se han implementado diferentes tipos de pruebas para asegurar la calidad del software:

* **Pruebas Unitarias**: Se han desarrollado pruebas unitarias utilizando JUnit 5 para verificar la lógica individual del componente y servicio de persona-cliente-sevice.
* **Pruebas de Integración**: Se ha implementado pruebas de integración, en el microservicio de `cuenta-movimiento-service`, con el fin de asegurar la correcta interacción entre los diferentes componentes, incluyendo la comunicación con la base de datos y el broker de mensajería.
* **Colección de Pruebas Postman**: Se incluye una colección de Postman (`DEVSU.postman_collection.json`) en la raíz del proyecto. Puedes importar esta colección a Postman para probar fácilmente los diferentes endpoints de la API.

## 🗄️ Scripts de Base de Datos

Los scripts SQL para la creación de las bases de datos y sus esquemas se encuentran en las siguientes ubicaciones:

* `persona-cliente-service/src/main/resources/persona_db.sql`
* `cuenta-movimiento-service/src/main/resources/cuenta_db.sql`

Opcionalmente, la ejecución automática de estos scripts al iniciar las aplicaciones puede configurarse añadiendo la siguiente sección al archivo `application.yml` de cada microservicio:

```yaml
spring:
  sql:
    init:
      mode: always
      schema-locations: classpath:persona_db.sql.
```

## 📦 Compilación Manual

Si prefieres compilar los microservicios individualmente sin utilizar Docker, puedes hacerlo con Gradle:
```bash
    ./gradlew clean build -x test
```

## 🧾 Notas Adicionales
Es importante asegurarse de que RabbitMQ esté en ejecución y accesible antes de iniciar los microservicios, ya que la comunicación asíncrona depende de este servicio. Docker Compose gestiona este orden de inicio cuando se utiliza. Si se ejecutan los microservicios manualmente, asegúrate de que RabbitMQ esté corriendo por separado.
Las variables de entorno necesarias para la configuración de las bases de datos, RabbitMQ y otros parámetros ya están definidas en el archivo docker-compose.yml, facilitando la configuración del entorno al usar Docker. Si ejecutas los microservicios manualmente, deberás configurar estas variables de entorno directamente en tu sistema o en la configuración de cada aplicación (por ejemplo, en sus archivos application.yml).
