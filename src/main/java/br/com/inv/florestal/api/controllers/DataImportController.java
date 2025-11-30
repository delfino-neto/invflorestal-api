package br.com.inv.florestal.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.inv.florestal.api.dto.ImportMappingRequest;
import br.com.inv.florestal.api.dto.ImportResultResponse;
import br.com.inv.florestal.api.service.DataImportService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/import")
@RequiredArgsConstructor
public class DataImportController {

    private final DataImportService importService;
    private final ObjectMapper objectMapper;

    /**
     * Importa espécimes de arquivo CSV ou Excel
     * 
     * @param file Arquivo CSV (separado por tab) ou Excel (.xlsx)
     * @param mappingJson JSON com mapeamento de colunas por índice
     * 
     * Exemplo de mappingJson:
     * {
     *   "columnMapping": {
     *     "0": "scientificName",  // Primeira coluna (índice 0) = nome científico
     *     "3": "latitude",        // Quarta coluna (índice 3) = latitude
     *     "4": "longitude"        // Quinta coluna (índice 4) = longitude
     *   },
     *   "startRow": 1,            // Linha inicial (ignora header na linha 0)
     *   "autoCreateSpecies": true,
     *   "collectionAreaId": 1
     * }
     * 
     * @return Resultado da importação com estatísticas e erros
     */
    @PostMapping("/specimens")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ImportResultResponse> importSpecimens(
        @RequestPart("file") MultipartFile file,
        @RequestParam("mapping") String mappingJson
    ) {
        try {
            ImportMappingRequest mapping = objectMapper.readValue(mappingJson, ImportMappingRequest.class);
            
            ImportResultResponse result = importService.importSpecimens(file, mapping);
            
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                ImportResultResponse.builder()
                    .totalRows(0)
                    .successCount(0)
                    .errorCount(1)
                    .errors(java.util.List.of(
                        ImportResultResponse.ImportError.builder()
                            .rowNumber(0)
                            .message("Erro ao processar arquivo: " + e.getMessage())
                            .build()
                    ))
                    .build()
            );
        }
    }
}
