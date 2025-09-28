-- Creazione della tabella per l'entità Contact

CREATE TABLE contact (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(50),
    company_name VARCHAR(255),
    status VARCHAR(50)
);