package br.com.inv.florestal.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlotImportRequest {
    
    private Long targetAreaId;
    
    private ImportType importType;
    
    private Long sourceAreaId;
    
    private Long sourcePlotId;
    
    private String plotCode;
    
    public enum ImportType {
        AREA,
        PLOT
    }
}
