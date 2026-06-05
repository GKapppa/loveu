# Modelo de Base de Datos - LoveU

Este documento describre el modelo conceptual
LoveU es una aplicaicon estilo match entre un usuario y otro usuario.

## Tablas principales

- usuarios
- perfiles
- fotos
- intereses
- perfil_intereses
- preferencias
- swipes
- matches
- mensajes
- reportes

## Relaciones principales
- Un usuario tiene un perfil.

- Un usuario tiene una configuracion de preferencia.

- Un perfil puede tener muchos reportes.

- Un perfil puede tener muchas fotos.

- Un perfil puede tener muchos intereses.

- Un interes puede pertenecer a muchos perfiles.

- La relacion entre perfiles e intereses se resuelve mediante la tabla perfil_intereses.

- Un usuario puede realizar muchos swipes.

- Un match se genera cuando dos usuarios se dan like mutuamente.

- un match puede tener muchos mensajes.


# Flujo general

- El usuario se registra.

- El usuario crea su perfil.

- El perfil agrega fotos e intereses.

- El usuario configura sus preferencias (gustos).

- El usuario realiza swipes sobre algún otro perfil.

- Si dos usuarios se dan like mutuamente, se crea un match.

- Si existe un match, los usuarios pueden enviar mensajes.




