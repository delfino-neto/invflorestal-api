-- Adicionar campo observation_date e tornar object_id NOT NULL
-- Como estamos mudando de OneToOne para ManyToOne, precisamos garantir que os dados existentes são preservados

-- Adicionar a coluna observation_date (usando created_at como valor padrão para registros existentes)
ALTER TABLE species_info
ADD COLUMN observation_date TIMESTAMP;

-- Atualizar registros existentes com a data de criação
UPDATE species_info
SET observation_date = created_at
WHERE observation_date IS NULL;

-- Tornar observation_date NOT NULL
ALTER TABLE species_info
ALTER COLUMN observation_date SET NOT NULL;

-- Remover a constraint UNIQUE de object_id se existir (para permitir múltiplos registros por espécime)
ALTER TABLE species_info
DROP CONSTRAINT IF EXISTS species_info_object_id_key;

-- Adicionar índice para melhor performance em consultas
CREATE INDEX idx_species_info_object_id ON species_info(object_id);
CREATE INDEX idx_species_info_observation_date ON species_info(observation_date);

-- Comentário para documentação
COMMENT ON COLUMN species_info.observation_date IS 'Data e hora da observação/medição das informações da espécime';
COMMENT ON TABLE species_info IS 'Histórico de informações e medições de espécimes ao longo do tempo (ManyToOne com specimen_object)';
