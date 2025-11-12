-- Create roles table
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP
);

-- Create _user table
CREATE TABLE _user (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    date_of_birth DATE,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    account_locked BOOLEAN DEFAULT FALSE,
    enabled BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP
);

-- Create user_roles join table
CREATE TABLE _user_roles (
    user_id BIGINT NOT NULL,
    roles_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, roles_id),
    FOREIGN KEY (user_id) REFERENCES _user(id) ON DELETE CASCADE,
    FOREIGN KEY (roles_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Create tokens table
CREATE TABLE tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    expires_at TIMESTAMP,
    validated_at TIMESTAMP,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES _user(id) ON DELETE CASCADE
);

-- Create species_taxonomy table
CREATE TABLE species_taxonomy (
    id BIGSERIAL PRIMARY KEY,
    scientific_name VARCHAR(255),
    common_name VARCHAR(255),
    family VARCHAR(255),
    genus VARCHAR(255),
    code VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create collection_area table
CREATE TABLE collection_area (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    geometry POLYGON,
    created_by BIGINT,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES _user(id) ON DELETE SET NULL
);

-- Create plot table
CREATE TABLE plot (
    id BIGSERIAL PRIMARY KEY,
    area_id BIGINT,
    geometry POLYGON,
    plot_code VARCHAR(255),
    area_m2 NUMERIC(10, 2),
    slope_deg NUMERIC(5, 2),
    aspect_deg NUMERIC(5, 2),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (area_id) REFERENCES collection_area(id) ON DELETE SET NULL
);

-- Create specimen_object table
CREATE TABLE specimen_object (
    id BIGSERIAL PRIMARY KEY,
    plot_id BIGINT,
    species_id BIGINT,
    latitude NUMERIC(10, 8),
    longitude NUMERIC(11, 8),
    observer_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (plot_id) REFERENCES plot(id) ON DELETE SET NULL,
    FOREIGN KEY (species_id) REFERENCES species_taxonomy(id) ON DELETE SET NULL,
    FOREIGN KEY (observer_id) REFERENCES _user(id) ON DELETE SET NULL
);

-- Create species_info table
CREATE TABLE species_info (
    id BIGSERIAL PRIMARY KEY,
    object_id BIGINT UNIQUE,
    height_m NUMERIC(5, 2),
    dbm_cm NUMERIC(5, 2),
    age_years INTEGER,
    condition BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (object_id) REFERENCES specimen_object(id) ON DELETE CASCADE
);

-- Create media table
CREATE TABLE media (
    id BIGSERIAL PRIMARY KEY,
    object_id BIGINT,
    url VARCHAR(255),
    type VARCHAR(50),
    description TEXT,
    uploaded_by BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (object_id) REFERENCES specimen_object(id) ON DELETE CASCADE,
    FOREIGN KEY (uploaded_by) REFERENCES _user(id) ON DELETE SET NULL
);

-- Create sync_log table
CREATE TABLE sync_log (
    id BIGSERIAL PRIMARY KEY,
    from_plot_id BIGINT,
    to_plot_id BIGINT,
    synced_by BIGINT,
    synced_at TIMESTAMP,
    notes TEXT,
    FOREIGN KEY (from_plot_id) REFERENCES plot(id) ON DELETE SET NULL,
    FOREIGN KEY (to_plot_id) REFERENCES plot(id) ON DELETE SET NULL,
    FOREIGN KEY (synced_by) REFERENCES _user(id) ON DELETE SET NULL
);

-- Create indexes for better performance
CREATE INDEX idx_user_email ON _user(email);
CREATE INDEX idx_species_scientific_name ON species_taxonomy(scientific_name);
CREATE INDEX idx_species_family ON species_taxonomy(family);
CREATE INDEX idx_specimen_species ON specimen_object(species_id);
CREATE INDEX idx_specimen_plot ON specimen_object(plot_id);
CREATE INDEX idx_media_object ON media(object_id);
