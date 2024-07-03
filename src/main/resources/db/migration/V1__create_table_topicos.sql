CREATE TABLE topicos (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_creacion datetime NOT NULL,
    active TINYINT NOT NULL
);
