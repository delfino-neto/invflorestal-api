-- ============================================
-- 📊 EXEMPLOS DE INSERT - COLLECTION AREA (CORRIGIDO)
-- ============================================

-- ============================================
-- 🗺️ FORMATO CORRETO PARA POSTGRESQL
-- ============================================
-- PostgreSQL usa o formato: '((x1,y1),(x2,y2),(x3,y3),...)'
-- Não precisa repetir o primeiro ponto no final

-- ============================================
-- 🗺️ EXEMPLO 1: Área Simples (Quadrado)
-- ============================================
INSERT INTO collection_area (name, geometry, created_by, notes, created_at, updated_at)
VALUES (
    'Mata Atlântica - Zona A',
    '((-47.9,-15.8),(-47.89,-15.8),(-47.89,-15.79),(-47.9,-15.79))',
    1,
    'Área de inventário florestal na região da Mata Atlântica. Possui alta biodiversidade com predominância de espécies nativas.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- ============================================
-- 🗺️ EXEMPLO 2: Cerrado - Área Sul
-- ============================================
INSERT INTO collection_area (name, geometry, created_by, notes, created_at, updated_at)
VALUES (
    'Cerrado - Área Sul',
    '((-47.95,-15.85),(-47.93,-15.85),(-47.92,-15.83),(-47.94,-15.82),(-47.96,-15.83))',
    1,
    'Área de cerrado com vegetação típica da região. Ideal para estudo de espécies de transição.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- ============================================
-- 🗺️ EXEMPLO 3: Caatinga - Área Experimental
-- ============================================
INSERT INTO collection_area (name, geometry, created_by, notes, created_at, updated_at)
VALUES (
    'Caatinga - Experimental',
    '((-47.88,-15.78),(-47.87,-15.78),(-47.87,-15.77),(-47.88,-15.77))',
    1,
    'Área experimental para testes de metodologia de inventário em região de caatinga.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- ============================================
-- 🗺️ EXEMPLO 4: Amazônia - Reserva Legal
-- ============================================
INSERT INTO collection_area (name, geometry, created_by, notes, created_at, updated_at)
VALUES (
    'Amazônia - Reserva Legal',
    '((-60.0,-3.0),(-59.98,-3.0),(-59.98,-2.98),(-60.0,-2.98))',
    3,
    'Área de reserva legal com floresta amazônica preservada. Acesso restrito para pesquisa científica.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- ============================================
-- 🗺️ EXEMPLO 5: Pantanal - Área Alagada
-- ============================================
INSERT INTO collection_area (name, geometry, created_by, notes, created_at, updated_at)
VALUES (
    'Pantanal - Várzea Norte',
    '((-56.5,-16.5),(-56.48,-16.5),(-56.48,-16.48),(-56.5,-16.48))',
    1,
    'Área de várzea no Pantanal. Sujeita a alagamentos sazonais. Inventário realizado durante período de seca.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- ============================================
-- 🗺️ EXEMPLO 6: Mata Atlântica - Serra
-- ============================================
INSERT INTO collection_area (name, geometry, created_by, notes, created_at, updated_at)
VALUES (
    'Mata Atlântica - Serra do Mar',
    '((-45.5,-23.0),(-45.48,-23.0),(-45.48,-22.98),(-45.5,-22.98))',
    1,
    'Área em região serrana com alta altitude. Vegetação densa e diversificada.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- ============================================
-- 🗺️ EXEMPLO 7: Área Urbana - Parque
-- ============================================
INSERT INTO collection_area (name, geometry, created_by, notes, created_at, updated_at)
VALUES (
    'Parque Urbano - Cidade Jardim',
    '((-47.92,-15.81),(-47.91,-15.81),(-47.91,-15.80),(-47.92,-15.80))',
    1,
    'Área de parque urbano. Inventário para monitoramento da arborização urbana.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- ============================================
-- 🗺️ EXEMPLO 8: Restauração Florestal
-- ============================================
INSERT INTO collection_area (name, geometry, created_by, notes, created_at, updated_at)
VALUES (
    'Projeto de Restauração - Área 001',
    '((-47.93,-15.82),(-47.92,-15.82),(-47.92,-15.81),(-47.93,-15.81))',
    1,
    'Área em processo de restauração florestal iniciado em 2023. Monitoramento trimestral do desenvolvimento das mudas.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- ============================================
-- 📊 VERIFICAR DADOS INSERIDOS
-- ============================================

-- Listar todas as áreas com informações completas
SELECT 
    ca.id,
    ca.name,
    ca.geometry::text as geometry_text,
    u.name as created_by_name,
    ca.notes,
    ca.created_at,
    ca.updated_at
FROM collection_area ca
LEFT JOIN _user u ON ca.created_by = u.id
ORDER BY ca.created_at DESC;

-- Contar total de áreas
SELECT COUNT(*) as total_areas FROM collection_area;

-- Ver área específica
SELECT * FROM collection_area WHERE id = 1;

-- ============================================
-- 🔄 LIMPAR DADOS DE TESTE (se necessário)
-- ============================================

-- DELETE FROM collection_area WHERE created_by = 1;
-- ou
-- TRUNCATE TABLE collection_area RESTART IDENTITY CASCADE;