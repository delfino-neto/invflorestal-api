package br.com.inv.florestal.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.inv.florestal.api.dto.UserRepresentation;
import br.com.inv.florestal.api.dto.UserRequest;
import br.com.inv.florestal.api.dto.UserUpdateRequest;
import br.com.inv.florestal.api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
    
    private final UserService userService;

    @GetMapping("search")
    public ResponseEntity<Page<UserRepresentation>> search(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "10") Integer size,
        @RequestParam(value = "searchTerm", required = false) String searchTerm
    ) {
        Page<UserRepresentation> users = userService.search(page, size, searchTerm);
        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserRepresentation> findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserRepresentation> create(@Valid @RequestBody UserRequest request) {
        UserRepresentation user = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserRepresentation> update(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        UserRepresentation user = userService.update(id, request);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}/toggle-status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserRepresentation> toggleStatus(@PathVariable Long id) {
        UserRepresentation user = userService.toggleStatus(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("{id}/toggle-lock")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserRepresentation> toggleLock(@PathVariable Long id) {
        UserRepresentation user = userService.toggleLock(id);
        return ResponseEntity.ok(user);
    }
}
