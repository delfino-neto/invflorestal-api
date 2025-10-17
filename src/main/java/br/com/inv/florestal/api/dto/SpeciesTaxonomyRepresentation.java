package br.com.inv.florestal.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SpeciesTaxonomyRepresentation {
    private Long id;
    private String scientificName;
    private String commonName;
    private String family;
    private String genus;
    private String code;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
