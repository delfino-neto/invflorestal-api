package br.com.inv.florestal.api.dto.specimen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesInfoDTO {
    private Long id;
    private Long objectId;
    private LocalDateTime observationDate;
    private BigDecimal heightM;
    private BigDecimal dbmCm;
    private Integer ageYears;
    private Long condition;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
