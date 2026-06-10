# LoveU v2 - Microservicios

Backend de aplicacion de citas tipo Tinder, migrado de monolitico a microservicios con Spring Boot.

## Integrantes

- Daniel Pinto
- Diego SantiBañez
- Paloma Moreno

## Arquitectura

```
┌─────────────────┐     ┌──────────────────┐     ┌────────────────┐
│ usuario-service │────▶│ interaccion-     │────▶│ reporte-       │
│ (8081)          │     │ service (8082)    │     │ service (8083) │
├─────────────────┤     ├──────────────────┤     ├────────────────┤
│ Usuario         │     │ Swipe            │     │ Reporte        │
│ Auth            │     │ Match            │     └────────────────┘
│ Perfil          │     │ Mensaje          │
│ FotoPerfil      │     │ Notificacion     │
│ Preferencia     │     └──────────────────┘
│ Comuna          │
│ Region          │
└─────────────────┘
```

Cada microservicio tiene su propia base de datos PostgreSQL, migraciones Flyway y API independiente.

## Tecnologias

- Java 21
- Spring Boot 4.0.6
- Spring Data JPA
- Spring Cloud OpenFeign
- Flyway
- PostgreSQL
- Lombok
- Jakarta Validation

## Endpoints

### usuario-service (puerto 8081)

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| POST | /api/v2/usuarios | Crear usuario |
| GET | /api/v2/usuarios | Listar usuarios |
| PUT | /api/v2/usuarios/{id} | Actualizar usuario |
| DELETE | /api/v2/usuarios/{id} | Eliminar usuario |
| POST | /api/v2/auth | Crear autenticacion |
| POST | /api/v2/auth/login | Iniciar sesion |
| GET | /api/v2/auth | Listar auths |
| GET | /api/v2/auth/email/{email} | Buscar por email |
| DELETE | /api/v2/auth/{id} | Desactivar auth |
| POST | /api/v2/perfiles | Crear perfil |
| GET | /api/v2/perfiles | Listar perfiles |
| GET | /api/v2/perfiles/{id} | Buscar perfil |
| PUT | /api/v2/perfiles/{id} | Actualizar perfil |
| DELETE | /api/v2/perfiles/{id} | Eliminar perfil |
| GET | /api/v2/comunas | Listar comunas |

### interaccion-service (puerto 8082)

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| POST | /api/v2/swipes | Crear swipe |
| GET | /api/v2/swipes | Listar swipes |
| GET | /api/v2/swipes/origen/{id} | Swipes por origen |
| GET | /api/v2/swipes/destino/{id} | Swipes por destino |
| POST | /api/v2/matches/verificar | Verificar match |
| GET | /api/v2/matches | Listar matches |
| GET | /api/v2/matches/perfil/{id} | Matches por perfil |
| PATCH | /api/v2/matches/{id}/deshacer | Deshacer match |
| POST | /api/v2/mensajes | Enviar mensaje |
| GET | /api/v2/mensajes/match/{id} | Mensajes por match |
| GET | /api/v2/mensajes/no-leidos/{id} | Mensajes no leidos |
| PATCH | /api/v2/mensajes/{id}/leido | Marcar como leido |
| POST | /api/v2/notificaciones | Crear notificacion |
| GET | /api/v2/notificaciones/perfil/{id} | Notificaciones por perfil |
| GET | /api/v2/notificaciones/perfil/{id}/no-leidas | No leidas |
| PATCH | /api/v2/notificaciones/{id}/leida | Marcar como leida |

### reporte-service (puerto 8083)

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| POST | /api/v2/reportes | Crear reporte |
| GET | /api/v2/reportes | Listar reportes |
| GET | /api/v2/reportes/estado/{estado} | Filtrar por estado |
| GET | /api/v2/reportes/reportado/{id} | Reportes de un perfil |
| PATCH | /api/v2/reportes/{id}/estado | Actualizar estado |
| DELETE | /api/v2/reportes/{id} | Eliminar reporte |

## Pendientes

- [ ] Gateway-service (Spring Cloud Gateway)
- [ ] Pruebas unitarias (JUnit / Mockito)
- [ ] Swagger / OpenAPI
- [ ] Dockerfile y docker-compose
- [ ] Seguridad (BCrypt + JWT)
- [ ] Exception Handler global (@ControllerAdvice)
- [ ] FotoPerfil y Preferencia en usuario-service

## Version anterior

El codigo monolitico original se migro a: [loveu-v1](https://github.com/GKapppa/loveu-v1)
