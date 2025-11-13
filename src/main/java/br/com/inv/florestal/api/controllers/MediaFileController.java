package br.com.inv.florestal.api.controllers;

import br.com.inv.florestal.api.storage.StorageFileNotFoundException;
import br.com.inv.florestal.api.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/uploads/media")
@RequiredArgsConstructor
public class MediaFileController {

    private static final Logger log = LoggerFactory.getLogger(MediaFileController.class);
    private final StorageService storageService;

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        System.out.println("\n\n\nServing file: " + filename + "\n\n\n");
        try {
            log.info("Attempting to serve file: {}", filename);
            Resource resource = storageService.loadAsResource(filename);
            
            String contentType = determineContentType(filename);
            log.info("Serving file: {} with content-type: {}", filename, contentType);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (StorageFileNotFoundException e) {
            log.warn("File not found: {}", filename);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error serving file: {}", filename, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    private String determineContentType(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "svg" -> "image/svg+xml";
            case "webp" -> "image/webp";
            case "bmp" -> "image/bmp";
            default -> "application/octet-stream";
        };
    }
}
