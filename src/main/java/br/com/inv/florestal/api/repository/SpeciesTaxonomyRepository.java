package br.com.inv.florestal.api.repository;

import br.com.inv.florestal.api.models.species.SpeciesTaxonomy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeciesTaxonomyRepository extends JpaRepository<SpeciesTaxonomy, Long> {
}
