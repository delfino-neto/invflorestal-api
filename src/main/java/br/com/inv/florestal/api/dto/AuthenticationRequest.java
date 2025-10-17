package br.com.inv.florestal.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {
    
    @NotEmpty(message = "O e-mail não pode estar vazio")
    @NotBlank(message = "O e-mail não pode ser nulo")
    @Email
    private String email;

    @NotEmpty(message = "A senha não pode estar vazia")
    @NotBlank(message = "A senha não pode ser nula")
    private String password;

}
