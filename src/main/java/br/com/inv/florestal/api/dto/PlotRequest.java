package br.com.inv.florestal.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlotRequest {
    private Long areaId;
    private String geometry;
    private String plotCode;
    private BigDecimal areaM2;
    private BigDecimal slopeDeg;
    private BigDecimal aspectDeg;
    private String notes;
}
