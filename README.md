# LoveU - Backend

LoveU es un backend hecho con Spring Boot para una aplicacion de citas tipo Tinder. El proyecto permite registrar usuarios, crear perfiles, guardar fotos, realizar swipes, generar matches, enviar notificaciones y manejar reportes.

El objetivo principal es demostrar el funcionamiento de una API REST conectada a una base de datos relacional, usando una estructura ordenada por capas.

---
## Convertidor de texto a YML

- https://theproductguy.in/tools/properties-to-yaml/?properties=spring.application.name%3Dreporte%0Aserver.port%3D8083%0A%0Aspring.datasource.url%3Djdbc%3Apostgresql%3A%2F%2Flocalhost%3A5432%2Freporte_db%0Aspring.datasource.username%3Droot%0Aspring.datasource.password%3D%0A%0Aspring.jpa.hibernate.ddl-auto%3Dvalidate%0Aspring.jpa.show-sql%3Dtrue%0Aspring.jpa.properties.hibernate.format_sql%3Dtrue%0A%0Aspring.flyway.enabled%3Dtrue%0Aspring.flyway.baseline-on-migrate%3Dtrue&mode=to-yaml

## Integrantes

- Integrante 1: Daniel Pinto
- Integrante 2: Diego SantiBañez
- Integrante 3: Paloma Moreno

---

## Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Maven
- Lombok
- Jakarta Validation

---

## Como Esta Estructurado

El proyecto esta organizado por capas para separar responsabilidades:


## Funcionamiento General

El flujo principal de la aplicacion es:

1. Se crea un usuario con sus datos personales.
2. Se crea un registro de Auth con email, password y rol.
3. El usuario crea un perfil visible dentro de la app.
4. El perfil puede agregar fotos.
5. Un perfil puede dar LIKE, DISLIKE o SKIP a otro perfil.
6. Si dos perfiles coinciden, se puede crear un match.
7. El sistema permite generar notificaciones y reportes.

La app no tiene frontend completo, por lo que se prueba usando Postman, Thunder Client o consultas SQL.

---

## Modulos Principales

- `Usuario`: guarda datos personales como nombre, apellido, fecha de nacimiento y telefono.
- `Auth`: guarda email, password y rol. La password entra al sistema, pero no se devuelve en el DTO de respuesta.
- `Perfil`: contiene la informacion visible en la app, como nombre, biografia, altura, ocupacion y comuna.
- `FotoPerfil`: permite agregar fotos, marcar una como principal y desactivar fotos.
- `Swipe`: registra decisiones entre perfiles. Sus valores son `LIKE` y `DISLIKE`.
- `Match`: representa la conexion entre dos perfiles.
- `Notificacion`: guarda avisos importantes para un perfil.
- `Reporte`: permite reportar perfiles. Sus estados son `EN_REVISION`, `RESUELTO` y `RECHAZADO`.

---

## Configuracion

El archivo principal de configuracion esta en:

```text
src/main/resources/application.properties
```

Ejemplo:

```properties
spring.application.name=loveu
server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5432/loveu_db
spring.datasource.username=postgres
spring.datasource.password=TU_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

Antes de ejecutar, debe existir una base de datos llamada:

```text
loveu_db
-- CREATE DATABASE loveu_db;
```

---

## Como Ejecutar

```bash
.\mvnw.cmd spring-boot:run
```
La API queda disponible en:

```text
http://localhost:8080
```

---
## Endpoints Importantes

```text
Usuarios:       POST /api/usuarios, GET /api/usuarios
Auth:           POST /api/auth, POST /api/auth/login
Perfiles:       POST /api/perfiles, GET /api/perfiles
Fotos:          POST /api/fotoperfil, GET /api/fotoperfil/perfil/{perfilId}
Swipes:         POST /api/swipes, GET /api/swipes
Matches:        POST /api/matches/verificar, GET /api/matches
Notificaciones: POST /api/notificaciones, GET /api/notificaciones/perfil/{perfilDestinatarioId}
Reportes:       POST /api/reportes, GET /api/reportes
```

Ejemplo simple para crear notificacion:

```json
{
  "perfilDestinatarioId": 1,
  "type": "MATCH",
  "message": "Tienes un nuevo match"
}
```

Ejemplo simple para crear reporte:

```json
{
  "perfilReportanteId": 1,
  "perfilReportadoId": 2,
  "razonReporte": "Perfil con informacion falsa"
}
```
---

## Datos Base Necesarios

Para crear perfiles se necesita tener al menos una region y una comuna en la base de datos.

```sql
INSERT INTO regiones (nombre_region, abreviacion)
VALUES ('Region Metropolitana', 'RM');

INSERT INTO comunas (nombre_comuna, region_id)
VALUES ('Santiago', 1);
```

---

## Consultas Utiles

```sql
SELECT * FROM usuarios ORDER BY id;
SELECT * FROM perfiles ORDER BY id;
SELECT * FROM foto_perfil ORDER BY id;
SELECT * FROM swipes ORDER BY id;
SELECT * FROM matches ORDER BY id;
SELECT * FROM notifications ORDER BY id;
SELECT * FROM reportes ORDER BY id;
```

---

## Observaciones

- El proyecto usa DTO para controlar que informacion se muestra al cliente.
- Algunos borrados se manejan como baja logica, cambiando el campo `activo`.
- El login actual es basico y no usa JWT ni Spring Security.
- Para una aplicacion real, las passwords deberian guardarse encriptadas.
- El proyecto esta pensado para demostrar estructura backend, relaciones entre entidades y persistencia con PostgreSQL.
