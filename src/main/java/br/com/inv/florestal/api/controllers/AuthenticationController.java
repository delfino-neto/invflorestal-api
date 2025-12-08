package br.com.inv.florestal.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inv.florestal.api.dto.AuthenticationRequest;
import br.com.inv.florestal.api.dto.AuthenticationResponse;
import br.com.inv.florestal.api.dto.RefreshTokenRequest;
import br.com.inv.florestal.api.dto.RegistrationRequest;
import br.com.inv.florestal.api.models.user.User;
import br.com.inv.florestal.api.repository.UserRepository;
import br.com.inv.florestal.api.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final AuthenticationService service;
    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() 
            || authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.ok(null);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        User user = userRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        Map<String, Object> response = Map.of(
            "id", user.getId(),
            "email", userDetails.getUsername(),
            "fullName", user.fullName(),
            "roles", user.getRoles().stream().map(r -> r.getName()).toList()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request) {
        service.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody @Valid AuthenticationRequest request,
        HttpServletResponse response
    ) {
        AuthenticationResponse auth = service.authenticate(request);

        Cookie cookie = new Cookie("auth-token-jwt", auth.getToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);

        response.addCookie(cookie);

        return ResponseEntity.ok(auth);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("auth-token-jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok().body(Map.of("message", "Logout realizado com sucesso"));
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(
        @RequestBody @Valid RefreshTokenRequest request
    ) {
        try {
            AuthenticationResponse response = service.refreshToken(request.getRefreshToken());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }
    }
    
}
