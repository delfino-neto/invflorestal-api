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

import br.com.inv.florestal.api.aspect.Auditable;
import br.com.inv.florestal.api.models.audit.AuditLog.AuditAction;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectionAreaService {

    private final CollectionAreaRepository collectionAreaRepository;
    private final UserRepository userRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Auditable(action = AuditAction.CREATE, entityName = "CollectionArea", description = "Nova área de coleta criada")
    public CollectionAreaRepresentation create(CollectionAreaRequest request, String userEmail) {
        User createdBy = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        // Usar query nativa para fazer o cast do geometry
        String sql = "INSERT INTO collection_area (name, geometry, created_by, notes, biome, " +
                     "climate_zone, soil_type, conservation_status, vegetation_type, altitude_m, " +
                     "protected_area, protected_area_name, land_owner, created_at) " +
                     "VALUES (?1, CAST(?2 AS polygon), ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, CURRENT_TIMESTAMP) RETURNING id";
        
        Long id = ((Number) entityManager.createNativeQuery(sql)
                .setParameter(1, request.getName())
                .setParameter(2, request.getGeometry())
                .setParameter(3, createdBy.getId())
                .setParameter(4, request.getNotes())
                .setParameter(5, request.getBiome())
                .setParameter(6, request.getClimateZone())
                .setParameter(7, request.getSoilType())
                .setParameter(8, request.getConservationStatus())
                .setParameter(9, request.getVegetationType())
                .setParameter(10, request.getAltitudeM())
                .setParameter(11, request.getProtectedArea())
                .setParameter(12, request.getProtectedAreaName())
                .setParameter(13, request.getLandOwner())
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
    @Auditable(action = AuditAction.UPDATE, entityName = "CollectionArea", description = "Área de coleta atualizada")
    public CollectionAreaRepresentation update(Long id, CollectionAreaRequest request, String userEmail) {
        // Validar se o usuário existe
        userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        // Verificar se a área existe
        CollectionArea collectionArea = collectionAreaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collection Area not found"));

        // Usar query nativa para fazer o cast do geometry
        String sql = "UPDATE collection_area SET name = ?1, geometry = CAST(?2 AS polygon), " +
                     "notes = ?3, biome = ?4, climate_zone = ?5, soil_type = ?6, " +
                     "conservation_status = ?7, vegetation_type = ?8, altitude_m = ?9, " +
                     "protected_area = ?10, protected_area_name = ?11, land_owner = ?12, " +
                     "updated_at = CURRENT_TIMESTAMP WHERE id = ?13";
        
        entityManager.createNativeQuery(sql)
                .setParameter(1, request.getName())
                .setParameter(2, request.getGeometry())
                .setParameter(3, request.getNotes())
                .setParameter(4, request.getBiome())
                .setParameter(5, request.getClimateZone())
                .setParameter(6, request.getSoilType())
                .setParameter(7, request.getConservationStatus())
                .setParameter(8, request.getVegetationType())
                .setParameter(9, request.getAltitudeM())
                .setParameter(10, request.getProtectedArea())
                .setParameter(11, request.getProtectedAreaName())
                .setParameter(12, request.getLandOwner())
                .setParameter(13, id)
                .executeUpdate();
        
        // Buscar o registro atualizado
        collectionArea = collectionAreaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Failed to update collection area"));

        return toRepresentation(collectionArea);
    }

    @Auditable(action = AuditAction.DELETE, entityName = "CollectionArea", description = "Área de coleta excluída")
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
                .biome(collectionArea.getBiome())
                .climateZone(collectionArea.getClimateZone())
                .soilType(collectionArea.getSoilType())
                .conservationStatus(collectionArea.getConservationStatus())
                .vegetationType(collectionArea.getVegetationType())
                .altitudeM(collectionArea.getAltitudeM())
                .protectedArea(collectionArea.getProtectedArea())
                .protectedAreaName(collectionArea.getProtectedAreaName())
                .landOwner(collectionArea.getLandOwner())
                .createdAt(collectionArea.getCreatedAt())
                .updatedAt(collectionArea.getUpdatedAt())
                .speciesCount(speciesCount != null ? speciesCount : 0L)
                .specimensCount(specimensCount != null ? specimensCount : 0L)
                .build();
    }
}
