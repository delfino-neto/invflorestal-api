package br.com.inv.florestal.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.inv.florestal.api.dto.UserRepresentation;
import br.com.inv.florestal.api.models.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
       SELECT DISTINCT new br.com.inv.florestal.api.dto.UserRepresentation(u) 
       FROM User u
       LEFT JOIN u.roles
       WHERE :searchTerm IS NULL 
          OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', CAST(:searchTerm AS string), '%'))
          OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', CAST(:searchTerm AS string), '%'))
          OR LOWER(u.email) LIKE LOWER(CONCAT('%', CAST(:searchTerm AS string), '%'))
    """)
    Page<UserRepresentation> search(String searchTerm, Pageable page);

    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
}
