[![Build Status](https://travis-ci.org/LucasBlanco/TACS.svg?branch=master)](https://travis-ci.org/LucasBlanco/TACS)  [![Coverage Status](https://coveralls.io/repos/github/LucasBlanco/TACS/badge.svg)](https://coveralls.io/github/LucasBlanco/TACS)

# TACS

## Documentación
* [Swagger](https://app.swaggerhub.com/apis/tacs2019/TACS/1.0.0)
* [Travis](https://travis-ci.org/LucasBlanco/TACS)
* [Coveralls](https://coveralls.io/github/LucasBlanco/TACS)

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
