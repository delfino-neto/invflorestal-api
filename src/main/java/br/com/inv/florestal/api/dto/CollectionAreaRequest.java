package br.com.inv.florestal.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionAreaRequest {
    private String name;
    private String geometry;
    private String notes;
    private String biome;
    private String climateZone;
    private String soilType;
    private String conservationStatus;
    private String vegetationType;
    private java.math.BigDecimal altitudeM;
    private Boolean protectedArea;
    private String protectedAreaName;
    private String landOwner;
}
