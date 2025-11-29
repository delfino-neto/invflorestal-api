package br.com.inv.florestal.api.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.inv.florestal.api.dto.AuthenticationRequest;
import br.com.inv.florestal.api.dto.AuthenticationResponse;
import br.com.inv.florestal.api.dto.RegistrationRequest;
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
        // sendValidationEmail();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        HashMap<String,Object> claims = new HashMap<String, Object>();
        User user = (User) auth.getPrincipal();
        claims.put("fullName", user.fullName());

        // Update last login timestamp
        user.setLastLogin(java.time.LocalDateTime.now());
        userRepository.save(user);

        String token = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder().token(token).build();
    }

}
