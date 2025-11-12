package br.com.inv.florestal.api.controllers;

import br.com.inv.florestal.api.dto.SpeciesTaxonomyRequest;
import br.com.inv.florestal.api.dto.SpeciesTaxonomyRepresentation;
import br.com.inv.florestal.api.service.SpeciesTaxonomyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/species-taxonomy")
public class SpeciesTaxonomyController {

    private final SpeciesTaxonomyService speciesTaxonomyService;

    @PostMapping
    public ResponseEntity<SpeciesTaxonomyRepresentation> create(@RequestBody SpeciesTaxonomyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(speciesTaxonomyService.create(request));
    }

    @GetMapping
    public ResponseEntity<Page<SpeciesTaxonomyRepresentation>> search(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            @RequestParam(value = "family", required = false) String family,
            @RequestParam(value = "genus", required = false) String genus
    ) {
        return ResponseEntity.ok(speciesTaxonomyService.search(page, size, searchTerm, family, genus));
    }
    
    @GetMapping("/families")
    public ResponseEntity<List<String>> getDistinctFamilies() {
        return ResponseEntity.ok(speciesTaxonomyService.getDistinctFamilies());
    }
    
    @GetMapping("/genera")
    public ResponseEntity<List<String>> getDistinctGenera() {
        return ResponseEntity.ok(speciesTaxonomyService.getDistinctGenera());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpeciesTaxonomyRepresentation> findById(@PathVariable Long id) {
        return speciesTaxonomyService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpeciesTaxonomyRepresentation> update(
            @PathVariable Long id,
            @RequestBody SpeciesTaxonomyRequest request
    ) {
        return ResponseEntity.ok(speciesTaxonomyService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        speciesTaxonomyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
