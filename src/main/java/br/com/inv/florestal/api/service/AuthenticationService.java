package br.com.inv.florestal.api.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.inv.florestal.api.aspect.Auditable;
import br.com.inv.florestal.api.dto.AuthenticationRequest;
import br.com.inv.florestal.api.dto.AuthenticationResponse;
import br.com.inv.florestal.api.dto.RegistrationRequest;
import br.com.inv.florestal.api.models.user.RefreshToken;
import br.com.inv.florestal.api.models.user.Role;
import br.com.inv.florestal.api.models.user.User;
import br.com.inv.florestal.api.repository.RoleRepository;
import br.com.inv.florestal.api.repository.UserRepository;
import br.com.inv.florestal.api.security.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;

    public void register(RegistrationRequest request){
        Role role = roleRepository.findByName("USER").orElseThrow(() -> new IllegalStateException("ROLE USER was not initialized"));
        
        User user = User.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .accountLocked(false)
        .enabled(true)
        .roles(List.of(role))
        .build();

        userRepository.save(user);
    }

    @Auditable(action = br.com.inv.florestal.api.models.audit.AuditLog.AuditAction.LOGIN, entityName = "User", description = "Login realizado")
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        HashMap<String,Object> claims = new HashMap<String, Object>();
        User user = (User) auth.getPrincipal();
        claims.put("fullName", user.fullName());

        user.setLastLogin(java.time.LocalDateTime.now());
        userRepository.save(user);

        String token = jwtService.generateToken(claims, user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
        
        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public AuthenticationResponse refreshToken(String refreshTokenStr) {
        return refreshTokenService.findByToken(refreshTokenStr)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    HashMap<String, Object> claims = new HashMap<>();
                    claims.put("fullName", user.fullName());
                    String newToken = jwtService.generateToken(claims, user);
                    
                    RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getEmail());
                    
                    return AuthenticationResponse.builder()
                            .token(newToken)
                            .refreshToken(newRefreshToken.getToken())
                            .build();
                })
                .orElseThrow(() -> new RuntimeException("Refresh token inválido"));
    }

}
