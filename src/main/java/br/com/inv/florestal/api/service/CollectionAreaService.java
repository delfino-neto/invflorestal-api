package br.com.inv.florestal.api.service;

import br.com.inv.florestal.api.dto.CollectionAreaRequest;
import br.com.inv.florestal.api.dto.CollectionAreaRepresentation;
import br.com.inv.florestal.api.models.collection.CollectionArea;
import br.com.inv.florestal.api.models.user.User;
import br.com.inv.florestal.api.repository.CollectionAreaRepository;
import br.com.inv.florestal.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectionAreaService {

    private final CollectionAreaRepository collectionAreaRepository;
    private final UserRepository userRepository;

    public CollectionAreaRepresentation create(CollectionAreaRequest request) {
        User createdBy = userRepository.findById(request.getCreatedById())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CollectionArea collectionArea = CollectionArea.builder()
                .name(request.getName())
                .geometry(request.getGeometry())
                .createdBy(createdBy)
                .notes(request.getNotes())
                .build();
        return toRepresentation(collectionAreaRepository.save(collectionArea));
    }

    public Page<CollectionAreaRepresentation> search(Integer page, Integer size) {
        return collectionAreaRepository.findAll(PageRequest.of(page, size))
                .map(this::toRepresentation);
    }

    public Optional<CollectionAreaRepresentation> findById(Long id) {
        return collectionAreaRepository.findById(id).map(this::toRepresentation);
    }

    public CollectionAreaRepresentation update(Long id, CollectionAreaRequest request) {
        CollectionArea collectionArea = collectionAreaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collection Area not found"));

        User createdBy = userRepository.findById(request.getCreatedById())
                .orElseThrow(() -> new RuntimeException("User not found"));

        collectionArea.setName(request.getName());
        collectionArea.setGeometry(request.getGeometry());
        collectionArea.setCreatedBy(createdBy);
        collectionArea.setNotes(request.getNotes());

        return toRepresentation(collectionAreaRepository.save(collectionArea));
    }

    public void delete(Long id) {
        collectionAreaRepository.deleteById(id);
    }

    private CollectionAreaRepresentation toRepresentation(CollectionArea collectionArea) {
        return CollectionAreaRepresentation.builder()
                .id(collectionArea.getId())
                .name(collectionArea.getName())
                .geometry(collectionArea.getGeometry())
                .createdById(collectionArea.getCreatedBy().getId())
                .createdByFullName(collectionArea.getCreatedBy().fullName())
                .notes(collectionArea.getNotes())
                .createdAt(collectionArea.getCreatedAt())
                .updatedAt(collectionArea.getUpdatedAt())
                .build();
    }
}
