package br.com.inv.florestal.api.repository;

import br.com.inv.florestal.api.models.collection.CollectionArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionAreaRepository extends JpaRepository<CollectionArea, Long> {
}
