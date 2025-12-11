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
    List<SpeciesInfo> findByObjectOrderByObservationDateDesc(SpecimenObject object);
    Page<SpeciesInfo> findByObjectOrderByObservationDateDesc(SpecimenObject object, Pageable pageable);

    @Query("SELECT si FROM SpeciesInfo si WHERE si.object = :object ORDER BY si.observationDate DESC LIMIT 1")
    Optional<SpeciesInfo> findLatestByObject(@Param("object") SpecimenObject object);

    @Query("SELECT si FROM SpeciesInfo si WHERE si.object.id = :objectId ORDER BY si.observationDate DESC")
    List<SpeciesInfo> findByObjectIdOrderByObservationDateDesc(@Param("objectId") Long objectId);

    @Query("SELECT si FROM SpeciesInfo si WHERE si.object.id = :objectId ORDER BY si.observationDate DESC LIMIT 1")
    Optional<SpeciesInfo> findLatestByObjectId(@Param("objectId") Long objectId);
    
    long countByObject(SpecimenObject object);
}
