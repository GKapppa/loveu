CREATE TABLE usuarios (
    id                SERIAL PRIMARY KEY,
    primer_nombre     VARCHAR(50)  NOT NULL,
    primer_apellido   VARCHAR(50)  NOT NULL,
    fecha_nacimiento  DATE         NOT NULL,
    telefono          VARCHAR(20),
    activo            BOOLEAN      NOT NULL DEFAULT TRUE
);