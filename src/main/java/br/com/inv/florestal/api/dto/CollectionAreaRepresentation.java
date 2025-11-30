package br.com.inv.florestal.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CollectionAreaRepresentation {
    private Long id;
    private String name;
    private String geometry;
    private Long createdById;
    private String createdByFullName;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long speciesCount;
    private Long specimensCount;
}
