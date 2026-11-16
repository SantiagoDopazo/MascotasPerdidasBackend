-- Inicializacion para instalaciones nuevas. No modifica tablas existentes.
CREATE TABLE IF NOT EXISTS usuarios (
    id UUID PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(254) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    CONSTRAINT uk_usuarios_email UNIQUE (email),
    CONSTRAINT ck_usuarios_email_normalizado CHECK (email = lower(email))
);
