package br.com.inv.florestal.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
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
import br.com.inv.florestal.api.repository.PlotRepository;
import br.com.inv.florestal.api.repository.SpeciesTaxonomyRepository;
import br.com.inv.florestal.api.repository.SpecimenObjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataImportService {

    private final SpecimenObjectRepository specimenRepository;
    private final SpeciesTaxonomyRepository speciesTaxonomyRepository;
    private final PlotRepository plotRepository;

    @Transactional
    public ImportResultResponse importSpecimens(
        MultipartFile file, 
        ImportMappingRequest mapping
    ) throws IOException {
        
        long startTime = System.currentTimeMillis();
        
        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw new IllegalArgumentException("Arquivo sem nome");
        }

        List<Map<String, String>> rows;
        
        if (filename.endsWith(".csv")) {
            rows = parseCSV(file.getInputStream(), mapping);
        } else if (filename.endsWith(".xlsx") || filename.endsWith(".xls")) {
            rows = parseExcel(file.getInputStream(), mapping);
        } else {
            throw new IllegalArgumentException("Formato de arquivo não suportado. Use CSV ou XLSX");
        }

        ImportResultResponse result = processSpecimenRows(rows, mapping);
        result.setExecutionTimeMs(System.currentTimeMillis() - startTime);
        
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
            } else {
                sheet = workbook.getSheetAt(0);
            }
            
            if (sheet == null) {
                return rows;
            }
            
            int startRow = mapping.getStartRow() != null ? mapping.getStartRow() : 1;
            
            for (int i = startRow; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                
                Map<String, String> rowData = new HashMap<>();
                
                // Mapeia valores por índice de coluna
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    String value = cell != null ? getCellValue(cell) : "";
                    rowData.put(String.valueOf(j), value);
                }
                
                rowData.put("_rowNumber", String.valueOf(i + 1));
                rows.add(rowData);
            }
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
                specimenRepository.save(specimen);
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
                case "scientificName" -> {} // Já processado antes
                default -> log.warn("Campo desconhecido para mapeamento: {}", field);
            }
        } catch (Exception e) {
            log.warn("Erro ao mapear campo {} com valor {}: {}", field, value, e.getMessage());
        }
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

    private SpeciesTaxonomy findOrCreateSpecies(String scientificName, Boolean autoCreate) {
        // Busca espécie existente por nome científico
        SpeciesTaxonomy species = speciesTaxonomyRepository.findAll().stream()
            .filter(s -> scientificName.equalsIgnoreCase(s.getScientificName()))
            .findFirst()
            .orElse(null);
        
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
