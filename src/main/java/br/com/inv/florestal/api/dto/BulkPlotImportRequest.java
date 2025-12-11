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
    
    private Long targetAreaId;
    
    private List<ImportItem> items;
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImportItem {
        private ImportType importType;
        private Long sourceAreaId;
        private Long sourcePlotId;
        private String plotCode;
    }
    
    public enum ImportType {
        AREA, 
        PLOT
    }
}
