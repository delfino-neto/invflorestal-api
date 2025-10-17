package br.com.inv.florestal.api.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "O nome não pode estar vazio")
    @NotBlank(message = "O nome não pode ser nulo")
    private String firstName;

    @NotEmpty(message = "O sobrenome não pode estar vazio")
    @NotBlank(message = "O sobrenome não pode ser nulo")
    private String lastName;

    private LocalDate dateOfBirth;

    @Email
    @NotEmpty(message = "O e-mail não pode estar vazio")
    @NotBlank(message = "O e-mail não pode ser nulo")
    private String email;

    @NotEmpty(message = "A senha não pode estar vazio")
    @NotBlank(message = "A senha não pode ser nulo")
    @Size(min = 8, message = "passoword size must be greater than 8 characters")
    private String password;
}
