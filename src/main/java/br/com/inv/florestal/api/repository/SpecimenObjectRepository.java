package br.com.inv.florestal.api.repository;

import br.com.inv.florestal.api.models.specimen.SpecimenObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpecimenObjectRepository extends JpaRepository<SpecimenObject, Long> {
    
    @Query("SELECT s FROM SpecimenObject s WHERE s.plot.area.id = :areaId")
    List<SpecimenObject> findByCollectionAreaId(@Param("areaId") Long areaId);
    
    @Query("SELECT COUNT(s) FROM SpecimenObject s WHERE s.createdAt >= :since")
    Long countRecentSpecimens(@Param("since") LocalDateTime since);
    
    @Query("""
        SELECT s.species.commonName, COUNT(s)
        FROM SpecimenObject s
        WHERE s.species.commonName IS NOT NULL
        GROUP BY s.species.commonName
        ORDER BY COUNT(s) DESC
    """)
    List<Object[]> findTopSpeciesDistribution();
    
    @Query("""
        SELECT s.id, s.species.commonName, CONCAT(s.observer.firstName, ' ', s.observer.lastName), s.createdAt
        FROM SpecimenObject s
        ORDER BY s.createdAt DESC
    """)
    List<Object[]> findRecentActivities();
}
