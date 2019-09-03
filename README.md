# TACS

## Requisitos para ejecución:
* Instalar [JDK](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) 1.8, seleccionando la versión correspondiente para el sistema operativo.
* [Descargar](https://maven.apache.org/download.cgi) e [instalar](https://maven.apache.org/install.html) apache maven versión 3.6.3 o superior
* Instalar [MySQL](https://dev.mysql.com/downloads/installer/)

## Levantar el proyecto:
* Levantar el servicio de MySQL con los parámetros por defecto
* Crear una base de datos, configurarla con:
    - Crear schema con nombre "tacs"
    - Agregar usuario al schema con username: tacs, password: tacs y los permisos necesarios
* Posicionarse en el root del proyecto
* Ejecutar el comando mvn spring-boot:run

## Descripción de las rutas:
### Usuario
* POST    /login - Inicio de sesión del usuario.
* POST    /logout - Salida de sesión del usuario.
* POST    /users - 
* GET     /users
* GET     /repositories/:id
* POST    /users/:id/favourites
* GET     /users/:id/favourites/:id
* PUT     /users/:id/favourites/:id
* DELETE  /users/:id/favourites/:id

### Admin
* GET	/users/:id
* GET /comparison/favourites?id1=1&id2=2
* GET	/repositories/:id
* GET	/repositories?since=01012019&to=01012020
