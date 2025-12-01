package br.com.inv.florestal.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.inv.florestal.api.dto.ImportMappingRequest;
import br.com.inv.florestal.api.dto.ImportResultResponse;
import br.com.inv.florestal.api.dto.ImportResultResponse.ImportError;
import br.com.inv.florestal.api.models.collection.Plot;
import br.com.inv.florestal.api.models.species.SpeciesTaxonomy;
import br.com.inv.florestal.api.models.specimen.SpecimenObject;
import br.com.inv.florestal.api.models.specimen.SpeciesInfo;
import br.com.inv.florestal.api.repository.PlotRepository;
import br.com.inv.florestal.api.repository.SpeciesTaxonomyRepository;
import br.com.inv.florestal.api.repository.SpecimenObjectRepository;
import br.com.inv.florestal.api.repository.SpeciesInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataImportService {

    private final SpecimenObjectRepository specimenRepository;
    private final SpeciesTaxonomyRepository speciesTaxonomyRepository;
    private final PlotRepository plotRepository;
    private final SpeciesInfoRepository speciesInfoRepository;

    @Transactional
    public ImportResultResponse importSpecimens(
        MultipartFile file, 
        ImportMappingRequest mapping
    ) throws IOException {
        
        long startTime = System.currentTimeMillis();
        
        log.info("Iniciando importação de espécimes");
        log.info("Arquivo: {}, Tamanho: {} bytes", file.getOriginalFilename(), file.getSize());
        log.info("Mapeamento: {}", mapping);
        
        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw new IllegalArgumentException("Arquivo sem nome");
        }

        List<Map<String, String>> rows;
        
        if (filename.endsWith(".csv")) {
            log.info("Processando arquivo CSV");
            rows = parseCSV(file.getInputStream(), mapping);
        } else if (filename.endsWith(".xlsx") || filename.endsWith(".xls")) {
            log.info("Processando arquivo Excel");
            rows = parseExcel(file.getInputStream(), mapping);
        } else {
            throw new IllegalArgumentException("Formato de arquivo não suportado. Use CSV ou XLSX");
        }

        log.info("Total de linhas extraídas: {}", rows.size());
        
        ImportResultResponse result = processSpecimenRows(rows, mapping);
        result.setExecutionTimeMs(System.currentTimeMillis() - startTime);
        
        log.info("Importação concluída: {} sucessos, {} erros em {}ms", 
            result.getSuccessCount(), result.getErrorCount(), result.getExecutionTimeMs());
        
        return result;
    }

    private List<Map<String, String>> parseCSV(InputStream inputStream, ImportMappingRequest mapping) 
        throws IOException {
        
        List<Map<String, String>> rows = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int rowNumber = 0;
            int startRow = mapping.getStartRow() != null ? mapping.getStartRow() : 0;
            
            while ((line = reader.readLine()) != null) {
                rowNumber++;
                
                if (rowNumber <= startRow) {
                    continue; // Pula headers e linhas antes do startRow
                }
                
                String[] values = line.split("\t", -1);
                Map<String, String> row = new HashMap<>();
                
                // Mapeia valores por índice de coluna
                for (int i = 0; i < values.length; i++) {
                    String value = values[i].trim();
                    row.put(String.valueOf(i), value);
                }
                
                row.put("_rowNumber", String.valueOf(rowNumber));
                rows.add(row);
            }
        }
        
        return rows;
    }

    private List<Map<String, String>> parseExcel(InputStream inputStream, ImportMappingRequest mapping) 
        throws IOException {
        
        List<Map<String, String>> rows = new ArrayList<>();
        
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet;
            
            if (mapping.getSheetName() != null && !mapping.getSheetName().isEmpty()) {
                sheet = workbook.getSheet(mapping.getSheetName());
                log.info("Tentando abrir planilha: {}", mapping.getSheetName());
                
                if (sheet == null) {
                    log.warn("Planilha '{}' não encontrada. Planilhas disponíveis:", mapping.getSheetName());
                    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                        log.warn("  - {}", workbook.getSheetName(i));
                    }
                    // Usa a primeira planilha como fallback
                    sheet = workbook.getSheetAt(0);
                    log.info("Usando primeira planilha: {}", sheet.getSheetName());
                }
            } else {
                sheet = workbook.getSheetAt(0);
                log.info("Usando primeira planilha: {}", sheet.getSheetName());
            }
            
            int startRow = mapping.getStartRow() != null ? mapping.getStartRow() : 1;
            int lastRowNum = sheet.getLastRowNum();
            
            log.info("Processando planilha '{}' - Linha inicial: {}, Última linha: {}", 
                sheet.getSheetName(), startRow, lastRowNum);
            
            for (int i = startRow; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    log.debug("Linha {} está vazia, pulando", i);
                    continue;
                }
                
                Map<String, String> rowData = new HashMap<>();
                boolean hasData = false;
                
                // Mapeia valores por índice de coluna
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    String value = cell != null ? getCellValue(cell) : "";
                    rowData.put(String.valueOf(j), value);
                    
                    if (!value.isEmpty()) {
                        hasData = true;
                    }
                }
                
                // Só adiciona a linha se tiver algum dado
                if (hasData) {
                    rowData.put("_rowNumber", String.valueOf(i + 1));
                    rows.add(rowData);
                }
            }
            
            log.info("Total de linhas lidas: {}", rows.size());
        }
        
        return rows;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

    private ImportResultResponse processSpecimenRows(
        List<Map<String, String>> rows, 
        ImportMappingRequest mapping
    ) {
        
        ImportResultResponse.ImportResultResponseBuilder resultBuilder = ImportResultResponse.builder()
            .totalRows(rows.size())
            .successCount(0)
            .errorCount(0)
            .speciesCreated(0);
        
        List<ImportError> errors = new ArrayList<>();
        
        // Busca o plot onde os espécimes serão cadastrados
        Plot plot = null;
        if (mapping.getPlotId() != null) {
            plot = plotRepository.findById(mapping.getPlotId())
                .orElseThrow(() -> new IllegalArgumentException(
                    "Plot não encontrado com ID: " + mapping.getPlotId()));
        }
        
        int successCount = 0;
        int errorCount = 0;
        int speciesCreated = 0;
        
        for (Map<String, String> row : rows) {
            try {
                String scientificName = null;
                
                // Extrai o nome científico do mapeamento por índice
                for (Map.Entry<Integer, String> entry : mapping.getColumnMapping().entrySet()) {
                    if ("scientificName".equals(entry.getValue())) {
                        scientificName = row.get(String.valueOf(entry.getKey()));
                        break;
                    }
                }
                
                // Busca ou cria espécie
                SpeciesTaxonomy species = null;
                if (scientificName != null && !scientificName.isEmpty()) {
                    species = findOrCreateSpecies(
                        scientificName, 
                        mapping.getAutoCreateSpecies()
                    );
                    
                    if (species != null && species.getId() == null) {
                        speciesCreated++;
                    }
                }
                
                SpecimenObject specimen = mapRowToSpecimen(row, mapping, plot, species);
                specimen = specimenRepository.save(specimen);
                
                log.debug("Specimen criado com ID: {}", specimen.getId());
                
                // Cria SpeciesInfo se houver dados mapeados
                SpeciesInfo speciesInfo = mapRowToSpeciesInfo(row, mapping, specimen);
                if (speciesInfo != null) {
                    speciesInfo = speciesInfoRepository.save(speciesInfo);
                    log.info("SpeciesInfo criado com ID: {} para specimen ID: {}", 
                        speciesInfo.getId(), specimen.getId());
                } else {
                    log.debug("Nenhum dado de SpeciesInfo para specimen ID: {}", specimen.getId());
                }
                
                successCount++;
                
            } catch (Exception e) {
                errorCount++;
                String rowNumber = row.get("_rowNumber");
                errors.add(ImportError.builder()
                    .rowNumber(Integer.parseInt(rowNumber))
                    .message(e.getMessage())
                    .rowData(row.toString())
                    .build());
                
                log.error("Error importing row {}: {}", rowNumber, e.getMessage());
            }
        }
        
        return resultBuilder
            .successCount(successCount)
            .errorCount(errorCount)
            .speciesCreated(speciesCreated)
            .errors(errors)
            .build();
    }

    private SpecimenObject mapRowToSpecimen(
        Map<String, String> row, 
        ImportMappingRequest mapping,
        Plot plot,
        SpeciesTaxonomy species
    ) {
        
        SpecimenObject specimen = SpecimenObject.builder()
            .plot(plot)
            .species(species)
            .build();
        
        Map<Integer, String> columnMapping = mapping.getColumnMapping();
        
        // Mapeia os campos baseado no mapping fornecido (índice -> campo)
        for (Map.Entry<Integer, String> entry : columnMapping.entrySet()) {
            Integer columnIndex = entry.getKey();
            String targetField = entry.getValue();
            String value = row.get(String.valueOf(columnIndex));
            
            if (value == null || value.isEmpty()) {
                continue;
            }
            
            mapFieldValue(specimen, targetField, value);
        }
        
        return specimen;
    }

    private void mapFieldValue(SpecimenObject specimen, String field, String value) {
        try {
            switch (field) {
                case "latitude" -> specimen.setLatitude(parseBigDecimal(value));
                case "longitude" -> specimen.setLongitude(parseBigDecimal(value));
                case "scientificName" -> {}
                // Campos de SpeciesInfo são tratados em mapRowToSpeciesInfo
                case "heightM", "dbmCm", "ageYears", "condition", "observationDate" -> {}
                default -> log.warn("Campo desconhecido para mapeamento: {}", field);
            }
        } catch (Exception e) {
            log.warn("Erro ao mapear campo {} com valor {}: {}", field, value, e.getMessage());
        }
    }

    private SpeciesInfo mapRowToSpeciesInfo(
        Map<String, String> row,
        ImportMappingRequest mapping,
        SpecimenObject specimen
    ) {
        Map<Integer, String> columnMapping = mapping.getColumnMapping();
        boolean hasSpeciesInfoData = false;
        
        SpeciesInfo.SpeciesInfoBuilder builder = SpeciesInfo.builder()
            .object(specimen)
            .observationDate(LocalDateTime.now()); // Default to now
        
        log.debug("Mapeando SpeciesInfo para specimen ID: {}", specimen.getId());
        
        for (Map.Entry<Integer, String> entry : columnMapping.entrySet()) {
            Integer columnIndex = entry.getKey();
            String targetField = entry.getValue();
            String value = row.get(String.valueOf(columnIndex));
            
            if (value == null || value.isEmpty()) {
                continue;
            }
            
            log.debug("Campo {} (coluna {}): {}", targetField, columnIndex, value);
            
            try {
                switch (targetField) {
                    case "heightM" -> {
                        BigDecimal height = parseBigDecimal(value);
                        if (height != null) {
                            builder.heightM(height);
                            hasSpeciesInfoData = true;
                            log.debug("heightM mapeado: {}", height);
                        }
                    }
                    case "dbmCm" -> {
                        BigDecimal dbm = parseBigDecimal(value);
                        if (dbm != null) {
                            builder.dbmCm(dbm);
                            hasSpeciesInfoData = true;
                            log.debug("dbmCm mapeado: {}", dbm);
                        }
                    }
                    case "ageYears" -> {
                        Integer age = parseInteger(value);
                        if (age != null) {
                            builder.ageYears(age);
                            hasSpeciesInfoData = true;
                            log.debug("ageYears mapeado: {}", age);
                        }
                    }
                    case "condition" -> {
                        Long condition = parseLong(value);
                        if (condition != null) {
                            builder.condition(condition);
                            hasSpeciesInfoData = true;
                            log.debug("condition mapeado: {}", condition);
                        }
                    }
                    case "observationDate" -> {
                        LocalDateTime date = parseDateTime(value);
                        if (date != null) {
                            builder.observationDate(date);
                            hasSpeciesInfoData = true;
                            log.debug("observationDate mapeado: {}", date);
                        }
                    }
                }
            } catch (Exception e) {
                log.warn("Erro ao mapear campo SpeciesInfo {} com valor {}: {}", targetField, value, e.getMessage());
            }
        }
        
        log.debug("hasSpeciesInfoData: {}", hasSpeciesInfoData);
        
        return hasSpeciesInfoData ? builder.build() : null;
    }

    private BigDecimal parseBigDecimal(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        try {
            // Remove possíveis caracteres não numéricos
            value = value.replace(",", ".").trim();
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            log.warn("Não foi possível parsear número: {}", value);
            return null;
        }
    }

    private Integer parseInteger(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            log.warn("Não foi possível parsear inteiro: {}", value);
            return null;
        }
    }

    private Long parseLong(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            log.warn("Não foi possível parsear long: {}", value);
            return null;
        }
    }

    private LocalDateTime parseDateTime(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        // Tenta vários formatos comuns
        DateTimeFormatter[] formatters = {
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
        };
        
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDateTime.parse(value.trim(), formatter);
            } catch (DateTimeParseException e) {
                // Tenta próximo formato
            }
        }
        
        log.warn("Não foi possível parsear data: {}", value);
        return null;
    }

    private SpeciesTaxonomy findOrCreateSpecies(String scientificName, Boolean autoCreate) {
        // Busca espécie existente por nome científico, nome comum, família ou gênero
        List<SpeciesTaxonomy> matchingSpecies = speciesTaxonomyRepository.findByAnyName(scientificName);
        
        SpeciesTaxonomy species = null;
        if (!matchingSpecies.isEmpty()) {
            // Prioriza correspondência exata no nome científico
            species = matchingSpecies.stream()
                .filter(s -> scientificName.equalsIgnoreCase(s.getScientificName()))
                .findFirst()
                .orElse(matchingSpecies.get(0)); // Caso contrário, usa a primeira correspondência
        }
        
        if (species == null && Boolean.TRUE.equals(autoCreate)) {
            // Cria nova espécie
            species = SpeciesTaxonomy.builder()
                .scientificName(scientificName)
                .build();
            species = speciesTaxonomyRepository.save(species);
            log.info("Nova espécie criada: {}", scientificName);
        }
        
        return species;
    }
}
