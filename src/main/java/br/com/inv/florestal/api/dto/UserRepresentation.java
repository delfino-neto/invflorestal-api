package br.com.inv.florestal.api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import br.com.inv.florestal.api.models.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRepresentation {
    private Long id;
    private String firstName;
    private String lastName;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private boolean enabled;
    private boolean accountLocked;
    private List<String> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;

    public UserRepresentation(User user){
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.name = user.fullName();
        this.email = user.getEmail();
        this.dateOfBirth = user.getDateOfBirth();
        this.enabled = user.isEnabled();
        this.accountLocked = user.getAccountLocked();
        this.roles = user.getRoles().stream().map(role -> role.getName()).toList();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdateAt();
        this.lastLogin = user.getLastLogin();
    }
}
