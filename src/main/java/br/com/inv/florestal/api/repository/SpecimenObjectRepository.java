package br.com.inv.florestal.api.repository;

import br.com.inv.florestal.api.models.specimen.SpecimenObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecimenObjectRepository extends JpaRepository<SpecimenObject, Long> {
    
    @Query("SELECT s FROM SpecimenObject s WHERE s.plot.area.id = :areaId")
    List<SpecimenObject> findByCollectionAreaId(@Param("areaId") Long areaId);
}
