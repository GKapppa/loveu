-- Poblar datos de prueba LoveU
-- Ejecutar en PgAdmin dentro de la base loveu_db.
-- Borra los datos actuales y deja 2 registros por tabla principal.



TRUNCATE TABLE
    notifications,
    messages,
    reportes,
    matches,
    swipes,
    foto_perfil,
    preferencias,
    perfiles,
    auth,
    comunas,
    regiones,
    usuarios
RESTART IDENTITY CASCADE;

-- Limpieza por si la tabla quedo con una columna antigua.
ALTER TABLE notifications DROP COLUMN IF EXISTS user_id;

-- Regiones
INSERT INTO regiones (nombre_region, abreviacion)
VALUES
('Region Metropolitana', 'RM'),
('Valparaiso', 'VAL');

-- Comunas
INSERT INTO comunas (nombre_comuna, region_id)
VALUES
('Santiago', 1),
('Vina del Mar', 2);

-- Usuarios
INSERT INTO usuarios (primer_nombre, primer_apellido, fecha_nacimiento, telefono, activo)
VALUES
('Daniel', 'Reyes', '2001-04-15', '+56911111111', true),
('Camila', 'Torres', '2002-08-22', '+56922222222', true);

-- Auth
INSERT INTO auth (email, password, rol, activo, usuario_id)
VALUES
('daniel@test.cl', '123456', 'USUARIO', true, 1),
('camila@test.cl', '123456', 'USUARIO', true, 2);

-- Perfiles
INSERT INTO perfiles (nombre, biografia, ocupacion, altura_cm, activo, usuario_id, comuna_id)
VALUES
('Dani', 'Me gusta salir, conversar y conocer gente nueva.', 'Estudiante', 175, true, 1, 1),
('Cami', 'Me gusta la musica, el cafe y los panoramas tranquilos.', 'Disenadora', 165, true, 2, 2);

-- Preferencias
-- Cada preferencia pertenece a un perfil por medio de perfil_id.
INSERT INTO preferencias (perfil_id, genero_deseado, edad_minima, edad_maxima, distancia_maxima_km, activo)
VALUES
((SELECT id FROM perfiles WHERE nombre = 'Dani'), 'Femenino', 18, 30, 50, true),
((SELECT id FROM perfiles WHERE nombre = 'Cami'), 'Masculino', 20, 35, 40, true);

-- Fotos de perfil
INSERT INTO foto_perfil (perfil_id, url_foto, principal, orden, fecha_subida, activo)
VALUES
(1, 'https://pepepepepe.com/falsa/image/upload/perfil_dani_1.jpg', true, 1, CURRENT_TIMESTAMP, true),
(2, 'https://tatatatata.com/falsa/image/upload/perfil_cami_1.jpg', true, 1, CURRENT_TIMESTAMP, true);

-- Swipes
INSERT INTO swipes (perfil_origen_id, perfil_destino_id, decision, fecha, activo)
VALUES
(1, 2, 'LIKE', CURRENT_TIMESTAMP, true),
(2, 1, 'LIKE', CURRENT_TIMESTAMP, true);

-- Matches
-- Se dejan 2 filas para cumplir con la prueba de 2 datos por tabla.
INSERT INTO matches (perfil_a_id, perfil_b_id, status, matched_at)
VALUES
(1, 2, 'ACTIVE', CURRENT_TIMESTAMP),
(2, 1, 'ACTIVE', CURRENT_TIMESTAMP);

-- Mensajes
INSERT INTO messages (match_id, perfil_emisor_id, perfil_receptor_id, contenido, sent_at, is_read)
VALUES
(1, 1, 2, 'Hola Cami, como estas?', CURRENT_TIMESTAMP, false),
(1, 2, 1, 'Hola Dani, bien y tu?', CURRENT_TIMESTAMP, false);

-- Notificaciones
INSERT INTO notifications (perfil_destinatario_id, type, message, is_read, created_at)
VALUES
(1, 'MATCH', 'Tienes un nuevo match con Cami', false, CURRENT_TIMESTAMP),
(2, 'MATCH', 'Tienes un nuevo match con Dani', false, CURRENT_TIMESTAMP);

-- Reportes
INSERT INTO reportes (perfil_reportante_id, perfil_reportado_id, razon_reporte, estado_reporte, fecha_reporte, activo)
VALUES
(1, 2, 'Perfil con informacion falsa', 'EN_REVISION', CURRENT_TIMESTAMP, true),
(2, 1, 'Comportamiento sospechoso', 'EN_REVISION', CURRENT_TIMESTAMP, true);

-- Verificacion rapida
SELECT 'usuarios' AS tabla, COUNT(*) AS total FROM usuarios
UNION ALL SELECT 'auth', COUNT(*) FROM auth
UNION ALL SELECT 'regiones', COUNT(*) FROM regiones
UNION ALL SELECT 'comunas', COUNT(*) FROM comunas
UNION ALL SELECT 'perfiles', COUNT(*) FROM perfiles
UNION ALL SELECT 'preferencias', COUNT(*) FROM preferencias
UNION ALL SELECT 'foto_perfil', COUNT(*) FROM foto_perfil
UNION ALL SELECT 'swipes', COUNT(*) FROM swipes
UNION ALL SELECT 'matches', COUNT(*) FROM matches
UNION ALL SELECT 'messages', COUNT(*) FROM messages
UNION ALL SELECT 'notifications', COUNT(*) FROM notifications
UNION ALL SELECT 'reportes', COUNT(*) FROM reportes;

-- Ver preferencias con el perfil al que pertenecen.
SELECT
    p.id AS perfil_id,
    p.nombre AS perfil,
    pr.genero_deseado,
    pr.edad_minima,
    pr.edad_maxima,
    pr.distancia_maxima_km
FROM preferencias pr
INNER JOIN perfiles p ON p.id = pr.perfil_id
ORDER BY p.id;
