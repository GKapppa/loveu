CREATE TABLE comunas (
    id              SERIAL PRIMARY KEY,
    nombre_comuna   VARCHAR(50)  NOT NULL,
    region_id       INT          NOT NULL,
    CONSTRAINT fk_comuna_region FOREIGN KEY (region_id) REFERENCES regiones(id)
);