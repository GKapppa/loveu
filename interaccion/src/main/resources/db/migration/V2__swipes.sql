CREATE TABLE swipes (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    perfil_origen_id INTEGER NOT NULL,
    perfil_destino_id INTEGER NOT NULL,
    decision VARCHAR(10) NOT NULL,
    fecha TIMESTAMP NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);
