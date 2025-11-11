package br.com.inv.florestal.api.repository;

import br.com.inv.florestal.api.models.collection.Plot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlotRepository extends JpaRepository<Plot, Long> {
}
