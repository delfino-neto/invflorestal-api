package br.com.inv.florestal.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PlotRepresentation {
    private Long id;
    private Long areaId;
    private String areaName;
    private String geometry;
    private String plotCode;
    private BigDecimal areaM2;
    private BigDecimal slopeDeg;
    private BigDecimal aspectDeg;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
