package br.com.inv.florestal.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inv.florestal.api.dto.RoleRepresentation;
import br.com.inv.florestal.api.service.RoleService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("roles")
public class RoleController {
    
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleRepresentation>> findAll() {
        List<RoleRepresentation> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }
}
