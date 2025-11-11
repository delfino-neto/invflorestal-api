package br.com.inv.florestal.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SpecimenObjectRepresentation {
    private Long id;
    private Long plotId;
    private String plotCode;
    private Long speciesId;
    private String speciesScientificName;
    private String speciesCommonName;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Long observerId;
    private String observerFullName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
