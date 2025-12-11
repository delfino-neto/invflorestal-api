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
    private Map<Integer, String> columnMapping;
    private String sheetName;
    private Integer startRow;
    private Boolean autoCreateSpecies;
    private Long plotId;
}
