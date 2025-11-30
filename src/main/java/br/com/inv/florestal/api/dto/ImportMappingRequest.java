package br.com.inv.florestal.api.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportMappingRequest {
    
    /**
     * Mapeamento entre o índice da coluna no arquivo (0-based) e o campo da entidade
     * Exemplo: {0: "scientificName", 3: "latitude", 4: "longitude"}
     */
    private Map<Integer, String> columnMapping;
    
    /**
     * Nome da planilha (para Excel) - opcional
     */
    private String sheetName;
    
    /**
     * Linha inicial de dados (ignora cabeçalhos extras)
     */
    private Integer startRow;
    
    /**
     * Se deve criar espécies automaticamente quando não encontradas
     */
    private Boolean autoCreateSpecies;
    
    /**
     * ID do plot onde os espécimes serão cadastrados
     */
    private Long plotId;
}
