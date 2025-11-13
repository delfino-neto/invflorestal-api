package br.com.inv.florestal.api.repository;

import br.com.inv.florestal.api.models.specimen.SpeciesInfo;
import br.com.inv.florestal.api.models.specimen.SpecimenObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpeciesInfoRepository extends JpaRepository<SpeciesInfo, Long> {
    
    /**
     * Busca todas as informações de um espécime ordenadas por data de observação
     */
    List<SpeciesInfo> findByObjectOrderByObservationDateDesc(SpecimenObject object);
    
    /**
     * Busca todas as informações de um espécime com paginação
     */
    Page<SpeciesInfo> findByObjectOrderByObservationDateDesc(SpecimenObject object, Pageable pageable);
    
    /**
     * Busca a informação mais recente de um espécime
     */
    @Query("SELECT si FROM SpeciesInfo si WHERE si.object = :object ORDER BY si.observationDate DESC LIMIT 1")
    Optional<SpeciesInfo> findLatestByObject(@Param("object") SpecimenObject object);
    
    /**
     * Busca todas as informações de um espécime por ID
     */
    @Query("SELECT si FROM SpeciesInfo si WHERE si.object.id = :objectId ORDER BY si.observationDate DESC")
    List<SpeciesInfo> findByObjectIdOrderByObservationDateDesc(@Param("objectId") Long objectId);
    
    /**
     * Busca a informação mais recente de um espécime por ID
     */
    @Query("SELECT si FROM SpeciesInfo si WHERE si.object.id = :objectId ORDER BY si.observationDate DESC LIMIT 1")
    Optional<SpeciesInfo> findLatestByObjectId(@Param("objectId") Long objectId);
    
    /**
     * Conta quantas observações um espécime possui
     */
    long countByObject(SpecimenObject object);
}
