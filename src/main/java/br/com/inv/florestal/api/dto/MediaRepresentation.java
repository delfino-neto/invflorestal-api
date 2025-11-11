package br.com.inv.florestal.api.dto;

import br.com.inv.florestal.api.models.media.Media;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MediaRepresentation {
    private Long id;
    private Long objectId;
    private String url;
    private Media.MediaType type;
    private String description;
    private Long uploadedById;
    private String uploadedByFullName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
