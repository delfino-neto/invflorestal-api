package br.com.inv.florestal.api.repository;

import br.com.inv.florestal.api.models.specimen.SpecimenObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecimenObjectRepository extends JpaRepository<SpecimenObject, Long> {
}
