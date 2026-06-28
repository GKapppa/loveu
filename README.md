# LoveU v2 - Microservicios

Backend de aplicacion de citas tipo Tinder, migrado de monolitico a microservicios con Spring Boot.
El README fue redactado con IA pero con instrucciones claras dadas por Daniel Pinto
## Integrantes

- Daniel Pinto
- Diego Santibañez
- Paloma Moreno

## Arquitectura

```
                         ┌──────────────┐
                         │   Eureka     │
                         │   :8761      │
                         └──────┬───────┘
                                │
                         ┌──────▼───────┐
                         │   Gateway    │
                         │   :8084      │
                         │  (Swagger)   │
                         └──────┬───────┘
               ┌────────────────┼────────────────┐
               │                │                │
    ┌──────────▼─────┐  ┌──────▼──────┐  ┌──────▼──────┐
    │ usuario-service│  │ interaccion │  │   reporte   │
    │ (8081)         │  │ (8082)      │  │ (8083)      │
    ├────────────────┤  ├─────────────┤  ├─────────────┤
    │ Usuario        │  │ Swipe       │  │ Reporte     │
    │ Auth           │  │ Match       │  └─────────────┘
    │ Perfil         │  │ Mensaje     │
    │ Preferencia 🆕 │  │ Notificacion│
    │ FotoPerfil     │  └─────────────┘
    │ Comuna         │
    │ Region         │
    └────────────────┘
```

Cada microservicio tiene su propia base de datos PostgreSQL, migraciones Flyway y API independiente.

## Funcionalidad de Preferencia 🆕

La preferencia define los filtros de busqueda que un perfil usa para encontrar matches. Cada perfil tiene **una unica preferencia activa**. Cuando un usuario hace swipe, el sistema usa su preferencia para filtrar los perfiles que se le muestran.

```
┌──────────────────────────────────────────────────────────────────┐
│                      PREFERENCIA (1 por perfil)                  │
│                                                                  │
│  ┌─────────────┐   ┌──────────────┐   ┌────────────────────┐    │
│  │  Rango Edad │   │  Distancia   │   │    Rango Altura    │    │
│  │             │   │              │   │                    │    │
│  │  edadMin ───┼───┤ distanciaMax │   │ alturaMinCm ───┐  │    │
│  │    │        │   │   (en km)    │   │       │        │  │    │
│  │    │        │   │              │   │       │        │  │    │
│  │  edadMax ───┘   │  Ej: 50 km   │   │ alturaMaxCm ──┘  │    │
│  │             │   │              │   │                    │    │
│  │ Ej: 25-35   │   │  Filtra:     │   │  Ej: 160-180 cm   │    │
│  │             │   │  solo gente  │   │                    │    │
│  │  Filtra:    │   │  cercana     │   │  Filtra:          │    │
│  │  solo gente │   └──────────────┘   │  solo gente en    │    │
│  │  en ese     │                      │  ese rango de     │    │
│  │  rango      │                      │  altura           │    │
│  └─────────────┘                      └────────────────────┘    │
│                                                                  │
│  ┌──────────────────────┐                                       │
│  │  Genero Preferido    │        ┌─────────────────────┐        │
│  │                      │        │  Resultado:         │        │
│  │  - Masculino         │───────▶│  Solo perfiles que  │        │
│  │  - Femenino          │        │  cumplen TODOS los  │        │
│  │  - No binario        │        │  filtros aparecen   │        │
│  │  - Todos             │        │  en el swipe        │        │
│  └──────────────────────┘        └─────────────────────┘        │
│                                                                  │
└──────────────────────────────────────────────────────────────────┘
         │
         │  Pertenece a
         ▼
┌─────────────────┐
│     PERFIL      │
│  (1 perfil = 1  │
│   preferencia)  │
│                 │
│  - nombreVisible│
│  - biografia    │
│  - ocupacion    │
│  - alturaCm     │
│  - usuarioId    │
│  - comunaId     │
└─────────────────┘
```

**Validaciones de Preferencia:**
- Edad minima: 18-99 años, debe ser ≤ edad maxima
- Edad maxima: 18-99 años, debe ser ≥ edad minima
- Distancia maxima: 1-500 km
- Altura: 100-250 cm, min ≤ max (opcional)
- Genero: solo "Masculino", "Femenino", "No binario" o "Todos"
- Un perfil no puede tener mas de una preferencia activa

## Tecnologias

- Java 21
- Spring Boot 4.1.0
- Spring Cloud 2025.1.2
- Spring Data JPA / Hibernate 7
- Spring Cloud Gateway (WebMVC)
- Netflix Eureka (Service Discovery)
- Flyway
- PostgreSQL
- Lombok
- Jakarta Validation
- SpringDoc OpenAPI (Swagger)
- Spring HATEOAS (usuarios)
- JUnit / Mockito

## Como arrancar

```bash
# 1. Iniciar PostgreSQL
sudo systemctl start postgresql

# 2. Levantar todo
./iniciar.sh
```

Luego acceder a:
- **Eureka**: http://localhost:8761
- **Gateway / Swagger**: http://localhost:8084/swagger-ui.html

`iniciar.sh` levanta Eureka primero, espera 15 segundos, y luego levanta los 4 microservicios en paralelo.

## Endpoints

### usuario-service (puerto 8081)

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| POST | /api/v2/usuarios | Crear usuario |
| GET | /api/v2/usuarios | Listar usuarios (HATEOAS) |
| GET | /api/v2/usuarios/{id} | Buscar usuario (HATEOAS) |
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
| DELETE | /api/v2/perfiles/{id} | Desactivar perfil |
| GET | /api/v2/regiones | Listar regiones |
| GET | /api/v2/regiones/{id} | Buscar region |
| GET | /api/v2/comunas | Listar comunas |
| GET | /api/v2/comunas/{id} | Buscar comuna |
| POST | /api/v2/fotoperfil | Subir foto de perfil |
| GET | /api/v2/fotoperfil/perfil/{perfilId} | Fotos de un perfil |
| GET | /api/v2/fotoperfil/perfil/{perfilId}/principal | Foto principal |
| PUT | /api/v2/fotoperfil/{fotoId}/principal | Marcar como principal |
| DELETE | /api/v2/fotoperfil/{fotoId} | Desactivar foto |
| POST | /api/v2/preferencias | Crear preferencia 🆕 |
| GET | /api/v2/preferencias | Listar preferencias 🆕 |
| GET | /api/v2/preferencias/{id} | Buscar por id 🆕 |
| GET | /api/v2/preferencias/perfil/{perfilId} | Buscar por perfil 🆕 |
| PUT | /api/v2/preferencias/{id} | Actualizar preferencia 🆕 |
| DELETE | /api/v2/preferencias/{id} | Desactivar preferencia 🆕 |

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

## Validaciones

Cada microservicio tiene una clase de validaciones separada (patron del profe):

| Microservicio | Clase | Metodos |
|---------------|-------|---------|
| usuarios | `UsuarioValidaciones.java` | 13 metodos |
| interaccion | `InteraccionValidaciones.java` | 8 metodos |
| reporte | `ReporteValidaciones.java` | 3 metodos |

## Tests

| Clase | Tests | Estado |
|-------|-------|--------|
| `UsuarioServiceTest` | 3 tests | ✅ |
| `AuthServiceTest` | 13 tests | ✅ |
| `ReporteServiceTest` | 3 tests | ✅ |

## Estructura del proyecto

```
LoveU/
├── iniciar.sh
├── iniciar.bat
├── eureka/                     # Service Discovery (:8761)
├── gateway/                    # API Gateway (:8084)
│   └── Swagger unificado con dropdown de servicios
├── usuarios/                   # usuario-service (:8081)
│   └── db/migration/
│       ├── V1__regiones.sql
│       ├── V2__usuarios.sql
│       ├── V3__auth.sql
│       ├── V4__perfil.sql
│       ├── V5__comuna.sql
│       ├── V6__foto_perfil.sql
│       └── V7__preferencia.sql 🆕
├── interaccion/                # interaccion-service (:8082)
└── reporte/                    # reporte-service (:8083)
```

## Pendientes

- [ ] @ControllerAdvice global
- [x] Gateway-service (Spring Cloud Gateway)
- [x] Pruebas unitarias (JUnit / Mockito)
- [x] Swagger / OpenAPI
- [x] FotoPerfil en usuario-service
- [x] Preferencia en usuario-service 🆕
- [x] Region completo
- [x] HATEOAS en UsuarioController
- [x] Eureka Server + API Gateway

## Version anterior

El codigo monolitico original se migro a: [loveu-v1](https://github.com/GKapppa/loveu-v1)
