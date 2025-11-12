-- Inserir espécies de plantas brasileiras
-- Este catálogo inclui espécies de diversos biomas brasileiros

-- FAMÍLIA FABACEAE (Leguminosas)
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Anadenanthera colubrina', 'Angico-branco', 'Fabaceae', 'Anadenanthera', 'ANA-COL', CURRENT_TIMESTAMP),
('Anadenanthera peregrina', 'Angico-vermelho', 'Fabaceae', 'Anadenanthera', 'ANA-PER', CURRENT_TIMESTAMP),
('Enterolobium contortisiliquum', 'Tamboril', 'Fabaceae', 'Enterolobium', 'ENT-CON', CURRENT_TIMESTAMP),
('Hymenaea courbaril', 'Jatobá', 'Fabaceae', 'Hymenaea', 'HYM-COU', CURRENT_TIMESTAMP),
('Inga vera', 'Ingá-do-brejo', 'Fabaceae', 'Inga', 'ING-VER', CURRENT_TIMESTAMP),
('Inga laurina', 'Ingá-banana', 'Fabaceae', 'Inga', 'ING-LAU', CURRENT_TIMESTAMP),
('Inga marginata', 'Ingá-feijão', 'Fabaceae', 'Inga', 'ING-MAR', CURRENT_TIMESTAMP),
('Mimosa scabrella', 'Bracatinga', 'Fabaceae', 'Mimosa', 'MIM-SCA', CURRENT_TIMESTAMP),
('Mimosa caesalpiniifolia', 'Sabiá', 'Fabaceae', 'Mimosa', 'MIM-CAE', CURRENT_TIMESTAMP),
('Peltophorum dubium', 'Canafístula', 'Fabaceae', 'Peltophorum', 'PEL-DUB', CURRENT_TIMESTAMP),
('Pterogyne nitens', 'Amendoim-bravo', 'Fabaceae', 'Pterogyne', 'PTE-NIT', CURRENT_TIMESTAMP),
('Schizolobium parahyba', 'Guapuruvu', 'Fabaceae', 'Schizolobium', 'SCH-PAR', CURRENT_TIMESTAMP),
('Copaifera langsdorffii', 'Copaíba', 'Fabaceae', 'Copaifera', 'COP-LAN', CURRENT_TIMESTAMP),
('Dalbergia nigra', 'Jacarandá-da-bahia', 'Fabaceae', 'Dalbergia', 'DAL-NIG', CURRENT_TIMESTAMP),
('Erythrina speciosa', 'Mulungu', 'Fabaceae', 'Erythrina', 'ERY-SPE', CURRENT_TIMESTAMP),
('Platypodium elegans', 'Jacarandá-do-campo', 'Fabaceae', 'Platypodium', 'PLA-ELE', CURRENT_TIMESTAMP),
('Bauhinia forficata', 'Pata-de-vaca', 'Fabaceae', 'Bauhinia', 'BAU-FOR', CURRENT_TIMESTAMP),
('Caesalpinia echinata', 'Pau-brasil', 'Fabaceae', 'Caesalpinia', 'CAE-ECH', CURRENT_TIMESTAMP),
('Caesalpinia ferrea', 'Pau-ferro', 'Fabaceae', 'Caesalpinia', 'CAE-FER', CURRENT_TIMESTAMP),
('Machaerium villosum', 'Jacarandá-paulista', 'Fabaceae', 'Machaerium', 'MAC-VIL', CURRENT_TIMESTAMP);

-- FAMÍLIA MYRTACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Eugenia uniflora', 'Pitanga', 'Myrtaceae', 'Eugenia', 'EUG-UNI', CURRENT_TIMESTAMP),
('Eugenia pyriformis', 'Uvaia', 'Myrtaceae', 'Eugenia', 'EUG-PYR', CURRENT_TIMESTAMP),
('Eugenia brasiliensis', 'Grumixama', 'Myrtaceae', 'Eugenia', 'EUG-BRA', CURRENT_TIMESTAMP),
('Psidium guajava', 'Goiabeira', 'Myrtaceae', 'Psidium', 'PSI-GUA', CURRENT_TIMESTAMP),
('Psidium cattleianum', 'Araçá', 'Myrtaceae', 'Psidium', 'PSI-CAT', CURRENT_TIMESTAMP),
('Campomanesia xanthocarpa', 'Guabiroba', 'Myrtaceae', 'Campomanesia', 'CAM-XAN', CURRENT_TIMESTAMP),
('Campomanesia phaea', 'Cambuci', 'Myrtaceae', 'Campomanesia', 'CAM-PHA', CURRENT_TIMESTAMP),
('Myrciaria cauliflora', 'Jabuticaba', 'Myrtaceae', 'Myrciaria', 'MYR-CAU', CURRENT_TIMESTAMP),
('Plinia trunciflora', 'Jabuticaba-de-cabinho', 'Myrtaceae', 'Plinia', 'PLI-TRU', CURRENT_TIMESTAMP),
('Syzygium cumini', 'Jambolão', 'Myrtaceae', 'Syzygium', 'SYZ-CUM', CURRENT_TIMESTAMP),
('Calyptranthes concinna', 'Guamirim', 'Myrtaceae', 'Calyptranthes', 'CAL-CON', CURRENT_TIMESTAMP),
('Myrcia splendens', 'Guamirim-da-folha-fina', 'Myrtaceae', 'Myrcia', 'MYR-SPL', CURRENT_TIMESTAMP),
('Myrciaria glazioviana', 'Cabeludinha', 'Myrtaceae', 'Myrciaria', 'MYR-GLA', CURRENT_TIMESTAMP);

-- FAMÍLIA LAURACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Ocotea puberula', 'Canela-guaicá', 'Lauraceae', 'Ocotea', 'OCO-PUB', CURRENT_TIMESTAMP),
('Ocotea porosa', 'Imbuia', 'Lauraceae', 'Ocotea', 'OCO-POR', CURRENT_TIMESTAMP),
('Nectandra megapotamica', 'Canelinha', 'Lauraceae', 'Nectandra', 'NEC-MEG', CURRENT_TIMESTAMP),
('Persea americana', 'Abacateiro', 'Lauraceae', 'Persea', 'PER-AME', CURRENT_TIMESTAMP),
('Cinnamomum verum', 'Canela', 'Lauraceae', 'Cinnamomum', 'CIN-VER', CURRENT_TIMESTAMP);

-- FAMÍLIA ANACARDIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Schinus terebinthifolius', 'Aroeira-vermelha', 'Anacardiaceae', 'Schinus', 'SCH-TER', CURRENT_TIMESTAMP),
('Schinus molle', 'Aroeira-salsa', 'Anacardiaceae', 'Schinus', 'SCH-MOL', CURRENT_TIMESTAMP),
('Astronium graveolens', 'Guaritá', 'Anacardiaceae', 'Astronium', 'AST-GRA', CURRENT_TIMESTAMP),
('Myracrodruon urundeuva', 'Aroeira-do-sertão', 'Anacardiaceae', 'Myracrodruon', 'MYR-URU', CURRENT_TIMESTAMP),
('Anacardium occidentale', 'Cajueiro', 'Anacardiaceae', 'Anacardium', 'ANA-OCC', CURRENT_TIMESTAMP),
('Spondias mombin', 'Cajá', 'Anacardiaceae', 'Spondias', 'SPO-MOM', CURRENT_TIMESTAMP),
('Spondias tuberosa', 'Umbuzeiro', 'Anacardiaceae', 'Spondias', 'SPO-TUB', CURRENT_TIMESTAMP);

-- FAMÍLIA BIGNONIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Tabebuia chrysotricha', 'Ipê-amarelo', 'Bignoniaceae', 'Tabebuia', 'TAB-CHR', CURRENT_TIMESTAMP),
('Handroanthus impetiginosus', 'Ipê-roxo', 'Bignoniaceae', 'Handroanthus', 'HAN-IMP', CURRENT_TIMESTAMP),
('Handroanthus albus', 'Ipê-branco', 'Bignoniaceae', 'Handroanthus', 'HAN-ALB', CURRENT_TIMESTAMP),
('Handroanthus heptaphyllus', 'Ipê-roxo-de-sete-folhas', 'Bignoniaceae', 'Handroanthus', 'HAN-HEP', CURRENT_TIMESTAMP),
('Jacaranda mimosifolia', 'Jacarandá-mimoso', 'Bignoniaceae', 'Jacaranda', 'JAC-MIM', CURRENT_TIMESTAMP),
('Zeyheria tuberculosa', 'Ipê-felpudo', 'Bignoniaceae', 'Zeyheria', 'ZEY-TUB', CURRENT_TIMESTAMP);

-- FAMÍLIA MELIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Cedrela fissilis', 'Cedro', 'Meliaceae', 'Cedrela', 'CED-FIS', CURRENT_TIMESTAMP),
('Swietenia macrophylla', 'Mogno', 'Meliaceae', 'Swietenia', 'SWI-MAC', CURRENT_TIMESTAMP),
('Trichilia elegans', 'Catiguá-vermelho', 'Meliaceae', 'Trichilia', 'TRI-ELE', CURRENT_TIMESTAMP),
('Guarea guidonia', 'Carrapeta', 'Meliaceae', 'Guarea', 'GUA-GUI', CURRENT_TIMESTAMP);

-- FAMÍLIA EUPHORBIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Croton urucurana', 'Sangra-d''água', 'Euphorbiaceae', 'Croton', 'CRO-URU', CURRENT_TIMESTAMP),
('Alchornea triplinervia', 'Tapiá', 'Euphorbiaceae', 'Alchornea', 'ALC-TRI', CURRENT_TIMESTAMP),
('Sapium glandulosum', 'Pau-de-leite', 'Euphorbiaceae', 'Sapium', 'SAP-GLA', CURRENT_TIMESTAMP);

-- FAMÍLIA MALVACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Ceiba speciosa', 'Paineira', 'Malvaceae', 'Ceiba', 'CEI-SPE', CURRENT_TIMESTAMP),
('Pseudobombax grandiflorum', 'Embiruçu', 'Malvaceae', 'Pseudobombax', 'PSE-GRA', CURRENT_TIMESTAMP),
('Luehea divaricata', 'Açoita-cavalo', 'Malvaceae', 'Luehea', 'LUE-DIV', CURRENT_TIMESTAMP),
('Guazuma ulmifolia', 'Mutambo', 'Malvaceae', 'Guazuma', 'GUA-ULM', CURRENT_TIMESTAMP);

-- FAMÍLIA SAPINDACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Cupania vernalis', 'Camboatã', 'Sapindaceae', 'Cupania', 'CUP-VER', CURRENT_TIMESTAMP),
('Matayba elaeagnoides', 'Camboatã-branco', 'Sapindaceae', 'Matayba', 'MAT-ELA', CURRENT_TIMESTAMP),
('Allophylus edulis', 'Chal-chal', 'Sapindaceae', 'Allophylus', 'ALL-EDU', CURRENT_TIMESTAMP),
('Dilodendron bipinnatum', 'Mamoninha', 'Sapindaceae', 'Dilodendron', 'DIL-BIP', CURRENT_TIMESTAMP);

-- FAMÍLIA MORACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Ficus insipida', 'Figueira-branca', 'Moraceae', 'Ficus', 'FIC-INS', CURRENT_TIMESTAMP),
('Ficus enormis', 'Figueira-mata-pau', 'Moraceae', 'Ficus', 'FIC-ENO', CURRENT_TIMESTAMP),
('Maclura tinctoria', 'Taiúva', 'Moraceae', 'Maclura', 'MAC-TIN', CURRENT_TIMESTAMP),
('Brosimum gaudichaudii', 'Mama-cadela', 'Moraceae', 'Brosimum', 'BRO-GAU', CURRENT_TIMESTAMP);

-- FAMÍLIA RUTACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Citrus sinensis', 'Laranjeira', 'Rutaceae', 'Citrus', 'CIT-SIN', CURRENT_TIMESTAMP),
('Citrus limon', 'Limoeiro', 'Rutaceae', 'Citrus', 'CIT-LIM', CURRENT_TIMESTAMP),
('Zanthoxylum rhoifolium', 'Mamica-de-porca', 'Rutaceae', 'Zanthoxylum', 'ZAN-RHO', CURRENT_TIMESTAMP),
('Esenbeckia leiocarpa', 'Guarantã', 'Rutaceae', 'Esenbeckia', 'ESE-LEI', CURRENT_TIMESTAMP);

-- FAMÍLIA SOLANACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Solanum mauritianum', 'Fumo-bravo', 'Solanaceae', 'Solanum', 'SOL-MAU', CURRENT_TIMESTAMP),
('Solanum granulosoleprosum', 'Gravitinga', 'Solanaceae', 'Solanum', 'SOL-GRA', CURRENT_TIMESTAMP),
('Cestrum intermedium', 'Coerana', 'Solanaceae', 'Cestrum', 'CES-INT', CURRENT_TIMESTAMP);

-- FAMÍLIA APOCYNACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Aspidosperma polyneuron', 'Peroba-rosa', 'Apocynaceae', 'Aspidosperma', 'ASP-POL', CURRENT_TIMESTAMP),
('Aspidosperma ramiflorum', 'Guatambu', 'Apocynaceae', 'Aspidosperma', 'ASP-RAM', CURRENT_TIMESTAMP),
('Tabernaemontana catharinensis', 'Leiteiro', 'Apocynaceae', 'Tabernaemontana', 'TAB-CAT', CURRENT_TIMESTAMP);

-- FAMÍLIA ARECACEAE (Palmeiras)
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Euterpe edulis', 'Palmito-juçara', 'Arecaceae', 'Euterpe', 'EUT-EDU', CURRENT_TIMESTAMP),
('Syagrus romanzoffiana', 'Jerivá', 'Arecaceae', 'Syagrus', 'SYA-ROM', CURRENT_TIMESTAMP),
('Bactris gasipaes', 'Pupunheira', 'Arecaceae', 'Bactris', 'BAC-GAS', CURRENT_TIMESTAMP),
('Attalea speciosa', 'Babaçu', 'Arecaceae', 'Attalea', 'ATT-SPE', CURRENT_TIMESTAMP),
('Mauritia flexuosa', 'Buriti', 'Arecaceae', 'Mauritia', 'MAU-FLE', CURRENT_TIMESTAMP),
('Acrocomia aculeata', 'Macaúba', 'Arecaceae', 'Acrocomia', 'ACR-ACU', CURRENT_TIMESTAMP);

-- FAMÍLIA SAPOTACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Pouteria caimito', 'Abiu', 'Sapotaceae', 'Pouteria', 'POU-CAI', CURRENT_TIMESTAMP),
('Pouteria ramiflora', 'Curriola', 'Sapotaceae', 'Pouteria', 'POU-RAM', CURRENT_TIMESTAMP),
('Chrysophyllum gonocarpum', 'Aguaí', 'Sapotaceae', 'Chrysophyllum', 'CHR-GON', CURRENT_TIMESTAMP),
('Manilkara subsericea', 'Maçaranduba', 'Sapotaceae', 'Manilkara', 'MAN-SUB', CURRENT_TIMESTAMP);

-- FAMÍLIA CHRYSOBALANACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Licania tomentosa', 'Oiti', 'Chrysobalanaceae', 'Licania', 'LIC-TOM', CURRENT_TIMESTAMP),
('Couepia grandiflora', 'Oiti-da-praia', 'Chrysobalanaceae', 'Couepia', 'COU-GRA', CURRENT_TIMESTAMP);

-- FAMÍLIA MELASTOMATACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Miconia cinnamomifolia', 'Jacatirão', 'Melastomataceae', 'Miconia', 'MIC-CIN', CURRENT_TIMESTAMP),
('Tibouchina granulosa', 'Quaresmeira', 'Melastomataceae', 'Tibouchina', 'TIB-GRA', CURRENT_TIMESTAMP),
('Tibouchina sellowiana', 'Manacá-da-serra', 'Melastomataceae', 'Tibouchina', 'TIB-SEL', CURRENT_TIMESTAMP);

-- FAMÍLIA NYCTAGINACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Guapira opposita', 'Maria-mole', 'Nyctaginaceae', 'Guapira', 'GUA-OPP', CURRENT_TIMESTAMP),
('Bougainvillea glabra', 'Primavera', 'Nyctaginaceae', 'Bougainvillea', 'BOU-GLA', CURRENT_TIMESTAMP);

-- FAMÍLIA LECYTHIDACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Cariniana legalis', 'Jequitibá-rosa', 'Lecythidaceae', 'Cariniana', 'CAR-LEG', CURRENT_TIMESTAMP),
('Cariniana estrellensis', 'Jequitibá-branco', 'Lecythidaceae', 'Cariniana', 'CAR-EST', CURRENT_TIMESTAMP),
('Bertholletia excelsa', 'Castanheira-do-pará', 'Lecythidaceae', 'Bertholletia', 'BER-EXC', CURRENT_TIMESTAMP),
('Couratari guianensis', 'Tauari', 'Lecythidaceae', 'Couratari', 'COU-GUI', CURRENT_TIMESTAMP);

-- FAMÍLIA VOCHYSIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Vochysia tucanorum', 'Cinzeiro', 'Vochysiaceae', 'Vochysia', 'VOC-TUC', CURRENT_TIMESTAMP),
('Qualea grandiflora', 'Pau-terra', 'Vochysiaceae', 'Qualea', 'QUA-GRA', CURRENT_TIMESTAMP),
('Callisthene fasciculata', 'Carvoeiro', 'Vochysiaceae', 'Callisthene', 'CAL-FAS', CURRENT_TIMESTAMP);

-- FAMÍLIA BORAGINACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Cordia trichotoma', 'Louro-pardo', 'Boraginaceae', 'Cordia', 'COR-TRI', CURRENT_TIMESTAMP),
('Cordia superba', 'Babosa-branca', 'Boraginaceae', 'Cordia', 'COR-SUP', CURRENT_TIMESTAMP);

-- FAMÍLIA ARAUCARIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Araucaria angustifolia', 'Araucária', 'Araucariaceae', 'Araucaria', 'ARA-ANG', CURRENT_TIMESTAMP);

-- FAMÍLIA PODOCARPACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Podocarpus lambertii', 'Pinheiro-bravo', 'Podocarpaceae', 'Podocarpus', 'POD-LAM', CURRENT_TIMESTAMP);

-- FAMÍLIA AQUIFOLIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Ilex paraguariensis', 'Erva-mate', 'Aquifoliaceae', 'Ilex', 'ILE-PAR', CURRENT_TIMESTAMP),
('Ilex theezans', 'Caúna', 'Aquifoliaceae', 'Ilex', 'ILE-THE', CURRENT_TIMESTAMP);

-- FAMÍLIA ROSACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Prunus myrtifolia', 'Pessegueiro-do-mato', 'Rosaceae', 'Prunus', 'PRU-MYR', CURRENT_TIMESTAMP);

-- FAMÍLIA SALICACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Casearia sylvestris', 'Guaçatonga', 'Salicaceae', 'Casearia', 'CAS-SYL', CURRENT_TIMESTAMP),
('Casearia decandra', 'Cafezeiro-do-mato', 'Salicaceae', 'Casearia', 'CAS-DEC', CURRENT_TIMESTAMP);

-- FAMÍLIA PROTEACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Roupala montana', 'Carne-de-vaca', 'Proteaceae', 'Roupala', 'ROU-MON', CURRENT_TIMESTAMP),
('Euplassa cantareirae', 'Carvalho-brasileiro', 'Proteaceae', 'Euplassa', 'EUP-CAN', CURRENT_TIMESTAMP);

-- FAMÍLIA RHAMNACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Hovenia dulcis', 'Uva-do-japão', 'Rhamnaceae', 'Hovenia', 'HOV-DUL', CURRENT_TIMESTAMP),
('Colubrina glandulosa', 'Saguaraji', 'Rhamnaceae', 'Colubrina', 'COL-GLA', CURRENT_TIMESTAMP);

-- FAMÍLIA CANNABACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Trema micrantha', 'Pau-pólvora', 'Cannabaceae', 'Trema', 'TRE-MIC', CURRENT_TIMESTAMP),
('Celtis iguanaea', 'Jameri', 'Cannabaceae', 'Celtis', 'CEL-IGU', CURRENT_TIMESTAMP);

-- FAMÍLIA URTICACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Cecropia pachystachya', 'Embaúba', 'Urticaceae', 'Cecropia', 'CEC-PAC', CURRENT_TIMESTAMP),
('Cecropia hololeuca', 'Embaúba-prateada', 'Urticaceae', 'Cecropia', 'CEC-HOL', CURRENT_TIMESTAMP);

-- FAMÍLIA ANNONACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Annona cacans', 'Araticum-cagão', 'Annonaceae', 'Annona', 'ANN-CAC', CURRENT_TIMESTAMP),
('Annona glabra', 'Araticum-do-brejo', 'Annonaceae', 'Annona', 'ANN-GLA', CURRENT_TIMESTAMP),
('Rollinia sylvatica', 'Araticum', 'Annonaceae', 'Rollinia', 'ROL-SYL', CURRENT_TIMESTAMP),
('Xylopia aromatica', 'Pimenta-de-macaco', 'Annonaceae', 'Xylopia', 'XYL-ARO', CURRENT_TIMESTAMP);

-- FAMÍLIA CLUSIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Garcinia gardneriana', 'Bacupari', 'Clusiaceae', 'Garcinia', 'GAR-GAR', CURRENT_TIMESTAMP),
('Calophyllum brasiliense', 'Guanandi', 'Clusiaceae', 'Calophyllum', 'CAL-BRA', CURRENT_TIMESTAMP);

-- FAMÍLIA COMBRETACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Terminalia argentea', 'Capitão-do-campo', 'Combretaceae', 'Terminalia', 'TER-ARG', CURRENT_TIMESTAMP),
('Terminalia triflora', 'Capitão', 'Combretaceae', 'Terminalia', 'TER-TRI', CURRENT_TIMESTAMP);

-- FAMÍLIA MYRISTICACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Virola sebifera', 'Ucuúba', 'Myristicaceae', 'Virola', 'VIR-SEB', CURRENT_TIMESTAMP);

-- FAMÍLIA PIPERACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Piper aduncum', 'Pimenta-de-macaco', 'Piperaceae', 'Piper', 'PIP-ADU', CURRENT_TIMESTAMP),
('Piper arboreum', 'Pariparoba', 'Piperaceae', 'Piper', 'PIP-ARB', CURRENT_TIMESTAMP);

-- FAMÍLIA PRIMULACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Myrsine coriacea', 'Capororoca', 'Primulaceae', 'Myrsine', 'MYR-COR', CURRENT_TIMESTAMP),
('Myrsine umbellata', 'Capororoca-graúda', 'Primulaceae', 'Myrsine', 'MYR-UMB', CURRENT_TIMESTAMP);

-- FAMÍLIA ERYTHROXYLACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Erythroxylum deciduum', 'Cocão', 'Erythroxylaceae', 'Erythroxylum', 'ERY-DEC', CURRENT_TIMESTAMP);

-- FAMÍLIA SIPARUNACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Siparuna guianensis', 'Negramina', 'Siparunaceae', 'Siparuna', 'SIP-GUI', CURRENT_TIMESTAMP);

-- FAMÍLIA VERBENACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Aloysia virgata', 'Lixeira', 'Verbenaceae', 'Aloysia', 'ALO-VIR', CURRENT_TIMESTAMP),
('Citharexylum myrianthum', 'Pau-viola', 'Verbenaceae', 'Citharexylum', 'CIT-MYR', CURRENT_TIMESTAMP);

-- FAMÍLIA WINTERACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Drimys brasiliensis', 'Casca-d''anta', 'Winteraceae', 'Drimys', 'DRI-BRA', CURRENT_TIMESTAMP);

-- FAMÍLIA CELASTRACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Maytenus ilicifolia', 'Espinheira-santa', 'Celastraceae', 'Maytenus', 'MAY-ILI', CURRENT_TIMESTAMP),
('Maytenus robusta', 'Cafezinho-do-mato', 'Celastraceae', 'Maytenus', 'MAY-ROB', CURRENT_TIMESTAMP);

-- FAMÍLIA POLYGONACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Triplaris americana', 'Pau-formiga', 'Polygonaceae', 'Triplaris', 'TRI-AME', CURRENT_TIMESTAMP);

-- FAMÍLIA CACTACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Cereus jamacaru', 'Mandacaru', 'Cactaceae', 'Cereus', 'CER-JAM', CURRENT_TIMESTAMP),
('Pilosocereus gounellei', 'Xique-xique', 'Cactaceae', 'Pilosocereus', 'PIL-GOU', CURRENT_TIMESTAMP);

-- FAMÍLIA BURSERACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Protium heptaphyllum', 'Almecegueira', 'Burseraceae', 'Protium', 'PRO-HEP', CURRENT_TIMESTAMP);

-- FAMÍLIA DIPTEROCARPACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Qualea multiflora', 'Pau-terra-da-folha-larga', 'Vochysiaceae', 'Qualea', 'QUA-MUL', CURRENT_TIMESTAMP);

-- FAMÍLIA ELAEOCARPACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Sloanea monosperma', 'Sapopema', 'Elaeocarpaceae', 'Sloanea', 'SLO-MON', CURRENT_TIMESTAMP);

-- FAMÍLIA HUMIRIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Vantanea compacta', 'Uxi-liso', 'Humiriaceae', 'Vantanea', 'VAN-COM', CURRENT_TIMESTAMP);

-- FAMÍLIA RUBIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Genipa americana', 'Jenipapo', 'Rubiaceae', 'Genipa', 'GEN-AME', CURRENT_TIMESTAMP),
('Psychotria vellosiana', 'Café-do-mato', 'Rubiaceae', 'Psychotria', 'PSY-VEL', CURRENT_TIMESTAMP),
('Rudgea jasminoides', 'Véu-de-noiva', 'Rubiaceae', 'Rudgea', 'RUD-JAS', CURRENT_TIMESTAMP),
('Randia armata', 'Limãozinho', 'Rubiaceae', 'Randia', 'RAN-ARM', CURRENT_TIMESTAMP);

-- FAMÍLIA BROMELIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Ananas comosus', 'Abacaxizeiro', 'Bromeliaceae', 'Ananas', 'ANA-COM', CURRENT_TIMESTAMP),
('Bromelia antiacantha', 'Gravatá', 'Bromeliaceae', 'Bromelia', 'BRO-ANT', CURRENT_TIMESTAMP),
('Tillandsia usneoides', 'Barba-de-pau', 'Bromeliaceae', 'Tillandsia', 'TIL-USN', CURRENT_TIMESTAMP);

-- FAMÍLIA ASTERACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Baccharis dracunculifolia', 'Alecrim-do-campo', 'Asteraceae', 'Baccharis', 'BAC-DRA', CURRENT_TIMESTAMP),
('Baccharis trimera', 'Carqueja', 'Asteraceae', 'Baccharis', 'BAC-TRI', CURRENT_TIMESTAMP),
('Vernonia polyanthes', 'Assa-peixe', 'Asteraceae', 'Vernonia', 'VER-POL', CURRENT_TIMESTAMP),
('Mikania glomerata', 'Guaco', 'Asteraceae', 'Mikania', 'MIK-GLO', CURRENT_TIMESTAMP);

-- FAMÍLIA ORCHIDACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Cattleya labiata', 'Orquídea-labiata', 'Orchidaceae', 'Cattleya', 'CAT-LAB', CURRENT_TIMESTAMP),
('Oncidium flexuosum', 'Chuva-de-ouro', 'Orchidaceae', 'Oncidium', 'ONC-FLE', CURRENT_TIMESTAMP),
('Epidendrum fulgens', 'Orquídea-vermelha', 'Orchidaceae', 'Epidendrum', 'EPI-FUL', CURRENT_TIMESTAMP),
('Vanilla planifolia', 'Baunilha', 'Orchidaceae', 'Vanilla', 'VAN-PLA', CURRENT_TIMESTAMP);

-- FAMÍLIA PASSIFLORACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Passiflora edulis', 'Maracujá-azedo', 'Passifloraceae', 'Passiflora', 'PAS-EDU', CURRENT_TIMESTAMP),
('Passiflora alata', 'Maracujá-doce', 'Passifloraceae', 'Passiflora', 'PAS-ALA', CURRENT_TIMESTAMP),
('Passiflora cincinnata', 'Maracujá-do-mato', 'Passifloraceae', 'Passiflora', 'PAS-CIN', CURRENT_TIMESTAMP);

-- FAMÍLIA CARICACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Carica papaya', 'Mamão', 'Caricaceae', 'Carica', 'CAR-PAP', CURRENT_TIMESTAMP),
('Jacaratia spinosa', 'Jaracatiá', 'Caricaceae', 'Jacaratia', 'JAC-SPI', CURRENT_TIMESTAMP);

-- FAMÍLIA MUSACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Musa paradisiaca', 'Bananeira', 'Musaceae', 'Musa', 'MUS-PAR', CURRENT_TIMESTAMP);

-- FAMÍLIA HELICONIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Heliconia bihai', 'Bico-de-papagaio', 'Heliconiaceae', 'Heliconia', 'HEL-BIH', CURRENT_TIMESTAMP),
('Heliconia rostrata', 'Helicônia-papagaio', 'Heliconiaceae', 'Heliconia', 'HEL-ROS', CURRENT_TIMESTAMP);

-- FAMÍLIA ZINGIBERACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Alpinia zerumbet', 'Colônia', 'Zingiberaceae', 'Alpinia', 'ALP-ZER', CURRENT_TIMESTAMP),
('Zingiber officinale', 'Gengibre', 'Zingiberaceae', 'Zingiber', 'ZIN-OFF', CURRENT_TIMESTAMP);

-- FAMÍLIA COSTACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Costus spiralis', 'Cana-do-brejo', 'Costaceae', 'Costus', 'COS-SPI', CURRENT_TIMESTAMP);

-- FAMÍLIA CYATHEACEAE (Samambaias arborescentes)
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Cyathea delgadii', 'Samambaiaçu', 'Cyatheaceae', 'Cyathea', 'CYA-DEL', CURRENT_TIMESTAMP),
('Alsophila setosa', 'Xaxim', 'Cyatheaceae', 'Alsophila', 'ALS-SET', CURRENT_TIMESTAMP);

-- FAMÍLIA DILLENIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Curatella americana', 'Lixeira', 'Dilleniaceae', 'Curatella', 'CUR-AME', CURRENT_TIMESTAMP),
('Davilla rugosa', 'Cipó-caboclo', 'Dilleniaceae', 'Davilla', 'DAV-RUG', CURRENT_TIMESTAMP);

-- FAMÍLIA PHYTOLACCACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Gallesia integrifolia', 'Pau-d''alho', 'Phytolaccaceae', 'Gallesia', 'GAL-INT', CURRENT_TIMESTAMP),
('Seguieria langsdorffii', 'Limão-bravo', 'Phytolaccaceae', 'Seguieria', 'SEG-LAN', CURRENT_TIMESTAMP);

-- FAMÍLIA CUNONIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Lamanonia ternata', 'Guaraperê', 'Cunoniaceae', 'Lamanonia', 'LAM-TER', CURRENT_TIMESTAMP),
('Weinmannia paulliniifolia', 'Gramimunha', 'Cunoniaceae', 'Weinmannia', 'WEI-PAU', CURRENT_TIMESTAMP);

-- FAMÍLIA STYRACACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Styrax camporum', 'Benjoeiro-do-campo', 'Styracaceae', 'Styrax', 'STY-CAM', CURRENT_TIMESTAMP),
('Styrax pohlii', 'Benjoeiro', 'Styracaceae', 'Styrax', 'STY-POH', CURRENT_TIMESTAMP);

-- FAMÍLIA SYMPLOCACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Symplocos tetrandra', 'Maria-mole-peluda', 'Symplocaceae', 'Symplocos', 'SYM-TET', CURRENT_TIMESTAMP);

-- FAMÍLIA THYMELAEACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Daphnopsis fasciculata', 'Embira-branca', 'Thymelaeaceae', 'Daphnopsis', 'DAP-FAS', CURRENT_TIMESTAMP);

-- FAMÍLIA LOGANIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Strychnos brasiliensis', 'Salta-martim', 'Loganiaceae', 'Strychnos', 'STR-BRA', CURRENT_TIMESTAMP);

-- FAMÍLIA MALPIGHIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Byrsonima basiloba', 'Murici-vermelho', 'Malpighiaceae', 'Byrsonima', 'BYR-BAS', CURRENT_TIMESTAMP),
('Byrsonima crassifolia', 'Murici', 'Malpighiaceae', 'Byrsonima', 'BYR-CRA', CURRENT_TIMESTAMP),
('Malpighia glabra', 'Acerola', 'Malpighiaceae', 'Malpighia', 'MAL-GLA', CURRENT_TIMESTAMP);

-- FAMÍLIA CHRYSOBALANACEAE (continuação)
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Hirtella hebeclada', 'Macuqueiro', 'Chrysobalanaceae', 'Hirtella', 'HIR-HEB', CURRENT_TIMESTAMP);

-- FAMÍLIA HUMIRIACEAE (continuação)
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Humiria balsamifera', 'Umiri', 'Humiriaceae', 'Humiria', 'HUM-BAL', CURRENT_TIMESTAMP);

-- FAMÍLIA ERICACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Gaylussacia brasiliensis', 'Camarinha', 'Ericaceae', 'Gaylussacia', 'GAY-BRA', CURRENT_TIMESTAMP);

-- FAMÍLIA CARYOCARACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Caryocar brasiliense', 'Pequi', 'Caryocaraceae', 'Caryocar', 'CAR-BRA', CURRENT_TIMESTAMP),
('Caryocar villosum', 'Piquiá', 'Caryocaraceae', 'Caryocar', 'CAR-VIL', CURRENT_TIMESTAMP);

-- FAMÍLIA EBENACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Diospyros inconstans', 'Marmelinho-do-mato', 'Ebenaceae', 'Diospyros', 'DIO-INC', CURRENT_TIMESTAMP);

-- FAMÍLIA CANNABACEAE (continuação)
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Celtis brasiliensis', 'Taleira', 'Cannabaceae', 'Celtis', 'CEL-BRA', CURRENT_TIMESTAMP);

-- FAMÍLIA ARECACEAE (continuação)
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Astrocaryum aculeatissimum', 'Brejaúva', 'Arecaceae', 'Astrocaryum', 'AST-ACU', CURRENT_TIMESTAMP),
('Geonoma schottiana', 'Guaricanga', 'Arecaceae', 'Geonoma', 'GEO-SCH', CURRENT_TIMESTAMP);

-- FAMÍLIA ICACINACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Citronella paniculata', 'Congonha', 'Icacinaceae', 'Citronella', 'CIT-PAN', CURRENT_TIMESTAMP);

-- FAMÍLIA MONIMIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Mollinedia schottiana', 'Capituva', 'Monimiaceae', 'Mollinedia', 'MOL-SCH', CURRENT_TIMESTAMP);

-- FAMÍLIA OCHNACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Ouratea castaneifolia', 'Farinha-seca', 'Ochnaceae', 'Ouratea', 'OUR-CAS', CURRENT_TIMESTAMP);

-- FAMÍLIA OLACACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Heisteria silvianii', 'Congonha-do-bugre', 'Olacaceae', 'Heisteria', 'HEI-SIL', CURRENT_TIMESTAMP);

-- FAMÍLIA PERACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Pera glabrata', 'Tabocuva', 'Peraceae', 'Pera', 'PER-GLA', CURRENT_TIMESTAMP);

-- FAMÍLIA CECROPIA (continuação Urticaceae)
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Cecropia glaziovii', 'Embaúba-vermelha', 'Urticaceae', 'Cecropia', 'CEC-GLA', CURRENT_TIMESTAMP);

-- FAMÍLIA ELAEOCARPACEAE (continuação)
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Sloanea guianensis', 'Sapopema-do-litoral', 'Elaeocarpaceae', 'Sloanea', 'SLO-GUI', CURRENT_TIMESTAMP);

-- FAMÍLIA SIMAROUBACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Simarouba amara', 'Marupá', 'Simaroubaceae', 'Simarouba', 'SIM-AMA', CURRENT_TIMESTAMP),
('Picramnia ramiflora', 'Pau-amargo', 'Simaroubaceae', 'Picramnia', 'PIC-RAM', CURRENT_TIMESTAMP);

-- FAMÍLIA ERYTHROXYLACEAE (continuação)
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Erythroxylum pelleterianum', 'Fruta-de-pomba', 'Erythroxylaceae', 'Erythroxylum', 'ERY-PEL', CURRENT_TIMESTAMP);

-- FAMÍLIA AQUIFOLIACEAE (continuação)
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Ilex dumosa', 'Caúna-mirim', 'Aquifoliaceae', 'Ilex', 'ILE-DUM', CURRENT_TIMESTAMP);

-- FAMÍLIA LACISTEMATACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Lacistema hasslerianum', 'Café-de-anta', 'Lacistemataceae', 'Lacistema', 'LAC-HAS', CURRENT_TIMESTAMP);

-- FAMÍLIA LYTHRACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Lafoensia pacari', 'Dedaleiro', 'Lythraceae', 'Lafoensia', 'LAF-PAC', CURRENT_TIMESTAMP),
('Physocalymma scaberrimum', 'Resedá-do-mato', 'Lythraceae', 'Physocalymma', 'PHY-SCA', CURRENT_TIMESTAMP);

-- FAMÍLIA POLYGONACEAE (continuação)
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Ruprechtia laxiflora', 'Farinha-seca', 'Polygonaceae', 'Ruprechtia', 'RUP-LAX', CURRENT_TIMESTAMP),
('Coccoloba mollis', 'Uveira-do-mato', 'Polygonaceae', 'Coccoloba', 'COC-MOL', CURRENT_TIMESTAMP);

-- FAMÍLIA SABIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Meliosma sellowii', 'Pau-de-viola', 'Sabiaceae', 'Meliosma', 'MEL-SEL', CURRENT_TIMESTAMP);

-- FAMÍLIA CLETHRACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Clethra scabra', 'Carne-de-vaca', 'Clethraceae', 'Clethra', 'CLE-SCA', CURRENT_TIMESTAMP);

-- FAMÍLIA CALOPHYLLACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Kielmeyera coriacea', 'Pau-santo', 'Calophyllaceae', 'Kielmeyera', 'KIE-COR', CURRENT_TIMESTAMP);

-- FAMÍLIA THEACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Laplacea fruticosa', 'Nhapindá', 'Theaceae', 'Laplacea', 'LAP-FRU', CURRENT_TIMESTAMP);

-- FAMÍLIA SANTALACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Phoradendron piperoides', 'Erva-de-passarinho', 'Santalaceae', 'Phoradendron', 'PHO-PIP', CURRENT_TIMESTAMP);

-- FAMÍLIA CONNARACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Connarus suberosus', 'Barbatimão-de-folha-miúda', 'Connaraceae', 'Connarus', 'CON-SUB', CURRENT_TIMESTAMP);

-- FAMÍLIA CORIARIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Coriaria ruscifolia', 'Sangue-de-cristo', 'Coriariaceae', 'Coriaria', 'COR-RUS', CURRENT_TIMESTAMP);

-- FAMÍLIA EUPHORBIACEAE (continuação)
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Maprounea guianensis', 'Marmelinho', 'Euphorbiaceae', 'Maprounea', 'MAP-GUI', CURRENT_TIMESTAMP),
('Actinostemon concolor', 'Laranjeira-do-mato', 'Euphorbiaceae', 'Actinostemon', 'ACT-CON', CURRENT_TIMESTAMP),
('Sebastiania commersoniana', 'Branquilho', 'Euphorbiaceae', 'Sebastiania', 'SEB-COM', CURRENT_TIMESTAMP);

-- FAMÍLIA PRIMULACEAE (continuação)
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Myrsine guianensis', 'Capororoca-vermelha', 'Primulaceae', 'Myrsine', 'MYR-GUI', CURRENT_TIMESTAMP);

-- FAMÍLIA POLYGALACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Bredemeyera floribunda', 'Cipó-suma', 'Polygalaceae', 'Bredemeyera', 'BRE-FLO', CURRENT_TIMESTAMP);

-- FAMÍLIA VELLOZIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Vellozia compacta', 'Canela-de-ema', 'Velloziaceae', 'Vellozia', 'VEL-COM', CURRENT_TIMESTAMP);

-- FAMÍLIA PROTEACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Roupala brasiliensis', 'Carvalho-brasileiro', 'Proteaceae', 'Roupala', 'ROU-BRA', CURRENT_TIMESTAMP);

INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Avicennia germinans', 'Mangue-preto', 'Acanthaceae', 'Avicennia', 'AVI-GER', CURRENT_TIMESTAMP),
('Avicennia schaueriana', 'Mangue-canoé', 'Acanthaceae', 'Avicennia', 'AVI-SCH', CURRENT_TIMESTAMP);

-- FAMÍLIA AMARANTHACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Pfaffia glomerata', 'Ginseng-brasileiro', 'Amaranthaceae', 'Pfaffia', 'PFA-GLO', CURRENT_TIMESTAMP);

-- FAMÍLIA ANACARDIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Lithraea molleoides', 'Aroeira-brava', 'Anacardiaceae', 'Lithraea', 'LIT-MOL', CURRENT_TIMESTAMP),
('Mangifera indica', 'Mangueira', 'Anacardiaceae', 'Mangifera', 'MAN-IND', CURRENT_TIMESTAMP),
('Spondias dulcis', 'Cajá-manga', 'Anacardiaceae', 'Spondias', 'SPO-DUL', CURRENT_TIMESTAMP),
('Spondias purpurea', 'Ciriguela', 'Anacardiaceae', 'Spondias', 'SPO-PUR', CURRENT_TIMESTAMP),
('Tapirira guianensis', 'Pau-pombo', 'Anacardiaceae', 'Tapirira', 'TAP-GUI', CURRENT_TIMESTAMP);

-- FAMÍLIA ANNONACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Annona crassiflora', 'Araticum-do-cerrado', 'Annonaceae', 'Annona', 'ANN-CRA', CURRENT_TIMESTAMP),
('Annona montana', 'Araticum-manso', 'Annonaceae', 'Annona', 'ANN-MON', CURRENT_TIMESTAMP),
('Annona mucosa', 'Biribá', 'Annonaceae', 'Annona', 'ANN-MUC', CURRENT_TIMESTAMP),
('Annona muricata', 'Graviola', 'Annonaceae', 'Annona', 'ANN-MUR', CURRENT_TIMESTAMP),
('Annona reticulata', 'Condessa', 'Annonaceae', 'Annona', 'ANN-RET', CURRENT_TIMESTAMP),
('Annona squamosa', 'Pinha', 'Annonaceae', 'Annona', 'ANN-SQU', CURRENT_TIMESTAMP),
('Duguetia lanceolata', 'Pindaíba', 'Annonaceae', 'Duguetia', 'DUG-LAN', CURRENT_TIMESTAMP),
('Guatteria nigrescens', 'Pindaúva-preta', 'Annonaceae', 'Guatteria', 'GUA-NIG', CURRENT_TIMESTAMP);

-- FAMÍLIA APOCYNACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Aspidosperma cylindrocarpon', 'Peroba-poca', 'Apocynaceae', 'Aspidosperma', 'ASP-CYL', CURRENT_TIMESTAMP),
('Aspidosperma macrocarpon', 'Peroba-do-campo', 'Apocynaceae', 'Aspidosperma', 'ASP-MAC', CURRENT_TIMESTAMP),
('Aspidosperma parvifolium', 'Guatambu-de-sapo', 'Apocynaceae', 'Aspidosperma', 'ASP-PAR', CURRENT_TIMESTAMP),
('Aspidosperma tomentosum', 'Guatambu-vermelho', 'Apocynaceae', 'Aspidosperma', 'ASP-TOM', CURRENT_TIMESTAMP),
('Hancornia speciosa', 'Mangabeira', 'Apocynaceae', 'Hancornia', 'HAN-SPE', CURRENT_TIMESTAMP),
('Himatanthus drasticus', 'Janaguba', 'Apocynaceae', 'Himatanthus', 'HIM-DRA', CURRENT_TIMESTAMP);

-- FAMÍLIA ARECACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Archontophoenix alexandrae', 'Palmeira-real', 'Arecaceae', 'Archontophoenix', 'ARC-ALE', CURRENT_TIMESTAMP),
('Attalea phalerata', 'Acuri', 'Arecaceae', 'Attalea', 'ATT-PHA', CURRENT_TIMESTAMP),
('Butia capitata', 'Butiá', 'Arecaceae', 'Butia', 'BUT-CAP', CURRENT_TIMESTAMP),
('Cocos nucifera', 'Coqueiro', 'Arecaceae', 'Cocos', 'COC-NUC', CURRENT_TIMESTAMP),
('Dypsis lutescens', 'Areca-bambu', 'Arecaceae', 'Dypsis', 'DYP-LUT', CURRENT_TIMESTAMP),
('Elaeis guineensis', 'Dendezeiro', 'Arecaceae', 'Elaeis', 'ELA-GUI', CURRENT_TIMESTAMP),
('Oenocarpus bacaba', 'Bacaba', 'Arecaceae', 'Oenocarpus', 'OEN-BAC', CURRENT_TIMESTAMP),
('Oenocarpus bataua', 'Patauá', 'Arecaceae', 'Oenocarpus', 'OEN-BAT', CURRENT_TIMESTAMP),
('Phoenix roebelenii', 'Tamareira-de-jardim', 'Arecaceae', 'Phoenix', 'PHO-ROE', CURRENT_TIMESTAMP),
('Roystonea oleracea', 'Palmeira-imperial', 'Arecaceae', 'Roystonea', 'ROY-OLE', CURRENT_TIMESTAMP);

-- FAMÍLIA BIGNONIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Handroanthus chrysotrichus', 'Ipê-amarelo-cascudo', 'Bignoniaceae', 'Handroanthus', 'HAN-CHR', CURRENT_TIMESTAMP),
('Handroanthus ochraceus', 'Ipê-amarelo-do-cerrado', 'Bignoniaceae', 'Handroanthus', 'HAN-OCH', CURRENT_TIMESTAMP),
('Handroanthus serratifolius', 'Ipê-amarelo-da-amazônia', 'Bignoniaceae', 'Handroanthus', 'HAN-SER', CURRENT_TIMESTAMP),
('Jacaranda caroba', 'Caroba', 'Bignoniaceae', 'Jacaranda', 'JAC-CAR', CURRENT_TIMESTAMP),
('Jacaranda cuspidifolia', 'Jacarandá-de-minas', 'Bignoniaceae', 'Jacaranda', 'JAC-CUS', CURRENT_TIMESTAMP),
('Sparattosperma leucanthum', 'Ipê-branco-liso', 'Bignoniaceae', 'Sparattosperma', 'SPA-LEU', CURRENT_TIMESTAMP),
('Spathodea campanulata', 'Espatódea', 'Bignoniaceae', 'Spathodea', 'SPA-CAM', CURRENT_TIMESTAMP),
('Tabebuia roseoalba', 'Ipê-branco-do-cerrado', 'Bignoniaceae', 'Tabebuia', 'TAB-ROS', CURRENT_TIMESTAMP),
('Tecoma stans', 'Ipê-de-jardim', 'Bignoniaceae', 'Tecoma', 'TEC-STA', CURRENT_TIMESTAMP);

-- FAMÍLIA BURSERACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Bursera leptophloeos', 'Imburana-de-espinho', 'Burseraceae', 'Bursera', 'BUR-LEP', CURRENT_TIMESTAMP),
('Commiphora leptophloeos', 'Imburana-de-cambão', 'Burseraceae', 'Commiphora', 'COM-LEP', CURRENT_TIMESTAMP),
('Protium icicariba', 'Icicariba', 'Burseraceae', 'Protium', 'PRO-ICI', CURRENT_TIMESTAMP),
('Protium widgrenii', 'Almecegueira-de-casca-lisa', 'Burseraceae', 'Protium', 'PRO-WID', CURRENT_TIMESTAMP);

-- FAMÍLIA CALOPHYLLACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Kielmeyera lathrophyton', 'Pau-santo-do-cerrado', 'Calophyllaceae', 'Kielmeyera', 'KIE-LAT', CURRENT_TIMESTAMP);

-- FAMÍLIA CLUSIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Clusia criuva', 'Mangue-bravo', 'Clusiaceae', 'Clusia', 'CLU-CRI', CURRENT_TIMESTAMP),
('Clusia fluminensis', 'Abaneiro', 'Clusiaceae', 'Clusia', 'CLU-FLU', CURRENT_TIMESTAMP),
('Platonia insignis', 'Bacurizeiro', 'Clusiaceae', 'Platonia', 'PLA-INS', CURRENT_TIMESTAMP),
('Rheedia brasiliensis', 'Bacupari-miúdo', 'Clusiaceae', 'Rheedia', 'RHE-BRA', CURRENT_TIMESTAMP);

-- FAMÍLIA COMBRETACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Buchenavia tomentosa', 'Mirindiba', 'Combretaceae', 'Buchenavia', 'BUC-TOM', CURRENT_TIMESTAMP),
('Combretum leprosum', 'Mofumbo', 'Combretaceae', 'Combretum', 'COM-LEP', CURRENT_TIMESTAMP),
('Conocarpus erectus', 'Mangue-de-botão', 'Combretaceae', 'Conocarpus', 'CON-ERE', CURRENT_TIMESTAMP),
('Laguncularia racemosa', 'Mangue-branco', 'Combretaceae', 'Laguncularia', 'LAG-RAC', CURRENT_TIMESTAMP),
('Terminalia catappa', 'Amendoeira-da-praia', 'Combretaceae', 'Terminalia', 'TER-CAT', CURRENT_TIMESTAMP);

-- FAMÍLIA FABACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Amburana cearensis', 'Cerejeira', 'Fabaceae', 'Amburana', 'AMB-CEA', CURRENT_TIMESTAMP),
('Andira fraxinifolia', 'Angelim-doce', 'Fabaceae', 'Andira', 'AND-FRA', CURRENT_TIMESTAMP),
('Andira inermis', 'Angelim-amargoso', 'Fabaceae', 'Andira', 'AND-INE', CURRENT_TIMESTAMP),
('Apuleia leiocarpa', 'Grápia', 'Fabaceae', 'Apuleia', 'APU-LEI', CURRENT_TIMESTAMP),
('Bowdichia virgilioides', 'Sucupira-preta', 'Fabaceae', 'Bowdichia', 'BOW-VIR', CURRENT_TIMESTAMP),
('Centrolobium tomentosum', 'Araribá', 'Fabaceae', 'Centrolobium', 'CEN-TOM', CURRENT_TIMESTAMP),
('Delonix regia', 'Flamboyant', 'Fabaceae', 'Delonix', 'DEL-REG', CURRENT_TIMESTAMP),
('Dipteryx alata', 'Baru', 'Fabaceae', 'Dipteryx', 'DIP-ALA', CURRENT_TIMESTAMP),
('Dipteryx odorata', 'Cumaru', 'Fabaceae', 'Dipteryx', 'DIP-ODO', CURRENT_TIMESTAMP),
('Lonchocarpus muehlbergianus', 'Embira-de-sapo', 'Fabaceae', 'Lonchocarpus', 'LON-MUE', CURRENT_TIMESTAMP),
('Machaerium aculeatum', 'Jacarandá-de-espinho', 'Fabaceae', 'Machaerium', 'MAC-ACU', CURRENT_TIMESTAMP),
('Machaerium scleroxylon', 'Caviúna', 'Fabaceae', 'Machaerium', 'MAC-SCL', CURRENT_TIMESTAMP),
('Myrocarpus frondosus', 'Cabreúva', 'Fabaceae', 'Myrocarpus', 'MYR-FRO', CURRENT_TIMESTAMP),
('Ormosia arborea', 'Olho-de-cabra', 'Fabaceae', 'Ormosia', 'ORM-ARB', CURRENT_TIMESTAMP),
('Platymiscium floribundum', 'Sacambu', 'Fabaceae', 'Platymiscium', 'PLA-FLO', CURRENT_TIMESTAMP),
('Pterodon emarginatus', 'Sucupira-branca', 'Fabaceae', 'Pterodon', 'PTE-EMA', CURRENT_TIMESTAMP),
('Stryphnodendron adstringens', 'Barbatimão', 'Fabaceae', 'Stryphnodendron', 'STR-ADS', CURRENT_TIMESTAMP),
('Sweetia fruticosa', 'Sucupira-amarela', 'Fabaceae', 'Sweetia', 'SWE-FRU', CURRENT_TIMESTAMP),
('Tachigali aurea', 'Carvoeiro-roxo', 'Fabaceae', 'Tachigali', 'TAC-AUR', CURRENT_TIMESTAMP),
('Tipuana tipu', 'Tipuana', 'Fabaceae', 'Tipuana', 'TIP-TIP', CURRENT_TIMESTAMP);

-- FAMÍLIA LAURACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Aniba rosaeodora', 'Pau-rosa', 'Lauraceae', 'Aniba', 'ANI-ROS', CURRENT_TIMESTAMP),
('Cryptocarya aschersoniana', 'Canela-fogo', 'Lauraceae', 'Cryptocarya', 'CRY-ASC', CURRENT_TIMESTAMP),
('Licaria armeniaca', 'Noz-moscada-do-Brasil', 'Lauraceae', 'Licaria', 'LIC-ARM', CURRENT_TIMESTAMP),
('Nectandra lanceolata', 'Canela-amarela', 'Lauraceae', 'Nectandra', 'NEC-LAN', CURRENT_TIMESTAMP),
('Nectandra oppositifolia', 'Canela-ferrugem', 'Lauraceae', 'Nectandra', 'NEC-OPP', CURRENT_TIMESTAMP),
('Ocotea catharinensis', 'Canela-preta', 'Lauraceae', 'Ocotea', 'OCO-CAT', CURRENT_TIMESTAMP),
('Ocotea odorifera', 'Canela-sassafrás', 'Lauraceae', 'Ocotea', 'OCO-ODO', CURRENT_TIMESTAMP),
('Persea pyrifolia', 'Maçaranduba', 'Lauraceae', 'Persea', 'PER-PYR', CURRENT_TIMESTAMP);

-- FAMÍLIA LECYTHIDACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Eschweilera ovata', 'Biriba', 'Lecythidaceae', 'Eschweilera', 'ESC-OVA', CURRENT_TIMESTAMP),
('Lecythis pisonis', 'Sapucaia', 'Lecythidaceae', 'Lecythis', 'LEC-PIS', CURRENT_TIMESTAMP);

-- FAMÍLIA MALVACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Apeiba tibourbou', 'Pente-de-macaco', 'Malvaceae', 'Apeiba', 'APE-TIB', CURRENT_TIMESTAMP),
('Basiloxylon brasiliensis', 'Pau-rei', 'Malvaceae', 'Basiloxylon', 'BAS-BRA', CURRENT_TIMESTAMP),
('Chorisia speciosa', 'Paineira-rosa', 'Malvaceae', 'Chorisia', 'CHO-SPE', CURRENT_TIMESTAMP),
('Durio zibethinus', 'Durian', 'Malvaceae', 'Durio', 'DUR-ZIB', CURRENT_TIMESTAMP),
('Heliocarpus americanus', 'Algodoeiro', 'Malvaceae', 'Heliocarpus', 'HEL-AME', CURRENT_TIMESTAMP),
('Hibiscus tiliaceus', 'Algodão-da-praia', 'Malvaceae', 'Hibiscus', 'HIB-TIL', CURRENT_TIMESTAMP),
('Pachira aquatica', 'Mamorana', 'Malvaceae', 'Pachira', 'PAC-AQU', CURRENT_TIMESTAMP),
('Sterculia striata', 'Chichá', 'Malvaceae', 'Sterculia', 'STE-STR', CURRENT_TIMESTAMP),
('Theobroma cacao', 'Cacaueiro', 'Malvaceae', 'Theobroma', 'THE-CAC', CURRENT_TIMESTAMP),
('Theobroma grandiflorum', 'Cupuaçu', 'Malvaceae', 'Theobroma', 'THE-GRA', CURRENT_TIMESTAMP);

-- FAMÍLIA MELIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Cedrela odorata', 'Cedro-rosa', 'Meliaceae', 'Cedrela', 'CED-ODO', CURRENT_TIMESTAMP),
('Melia azedarach', 'Cinamomo', 'Meliaceae', 'Melia', 'MEL-AZE', CURRENT_TIMESTAMP),
('Trichilia catigua', 'Catuaba', 'Meliaceae', 'Trichilia', 'TRI-CAT', CURRENT_TIMESTAMP),
('Trichilia clausseni', 'Catiguá', 'Meliaceae', 'Trichilia', 'TRI-CLA', CURRENT_TIMESTAMP),
('Trichilia pallida', 'Pau-ervilha', 'Meliaceae', 'Trichilia', 'TRI-PAL', CURRENT_TIMESTAMP);

-- FAMÍLIA MORACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Artocarpus heterophyllus', 'Jaqueira', 'Moraceae', 'Artocarpus', 'ART-HET', CURRENT_TIMESTAMP),
('Ficus benjamina', 'Ficus', 'Moraceae', 'Ficus', 'FIC-BEN', CURRENT_TIMESTAMP),
('Ficus elastica', 'Ficus-elástica', 'Moraceae', 'Ficus', 'FIC-ELA', CURRENT_TIMESTAMP),
('Ficus lyrata', 'Ficus-lira', 'Moraceae', 'Ficus', 'FIC-LYR', CURRENT_TIMESTAMP),
('Morus nigra', 'Amoreira-negra', 'Moraceae', 'Morus', 'MOR-NIG', CURRENT_TIMESTAMP),
('Sorocea bonplandii', 'Cincho', 'Moraceae', 'Sorocea', 'SOR-BON', CURRENT_TIMESTAMP);

-- FAMÍLIA MYRTACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Acca sellowiana', 'Goiaba-serrana', 'Myrtaceae', 'Acca', 'ACC-SEL', CURRENT_TIMESTAMP),
('Blepharocalyx salicifolius', 'Murta', 'Myrtaceae', 'Blepharocalyx', 'BLE-SAL', CURRENT_TIMESTAMP),
('Eucalyptus citriodora', 'Eucalipto-cheiroso', 'Myrtaceae', 'Eucalyptus', 'EUC-CIT', CURRENT_TIMESTAMP),
('Eucalyptus grandis', 'Eucalipto-grandis', 'Myrtaceae', 'Eucalyptus', 'EUC-GRA', CURRENT_TIMESTAMP),
('Eugenia dysenterica', 'Cagaita', 'Myrtaceae', 'Eugenia', 'EUG-DYS', CURRENT_TIMESTAMP),
('Eugenia involucrata', 'Cerejeira-do-mato', 'Myrtaceae', 'Eugenia', 'EUG-INV', CURRENT_TIMESTAMP),
('Eugenia stipitata', 'Araçá-boi', 'Myrtaceae', 'Eugenia', 'EUG-STI', CURRENT_TIMESTAMP),
('Myrcia bella', 'Cambuí', 'Myrtaceae', 'Myrcia', 'MYR-BEL', CURRENT_TIMESTAMP),
('Myrcianthes pungens', 'Guabiju', 'Myrtaceae', 'Myrcianthes', 'MYR-PUN', CURRENT_TIMESTAMP),
('Psidium longipetiolatum', 'Araçá-do-campo', 'Myrtaceae', 'Psidium', 'PSI-LON', CURRENT_TIMESTAMP),
('Syzygium jambos', 'Jambo', 'Myrtaceae', 'Syzygium', 'SYZ-JAM', CURRENT_TIMESTAMP);

-- FAMÍLIA OXALIDACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Averrhoa carambola', 'Carambola', 'Oxalidaceae', 'Averrhoa', 'AVE-CAR', CURRENT_TIMESTAMP);

-- FAMÍLIA PHYLLANTHACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Phyllanthus niruri', 'Quebra-pedra', 'Phyllanthaceae', 'Phyllanthus', 'PHY-NIR', CURRENT_TIMESTAMP);

-- FAMÍLIA RHIZOPHORACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Rhizophora mangle', 'Mangue-vermelho', 'Rhizophoraceae', 'Rhizophora', 'RHI-MAN', CURRENT_TIMESTAMP);

-- FAMÍLIA RUBIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Alibertia edulis', 'Marmelo', 'Rubiaceae', 'Alibertia', 'ALI-EDU', CURRENT_TIMESTAMP),
('Amaioua guianensis', 'Canela-de-veado', 'Rubiaceae', 'Amaioua', 'AMA-GUI', CURRENT_TIMESTAMP),
('Chomelia pohliana', 'Espinho-de-vintém', 'Rubiaceae', 'Chomelia', 'CHO-POH', CURRENT_TIMESTAMP),
('Coffea arabica', 'Cafeeiro-arábica', 'Rubiaceae', 'Coffea', 'COF-ARA', CURRENT_TIMESTAMP),
('Coffea canephora', 'Cafeeiro-robusta', 'Rubiaceae', 'Coffea', 'COF-CAN', CURRENT_TIMESTAMP),
('Cordiera sessilis', 'Marmelada-bola', 'Rubiaceae', 'Cordiera', 'COR-SES', CURRENT_TIMESTAMP),
('Palicourea rigida', 'Bate-caixa', 'Rubiaceae', 'Palicourea', 'PAL-RIG', CURRENT_TIMESTAMP),
('Posoqueria latifolia', 'Fruta-de-paraó', 'Rubiaceae', 'Posoqueria', 'POS-LAT', CURRENT_TIMESTAMP),
('Uncaria tomentosa', 'Unha-de-gato', 'Rubiaceae', 'Uncaria', 'UNC-TOM', CURRENT_TIMESTAMP);

-- FAMÍLIA SAPINDACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Dimocarpus longan', 'Longan', 'Sapindaceae', 'Dimocarpus', 'DIM-LON', CURRENT_TIMESTAMP),
('Litchi chinensis', 'Lichia', 'Sapindaceae', 'Litchi', 'LIT-CHI', CURRENT_TIMESTAMP),
('Nephelium lappaceum', 'Rambutan', 'Sapindaceae', 'Nephelium', 'NEP-LAP', CURRENT_TIMESTAMP);

-- FAMÍLIA SAPOTACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Chrysophyllum marginatum', 'Leiteiro', 'Sapotaceae', 'Chrysophyllum', 'CHR-MAR', CURRENT_TIMESTAMP),
('Chrysophyllum viride', 'Leiteiro-verde', 'Sapotaceae', 'Chrysophyllum', 'CHR-VIR', CURRENT_TIMESTAMP),
('Ecclinusa ramiflora', 'Guapeva', 'Sapotaceae', 'Ecclinusa', 'ECC-RAM', CURRENT_TIMESTAMP),
('Manilkara zapota', 'Sapoti', 'Sapotaceae', 'Manilkara', 'MAN-ZAP', CURRENT_TIMESTAMP),
('Pouteria lucuma', 'Lúcuma', 'Sapotaceae', 'Pouteria', 'POU-LUC', CURRENT_TIMESTAMP),
('Pouteria torta', 'Abiurana', 'Sapotaceae', 'Pouteria', 'POU-TOR', CURRENT_TIMESTAMP);

-- FAMÍLIA SOLANACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Brunfelsia uniflora', 'Manacá-de-cheiro', 'Solanaceae', 'Brunfelsia', 'BRU-UNI', CURRENT_TIMESTAMP);

-- FAMÍLIA VOCHYSIACEAE
INSERT INTO species_taxonomy (scientific_name, common_name, family, genus, code, created_at) VALUES
('Qualea parviflora', 'Pau-terra-miúdo', 'Vochysiaceae', 'Qualea', 'QUA-PAR', CURRENT_TIMESTAMP),
('Salvertia convallariodora', 'Pau-de-colher', 'Vochysiaceae', 'Salvertia', 'SAL-CON', CURRENT_TIMESTAMP),
('Vochysia divergens', 'Cambará', 'Vochysiaceae', 'Vochysia', 'VOC-DIV', CURRENT_TIMESTAMP);
