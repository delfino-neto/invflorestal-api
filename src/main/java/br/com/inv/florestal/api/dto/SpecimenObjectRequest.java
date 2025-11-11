package br.com.inv.florestal.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class SpecimenObjectRequest {
    private Long plotId;
    private Long speciesId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Long observerId;
}
