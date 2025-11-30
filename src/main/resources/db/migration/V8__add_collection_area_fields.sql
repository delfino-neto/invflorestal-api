-- Add new fields to collection_area table
ALTER TABLE collection_area
ADD COLUMN biome VARCHAR(100),
ADD COLUMN climate_zone VARCHAR(100),
ADD COLUMN soil_type VARCHAR(100),
ADD COLUMN conservation_status VARCHAR(100),
ADD COLUMN vegetation_type VARCHAR(100),
ADD COLUMN altitude_m NUMERIC(8, 2),
ADD COLUMN protected_area BOOLEAN,
ADD COLUMN protected_area_name VARCHAR(255),
ADD COLUMN land_owner VARCHAR(255);
