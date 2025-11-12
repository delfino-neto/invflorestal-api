package br.com.inv.florestal.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BulkPlotImportRequest {
    
    private Long targetAreaId; // Área de destino
    
    private List<ImportItem> items; // Lista de itens a importar
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImportItem {
        private ImportType importType; // AREA ou PLOT
        private Long sourceAreaId; // ID da área fonte (quando importType = AREA)
        private Long sourcePlotId; // ID do plot fonte (quando importType = PLOT)
        private String plotCode; // Código do novo plot
    }
    
    public enum ImportType {
        AREA,  // Importar área inteira como um único plot
        PLOT   // Importar plot individual
    }
}
