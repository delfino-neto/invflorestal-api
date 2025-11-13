package br.com.inv.florestal.api.controllers;

import br.com.inv.florestal.api.dto.specimen.CreateSpeciesInfoDTO;
import br.com.inv.florestal.api.dto.specimen.SpeciesInfoDTO;
import br.com.inv.florestal.api.service.SpeciesInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/species-info")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class SpeciesInfoController {

    private final SpeciesInfoService speciesInfoService;

    /**
     * Cria uma nova observação/medição para um espécime
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpeciesInfoDTO> create(@Valid @RequestBody CreateSpeciesInfoDTO dto) {
        SpeciesInfoDTO created = speciesInfoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Busca uma informação específica por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<SpeciesInfoDTO> getById(@PathVariable Long id) {
        SpeciesInfoDTO info = speciesInfoService.getById(id);
        return ResponseEntity.ok(info);
    }

    /**
     * Busca todo o histórico de um espécime
     */
    @GetMapping("/specimen/{specimenId}/history")
    public ResponseEntity<List<SpeciesInfoDTO>> getHistory(@PathVariable Long specimenId) {
        List<SpeciesInfoDTO> history = speciesInfoService.getHistoryBySpecimenId(specimenId);
        return ResponseEntity.ok(history);
    }

    /**
     * Busca o histórico de um espécime com paginação
     */
    @GetMapping("/specimen/{specimenId}/history/paginated")
    public ResponseEntity<Page<SpeciesInfoDTO>> getHistoryPaginated(
            @PathVariable Long specimenId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<SpeciesInfoDTO> historyPage = speciesInfoService.getHistoryBySpecimenId(specimenId, page, size);
        return ResponseEntity.ok(historyPage);
    }

    /**
     * Busca a informação mais recente de um espécime
     */
    @GetMapping("/specimen/{specimenId}/latest")
    public ResponseEntity<SpeciesInfoDTO> getLatest(@PathVariable Long specimenId) {
        SpeciesInfoDTO latest = speciesInfoService.getLatestBySpecimenId(specimenId);
        if (latest == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(latest);
    }

    /**
     * Conta quantas observações um espécime possui
     */
    @GetMapping("/specimen/{specimenId}/count")
    public ResponseEntity<Long> count(@PathVariable Long specimenId) {
        long count = speciesInfoService.countBySpecimenId(specimenId);
        return ResponseEntity.ok(count);
    }

    /**
     * Atualiza uma informação existente
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpeciesInfoDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CreateSpeciesInfoDTO dto
    ) {
        SpeciesInfoDTO updated = speciesInfoService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Deleta uma informação
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        speciesInfoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
