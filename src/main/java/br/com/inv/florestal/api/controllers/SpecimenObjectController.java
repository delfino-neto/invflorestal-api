package br.com.inv.florestal.api.controllers;

import br.com.inv.florestal.api.dto.SpecimenObjectRequest;
import br.com.inv.florestal.api.dto.SpecimenObjectRepresentation;
import br.com.inv.florestal.api.service.SpecimenObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/specimen-objects")
public class SpecimenObjectController {

    private final SpecimenObjectService specimenObjectService;

    @PostMapping
    public ResponseEntity<SpecimenObjectRepresentation> create(@RequestBody SpecimenObjectRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(specimenObjectService.create(request));
    }

    @GetMapping
    public ResponseEntity<Page<SpecimenObjectRepresentation>> search(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(specimenObjectService.search(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecimenObjectRepresentation> findById(@PathVariable Long id) {
        return specimenObjectService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/by-area/{areaId}")
    public ResponseEntity<java.util.List<SpecimenObjectRepresentation>> findByCollectionAreaId(@PathVariable Long areaId) {
        return ResponseEntity.ok(specimenObjectService.findByCollectionAreaId(areaId));
    }
    
    @GetMapping("/filter")
    public ResponseEntity<java.util.List<SpecimenObjectRepresentation>> findWithFilters(
            @RequestParam(required = false) Long speciesId,
            @RequestParam(required = false) Long areaId,
            @RequestParam(required = false) Long observerId
    ) {
        return ResponseEntity.ok(specimenObjectService.findWithFilters(speciesId, areaId, observerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecimenObjectRepresentation> update(
            @PathVariable Long id,
            @RequestBody SpecimenObjectRequest request
    ) {
        return ResponseEntity.ok(specimenObjectService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        specimenObjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
