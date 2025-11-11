package br.com.inv.florestal.api.controllers;

import br.com.inv.florestal.api.dto.MediaRequest;
import br.com.inv.florestal.api.dto.MediaRepresentation;
import br.com.inv.florestal.api.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/media")
public class MediaController {

    private final MediaService mediaService;

    @PostMapping
    public ResponseEntity<MediaRepresentation> create(@RequestBody MediaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mediaService.create(request));
    }

    @PostMapping("/upload/{objectId}")
    public ResponseEntity<MediaRepresentation> uploadImage(
            @PathVariable Long objectId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("uploadedById") Long uploadedById
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mediaService.uploadImage(objectId, file, description, uploadedById));
    }

    @GetMapping
    public ResponseEntity<Page<MediaRepresentation>> search(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(mediaService.search(page, size));
    }

    @GetMapping("/object/{objectId}")
    public ResponseEntity<Page<MediaRepresentation>> findByObjectId(
            @PathVariable Long objectId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(mediaService.findByObjectId(objectId, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaRepresentation> findById(@PathVariable Long id) {
        return mediaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MediaRepresentation> update(
            @PathVariable Long id,
            @RequestBody MediaRequest request
    ) {
        return ResponseEntity.ok(mediaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mediaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
