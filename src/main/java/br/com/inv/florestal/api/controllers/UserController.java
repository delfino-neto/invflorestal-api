package br.com.inv.florestal.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.inv.florestal.api.dto.UserRepresentation;
import br.com.inv.florestal.api.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
    
    private final UserService userService;

    @GetMapping("search")
    public ResponseEntity<?> search(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        Page<UserRepresentation> users = userService.search(page, size);
        return ResponseEntity.ok(users);
    }

}
