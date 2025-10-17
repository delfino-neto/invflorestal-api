package br.com.inv.florestal.api.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.inv.florestal.api.dto.UserRepresentation;
import br.com.inv.florestal.api.models.user.User;

public interface UserRepository extends JpaRepository<User, Long> {


    @Query("""
       SELECT new br.com.inv.florestal.api.dto.UserRepresentation(u) 
       FROM User u
       INNER JOIN u.roles
    """)
    Page<UserRepresentation> search(PageRequest page);

    Optional<User> findByEmail(String email);
    
}
