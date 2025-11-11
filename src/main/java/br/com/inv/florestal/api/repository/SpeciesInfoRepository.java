package br.com.inv.florestal.api.repository;

import br.com.inv.florestal.api.models.specimen.SpeciesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeciesInfoRepository extends JpaRepository<SpeciesInfo, Long> {
}
