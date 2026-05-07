# LoveU - Backend

LoveU es un proyecto backend desarrollado con Spring Boot orientado a una aplicación de tipo match entre perfiles.  
El sistema permite modelar usuarios, autenticación, perfiles, preferencias de búsqueda, fotos, swipes, matches, mensajes, reportes y notificaciones.

Este proyecto forma parte de una actividad académica enfocada en el desarrollo y operación de microservicios, persistencia con base de datos relacional y buenas prácticas de arquitectura backend.

---

## Objetivo del proyecto

El objetivo principal de LoveU es construir una base backend capaz de representar el flujo principal de una aplicación de matching:

1. Un usuario se registra en la plataforma.
2. El usuario crea su perfil público.
3. El perfil configura sus preferencias de búsqueda.
4. El perfil puede agregar fotos.
5. El perfil puede realizar swipes sobre otros perfiles.
6. Si dos perfiles se dan like mutuamente, se genera un match.
7. Los perfiles con match pueden intercambiar mensajes.
8. Los usuarios pueden reportar perfiles inapropiados o sospechosos.
9. El sistema puede generar notificaciones según eventos importantes.

---

## Tecnologías utilizadas

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Jakarta Validation
- Lombok
- Maven

---

## Arquitectura general

El proyecto sigue una estructura por capas:

```text
controller  → recibe las peticiones HTTP
service     → contiene la lógica de negocio
repository  → comunica con la base de datos
model       → representa las entidades persistentes
dto         → transporta datos entre capas
exception   → maneja errores personalizados