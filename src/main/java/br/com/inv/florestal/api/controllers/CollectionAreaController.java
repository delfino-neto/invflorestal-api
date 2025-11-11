package br.com.inv.florestal.api.controllers;

import br.com.inv.florestal.api.dto.CollectionAreaRequest;
import br.com.inv.florestal.api.dto.CollectionAreaRepresentation;
import br.com.inv.florestal.api.service.CollectionAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/collection-areas")
public class CollectionAreaController {

    private final CollectionAreaService collectionAreaService;

    @PostMapping
    public ResponseEntity<CollectionAreaRepresentation> create(@RequestBody CollectionAreaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(collectionAreaService.create(request));
    }

    @GetMapping
    public ResponseEntity<Page<CollectionAreaRepresentation>> search(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(collectionAreaService.search(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionAreaRepresentation> findById(@PathVariable Long id) {
        return collectionAreaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollectionAreaRepresentation> update(
            @PathVariable Long id,
            @RequestBody CollectionAreaRequest request
    ) {
        return ResponseEntity.ok(collectionAreaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        collectionAreaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
