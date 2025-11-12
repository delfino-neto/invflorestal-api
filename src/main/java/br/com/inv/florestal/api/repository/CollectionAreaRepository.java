package br.com.inv.florestal.api.repository;

import br.com.inv.florestal.api.models.collection.CollectionArea;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionAreaRepository extends JpaRepository<CollectionArea, Long> {

    @Query("""
        SELECT ca FROM CollectionArea ca
        WHERE (:searchTerm IS NULL OR LOWER(ca.name) LIKE LOWER(CONCAT('%', CAST(:searchTerm AS string), '%')))
    """)
    Page<CollectionArea> search(String searchTerm, Pageable pageable);

}
