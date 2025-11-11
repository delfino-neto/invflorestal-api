package br.com.inv.florestal.api.repository;

import br.com.inv.florestal.api.models.sync.SyncLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyncLogRepository extends JpaRepository<SyncLog, Long> {
}
