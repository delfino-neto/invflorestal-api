package br.com.inv.florestal.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpeciesTaxonomyRequest {
    private String scientificName;
    private String commonName;
    private String family;
    private String genus;
    private String code;
}
