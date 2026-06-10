CREATE TABLE notificaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    perfil_destinatario_id INT NOT NULL,
    type VARCHAR(20) NOT NULL,
    message VARCHAR(500) NOT NULL,
    leido BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL
);