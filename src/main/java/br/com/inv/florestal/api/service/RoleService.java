package br.com.inv.florestal.api.service;

import org.springframework.stereotype.Service;

import br.com.inv.florestal.api.dto.RoleRepresentation;
import br.com.inv.florestal.api.repository.RoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    
    private final RoleRepository roleRepository;

    public List<RoleRepresentation> findAll() {
        return roleRepository.findAll().stream()
                .map(RoleRepresentation::new)
                .collect(Collectors.toList());
    }
}
