package br.com.inv.florestal.api.dto;

import br.com.inv.florestal.api.models.media.Media;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MediaRequest {
    private Long objectId;
    private String url;
    private Media.MediaType type;
    private String description;
    private Long uploadedById;
}
