package br.com.inv.florestal.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CollectionAreaRequest {
    private String name;
    private String geometry;
    private String notes;
}
