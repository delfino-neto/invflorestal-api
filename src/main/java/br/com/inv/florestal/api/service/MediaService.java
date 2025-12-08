package br.com.inv.florestal.api.service;

import br.com.inv.florestal.api.dto.MediaRequest;
import br.com.inv.florestal.api.dto.MediaRepresentation;
import br.com.inv.florestal.api.models.media.Media;
import br.com.inv.florestal.api.models.specimen.SpecimenObject;
import br.com.inv.florestal.api.models.user.User;
import br.com.inv.florestal.api.repository.MediaRepository;
import br.com.inv.florestal.api.repository.SpecimenObjectRepository;
import br.com.inv.florestal.api.repository.UserRepository;
import br.com.inv.florestal.api.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;
    private final SpecimenObjectRepository specimenObjectRepository;
    private final UserRepository userRepository;
    private final StorageService storageService;
    private final ExifMetadataService exifMetadataService;

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

    public MediaRepresentation uploadImage(Long objectId, MultipartFile file, String description, 
                                          Long uploadedById, Double latitude, Double longitude, Long timestamp) {
        System.out.println("📸 [MediaService] Upload iniciado:");
        System.out.println("   Arquivo: " + file.getOriginalFilename());
        System.out.println("   Latitude: " + latitude);
        System.out.println("   Longitude: " + longitude);
        System.out.println("   Timestamp: " + timestamp);
        
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("Only image files are allowed");
        }

        SpecimenObject specimenObject = specimenObjectRepository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("Specimen Object not found"));

        User uploadedBy;
        if (uploadedById != null) {
            uploadedBy = userRepository.findById(uploadedById)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof User) {
                uploadedBy = (User) authentication.getPrincipal();
            } else if (authentication != null && authentication.getName() != null) {
                uploadedBy = userRepository.findByEmail(authentication.getName())
                        .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
            } else {
                throw new RuntimeException("No user specified and no authenticated user found");
            }
        }

        try {
            String filename = storageService.store(file);
            
            // Adiciona metadados EXIF se houver GPS ou timestamp
            if (latitude != null || longitude != null || timestamp != null) {
                try {
                    Path uploadPath = storageService.load(filename);
                    exifMetadataService.addExifMetadata(
                        uploadPath.toString(), 
                        latitude, 
                        longitude, 
                        timestamp
                    );
                    System.out.println("✅ Metadados EXIF adicionados à imagem: " + filename);
                } catch (Exception e) {
                    System.err.println("⚠️ Falha ao adicionar metadados EXIF: " + e.getMessage());
                    // Não falha o upload se o EXIF não puder ser adicionado
                }
            }

            Media media = Media.builder()
                    .object(specimenObject)
                    .url("/uploads/media/" + filename)
                    .type(Media.MediaType.IMAGEM)
                    .description(description)
                    .uploadedBy(uploadedBy)
                    .build();

            return toRepresentation(mediaRepository.save(media));
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
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

        try {
            String url = media.getUrl();
            if (url != null && url.startsWith("/uploads/media/")) {
                String filename = url.substring(url.lastIndexOf("/") + 1);
                storageService.delete(filename);
            }
        } catch (Exception e) {
            // Ignore file deletion errors
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
