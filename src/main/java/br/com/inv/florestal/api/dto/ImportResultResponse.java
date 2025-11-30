package br.com.inv.florestal.api.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportResultResponse {
    
    private Integer totalRows;
    private Integer successCount;
    private Integer errorCount;
    private Integer speciesCreated;
    
    @Builder.Default
    private List<ImportError> errors = new ArrayList<>();
    
    private Long executionTimeMs;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImportError {
        private Integer rowNumber;
        private String message;
        private String rowData;
    }
}
