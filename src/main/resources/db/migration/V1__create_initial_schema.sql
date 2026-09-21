-- =========================================================
-- USUÁRIOS
-- =========================================================

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);


-- =========================================================
-- EQUIPAMENTOS
-- =========================================================

CREATE TABLE equipment (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    category VARCHAR(100),
    location VARCHAR(150),
    description TEXT,
    status VARCHAR(30) NOT NULL DEFAULT 'ATIVO'
);


-- =========================================================
-- PLANO DE MANUTENÇÃO
-- =========================================================

CREATE TABLE maintenance_plan (
    id BIGSERIAL PRIMARY KEY,
    equipment_id BIGINT NOT NULL,
    description VARCHAR(255) NOT NULL,
    frequency INTEGER NOT NULL,
    frequency_unit VARCHAR(20) NOT NULL,
    last_maintenance_date DATE,
    next_maintenance_date DATE NOT NULL,

    CONSTRAINT fk_maintenance_plan_equipment
        FOREIGN KEY (equipment_id)
        REFERENCES equipment(id)
        ON DELETE CASCADE
);


-- =========================================================
-- MANUTENÇÕES REALIZADAS
-- =========================================================

CREATE TABLE maintenance (
    id BIGSERIAL PRIMARY KEY,
    equipment_id BIGINT NOT NULL,
    maintenance_plan_id BIGINT,
    date DATE NOT NULL,
    description TEXT NOT NULL,
    cost NUMERIC(10, 2),
    observation TEXT,

    CONSTRAINT fk_maintenance_equipment
        FOREIGN KEY (equipment_id)
        REFERENCES equipment(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_maintenance_plan
        FOREIGN KEY (maintenance_plan_id)
        REFERENCES maintenance_plan(id)
        ON DELETE SET NULL
);