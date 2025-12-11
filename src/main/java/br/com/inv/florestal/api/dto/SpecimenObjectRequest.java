package br.com.inv.florestal.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecimenObjectRequest {
    private Long plotId;
    private Long speciesId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Long observerId;
    
    // SpeciesInfo
    private LocalDateTime observationDate;
    private BigDecimal heightM;
    private BigDecimal dbmCm;
    private Integer ageYears;
    private Long condition;
}
