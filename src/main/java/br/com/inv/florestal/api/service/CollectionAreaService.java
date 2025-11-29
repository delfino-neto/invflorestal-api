package br.com.inv.florestal.api.service;

import br.com.inv.florestal.api.dto.CollectionAreaRequest;
import br.com.inv.florestal.api.dto.CollectionAreaRepresentation;
import br.com.inv.florestal.api.models.collection.CollectionArea;
import br.com.inv.florestal.api.models.user.User;
import br.com.inv.florestal.api.repository.CollectionAreaRepository;
import br.com.inv.florestal.api.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectionAreaService {

    private final CollectionAreaRepository collectionAreaRepository;
    private final UserRepository userRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public CollectionAreaRepresentation create(CollectionAreaRequest request, String userEmail) {
        User createdBy = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        // Usar query nativa para fazer o cast do geometry
        String sql = "INSERT INTO collection_area (name, geometry, created_by, notes, created_at) " +
                     "VALUES (?1, CAST(?2 AS polygon), ?3, ?4, CURRENT_TIMESTAMP) RETURNING id";
        
        Long id = ((Number) entityManager.createNativeQuery(sql)
                .setParameter(1, request.getName())
                .setParameter(2, request.getGeometry())
                .setParameter(3, createdBy.getId())
                .setParameter(4, request.getNotes())
                .getSingleResult()).longValue();
        
        // Buscar o registro completo
        CollectionArea collectionArea = collectionAreaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Failed to create collection area"));
        
        return toRepresentation(collectionArea);
    }

    public Page<CollectionAreaRepresentation> search(Integer page, Integer size, String searchTerm) {
        System.out.println("SEARCH TERM: " + searchTerm);
        return collectionAreaRepository.search(
                searchTerm,
                PageRequest.of(page, size)
        ).map(this::toRepresentation);
    }

    public Optional<CollectionAreaRepresentation> findById(Long id) {
        return collectionAreaRepository.findById(id).map(this::toRepresentation);
    }

    @Transactional
    public CollectionAreaRepresentation update(Long id, CollectionAreaRequest request, String userEmail) {
        // Validar se o usuário existe
        userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        // Verificar se a área existe
        CollectionArea collectionArea = collectionAreaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collection Area not found"));

        // Usar query nativa para fazer o cast do geometry
        String sql = "UPDATE collection_area SET name = ?1, geometry = CAST(?2 AS polygon), " +
                     "notes = ?3, updated_at = CURRENT_TIMESTAMP WHERE id = ?4";
        
        entityManager.createNativeQuery(sql)
                .setParameter(1, request.getName())
                .setParameter(2, request.getGeometry())
                .setParameter(3, request.getNotes())
                .setParameter(4, id)
                .executeUpdate();
        
        // Buscar o registro atualizado
        collectionArea = collectionAreaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Failed to update collection area"));

        return toRepresentation(collectionArea);
    }

    public void delete(Long id) {
        collectionAreaRepository.deleteById(id);
    }

    private CollectionAreaRepresentation toRepresentation(CollectionArea collectionArea) {
        Long speciesCount = collectionAreaRepository.countSpeciesByAreaId(collectionArea.getId());
        Long specimensCount = collectionAreaRepository.countSpecimensByAreaId(collectionArea.getId());
        
        return CollectionAreaRepresentation.builder()
                .id(collectionArea.getId())
                .name(collectionArea.getName())
                .geometry(collectionArea.getGeometry())
                .createdById(collectionArea.getCreatedBy().getId())
                .createdByFullName(collectionArea.getCreatedBy().fullName())
                .notes(collectionArea.getNotes())
                .createdAt(collectionArea.getCreatedAt())
                .updatedAt(collectionArea.getUpdatedAt())
                .speciesCount(speciesCount != null ? speciesCount : 0L)
                .specimensCount(specimensCount != null ? specimensCount : 0L)
                .build();
    }
}
