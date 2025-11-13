package br.com.inv.florestal.api.dto.specimen;

import jakarta.validation.constraints.NotNull;
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
public class CreateSpeciesInfoDTO {
    
    @NotNull(message = "Object ID é obrigatório")
    private Long objectId;
    
    private LocalDateTime observationDate; // Se null, usa LocalDateTime.now()
    
    private BigDecimal heightM;
    
    private BigDecimal dbmCm;
    
    private Integer ageYears;
    
    private Long condition;
}
