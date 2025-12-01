package br.com.inv.florestal.api.repository;

import br.com.inv.florestal.api.models.species.SpeciesTaxonomy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpeciesTaxonomyRepository extends JpaRepository<SpeciesTaxonomy, Long> {
    
    @Query("SELECT DISTINCT s.family FROM SpeciesTaxonomy s WHERE s.family IS NOT NULL ORDER BY s.family")
    List<String> findDistinctFamilies();
    
    @Query("SELECT DISTINCT s.genus FROM SpeciesTaxonomy s WHERE s.genus IS NOT NULL ORDER BY s.genus")
    List<String> findDistinctGenera();
    
    @Query("""
        FROM SpeciesTaxonomy s WHERE
            (
                :searchTerm IS NULL OR
                LOWER(s.scientificName) LIKE LOWER(CONCAT('%', CAST(:searchTerm AS text), '%')) OR
                LOWER(s.commonName) LIKE LOWER(CONCAT('%', CAST(:searchTerm AS text), '%')) OR
                LOWER(s.code) LIKE LOWER(CONCAT('%', CAST(:searchTerm AS text), '%'))
            ) 
            AND
            (:family IS NULL OR s.family = :family) AND
            (:genus IS NULL OR s.genus = :genus)
    """)
    Page<SpeciesTaxonomy> searchWithFilters(
        @Param("searchTerm") String searchTerm,
        @Param("family") String family,
        @Param("genus") String genus,
        Pageable pageable
    );
    
    @Query("""
        SELECT s FROM SpeciesTaxonomy s WHERE
            LOWER(s.scientificName) = LOWER(:name) OR
            LOWER(s.commonName) = LOWER(:name) OR
            LOWER(s.family) = LOWER(:name) OR
            LOWER(s.genus) = LOWER(:name)
    """)
    List<SpeciesTaxonomy> findByAnyName(@Param("name") String name);
}
