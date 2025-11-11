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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
