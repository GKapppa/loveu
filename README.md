# LoveU

LoveU es una aplicación estilo match entre usuarios, inspirada en el funcionamiento de Tinder. El sistema permitirá crear usuarios, perfiles, intereses, preferencias, swipes, matches, mensajes y reportes.

## Integrantes

- Daniel Pinto
- Diego Santibanez

## Tecnologías

- Java 21
- Spring Boot
- Maven
- PostgreSQL
- Spring Data JPA
- Validation
- Lombok
- OpenFeign

## Configuracion del proyecto (JAVA).

1. Debe tener instalado GIT / Java JDK 21 / VS Code o algun IDLE similiar / PostgreSQL + pgAdmin

2. Comprobar version de JAVA:
```Bash
java --version
javac --version
```

3. Clonar el Repositorio
- cd (Direccion de la carpeta en la cual quieras guaradr el proyecto).

- git clone https://github.com/GKapppa/loveu.git

- Luego entrar a la carpeta: cd loveu

- Ejecutar git status para ver si funciona

4. Paso (OPCIONAL) solo en caso de querer entrar a develop.

- git checkout develop
- git pull origin develop

5. Creamos labase de datos local pgADMIN o SQL SHELL 
```sql
CREATE DATABASE loveu_db;