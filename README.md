# TACS

## Requisitos para ejecución:
* Instalar [JDK](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) 1.8, seleccionando la versión correspondiente para el sistema operativo.
* [Descargar](https://maven.apache.org/download.cgi) e [instalar](https://maven.apache.org/install.html) apache maven versión 3.6.3 o superior
* Instalar [MySQL](https://dev.mysql.com/downloads/installer/)

## Levantar el proyecto:
* Levantar el servicio de MySQL con los parámetros por defecto
* Crear una base de datos, configurarla con:
    - Nombre del schema: tacs
    - Agregar usuario al schema con username: tacs, password: tacs y los permisos necesarios (Select, insert, update, delete)
* Posicionarse en el root del proyecto desde el command prompt
* Ejecutar el comando mvn spring-boot:run

## Descripción de las rutas:
### Usuario
* POST    /login - Inicio de sesión del usuario.
* POST    /logout - Salida de sesión del usuario.
* POST    /users - Agregar un usuario nuevo
* GET     /repositories/:id - Obtener un repositorio particular
* POST    /users/:id/favourites - Agregar un repositorio a la lista de favoritos
* GET     /users/:id/favourites - Obtener la lista de repositorios favoritos de un usuario particular
* GET     /users/:id/favourites/:id - Obtener un repositorio favorito de un usuario particular 
* DELETE  /users/:id/favourites/:id - Quitar a un repositorio particular de la lista de favoritos de un usuario

### Admin
* GET   /users - Obtener una lista de todos los usuarios 
* GET	/users/:id - Obtener un usuario particular
* GET   /comparison/favourites?id1=1&id2=2 - Obtener la comparación entre dos listas de favoritos de 2 usuarios diferentes
* GET	/repositories - Obtener los repositorios en el sistema
* GET	/repositories?since=01012019&to=01012020 - Obtener los repositorios que se encuentren entre un determinado tiempo
