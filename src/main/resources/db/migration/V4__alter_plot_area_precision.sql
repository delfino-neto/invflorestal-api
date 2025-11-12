-- Aumentar precisão do campo area_m2 para suportar áreas maiores (municípios, grandes regiões)
-- Antes: NUMERIC(10,2) - máximo 99.999.999,99 m² (~10.000 hectares)
-- Depois: NUMERIC(15,2) - máximo 9.999.999.999.999,99 m² (~1.000.000.000 hectares)
ALTER TABLE plot ALTER COLUMN area_m2 TYPE NUMERIC(15,2);
