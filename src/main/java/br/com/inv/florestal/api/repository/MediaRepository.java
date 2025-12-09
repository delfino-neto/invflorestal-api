package br.com.inv.florestal.api.repository;

import br.com.inv.florestal.api.models.media.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    Page<Media> findByObjectId(Long objectId, Pageable pageable);
    Optional<Media> findByUrl(String url);
}
