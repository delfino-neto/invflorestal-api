package br.com.inv.florestal.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.inv.florestal.api.aspect.Auditable;
import br.com.inv.florestal.api.dto.UserRepresentation;
import br.com.inv.florestal.api.dto.UserRequest;
import br.com.inv.florestal.api.dto.UserUpdateRequest;
import br.com.inv.florestal.api.models.user.Role;
import br.com.inv.florestal.api.models.user.User;
import br.com.inv.florestal.api.repository.RoleRepository;
import br.com.inv.florestal.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<UserRepresentation> search(Integer page, Integer size, String searchTerm) {
        return this.userRepository.search(searchTerm, PageRequest.of(page, size));
    }

    public Optional<UserRepresentation> findById(Long id) {
        return userRepository.findById(id).map(UserRepresentation::new);
    }

    @Transactional
    @Auditable(action = br.com.inv.florestal.api.models.audit.AuditLog.AuditAction.CREATE, entityName = "User", description = "Novo usuário criado")
    public UserRepresentation create(UserRequest request) {
        // Verificar se o email já existe
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Buscar roles
        List<Role> roles = List.of();
        if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
            roles = roleRepository.findAllById(request.getRoleIds());
        }

        // Criar usuário
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(request.getEnabled() != null ? request.getEnabled() : true)
                .accountLocked(request.getAccountLocked() != null ? request.getAccountLocked() : false)
                .roles(roles)
                .build();

        user = userRepository.save(user);
        return new UserRepresentation(user);
    }

    @Transactional
    @Auditable(action = br.com.inv.florestal.api.models.audit.AuditLog.AuditAction.UPDATE, entityName = "User", description = "Usuário atualizado")
    public UserRepresentation update(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verificar se o email já existe (exceto para o próprio usuário)
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already exists");
            }
            user.setEmail(request.getEmail());
        }

        // Atualizar campos
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getDateOfBirth() != null) {
            user.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getEnabled() != null) {
            user.setEnabled(request.getEnabled());
        }
        if (request.getAccountLocked() != null) {
            user.setAccountLocked(request.getAccountLocked());
        }
        if (request.getRoleIds() != null) {
            List<Role> roles = roleRepository.findAllById(request.getRoleIds());
            user.setRoles(roles);
        }

        user = userRepository.save(user);
        return new UserRepresentation(user);
    }

    @Transactional
    @Auditable(action = br.com.inv.florestal.api.models.audit.AuditLog.AuditAction.DELETE, entityName = "User", description = "Usuário excluído")
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    @Auditable(action = br.com.inv.florestal.api.models.audit.AuditLog.AuditAction.STATUS_CHANGE, entityName = "User", description = "Status do usuário alterado")
    public UserRepresentation toggleStatus(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setEnabled(!user.isEnabled());
        user = userRepository.save(user);
        return new UserRepresentation(user);
    }

    @Transactional
    @Auditable(action = br.com.inv.florestal.api.models.audit.AuditLog.AuditAction.LOCK_CHANGE, entityName = "User", description = "Bloqueio do usuário alterado")
    public UserRepresentation toggleLock(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setAccountLocked(!user.getAccountLocked());
        user = userRepository.save(user);
        return new UserRepresentation(user);
    }
}
