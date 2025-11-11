package br.com.inv.florestal.api.service;

import br.com.inv.florestal.api.dto.MediaRequest;
import br.com.inv.florestal.api.dto.MediaRepresentation;
import br.com.inv.florestal.api.models.media.Media;
import br.com.inv.florestal.api.models.specimen.SpecimenObject;
import br.com.inv.florestal.api.models.user.User;
import br.com.inv.florestal.api.repository.MediaRepository;
import br.com.inv.florestal.api.repository.SpecimenObjectRepository;
import br.com.inv.florestal.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;
    private final SpecimenObjectRepository specimenObjectRepository;
    private final UserRepository userRepository;
    private final String uploadDir = "uploads/media/";

    public MediaRepresentation create(MediaRequest request) {
        SpecimenObject specimenObject = specimenObjectRepository.findById(request.getObjectId())
                .orElseThrow(() -> new RuntimeException("Specimen Object not found"));

        User uploadedBy = userRepository.findById(request.getUploadedById())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Media media = Media.builder()
                .object(specimenObject)
                .url(request.getUrl())
                .type(request.getType())
                .description(request.getDescription())
                .uploadedBy(uploadedBy)
                .build();
        
        return toRepresentation(mediaRepository.save(media));
    }

    public MediaRepresentation uploadImage(Long objectId, MultipartFile file, String description, Long uploadedById) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        // Validate image type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("Only image files are allowed");
        }

        SpecimenObject specimenObject = specimenObjectRepository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("Specimen Object not found"));

        User uploadedBy = userRepository.findById(uploadedById)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".") 
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : ".jpg";
            String filename = UUID.randomUUID().toString() + extension;

            // Save file
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Create media record
            Media media = Media.builder()
                    .object(specimenObject)
                    .url("/uploads/media/" + filename)
                    .type(Media.MediaType.IMAGEM)
                    .description(description)
                    .uploadedBy(uploadedBy)
                    .build();

            return toRepresentation(mediaRepository.save(media));

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }
    }

    public Page<MediaRepresentation> search(Integer page, Integer size) {
        return mediaRepository.findAll(PageRequest.of(page, size))
                .map(this::toRepresentation);
    }

    public Page<MediaRepresentation> findByObjectId(Long objectId, Integer page, Integer size) {
        return mediaRepository.findByObjectId(objectId, PageRequest.of(page, size))
                .map(this::toRepresentation);
    }

    public Optional<MediaRepresentation> findById(Long id) {
        return mediaRepository.findById(id).map(this::toRepresentation);
    }

    public MediaRepresentation update(Long id, MediaRequest request) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        SpecimenObject specimenObject = specimenObjectRepository.findById(request.getObjectId())
                .orElseThrow(() -> new RuntimeException("Specimen Object not found"));

        User uploadedBy = userRepository.findById(request.getUploadedById())
                .orElseThrow(() -> new RuntimeException("User not found"));

        media.setObject(specimenObject);
        media.setUrl(request.getUrl());
        media.setType(request.getType());
        media.setDescription(request.getDescription());
        media.setUploadedBy(uploadedBy);

        return toRepresentation(mediaRepository.save(media));
    }

    public void delete(Long id) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found"));

        // Delete physical file
        try {
            String url = media.getUrl();
            if (url != null && url.startsWith("/uploads/media/")) {
                Path filePath = Paths.get("uploads/media/" + url.substring(url.lastIndexOf("/") + 1));
                Files.deleteIfExists(filePath);
            }
        } catch (IOException e) {
            // Log error but don't fail the operation
            System.err.println("Failed to delete file: " + e.getMessage());
        }

        mediaRepository.deleteById(id);
    }

    private MediaRepresentation toRepresentation(Media media) {
        return MediaRepresentation.builder()
                .id(media.getId())
                .objectId(media.getObject().getId())
                .url(media.getUrl())
                .type(media.getType())
                .description(media.getDescription())
                .uploadedById(media.getUploadedBy().getId())
                .uploadedByFullName(media.getUploadedBy().fullName())
                .createdAt(media.getCreatedAt())
                .updatedAt(media.getUpdatedAt())
                .build();
    }
}
