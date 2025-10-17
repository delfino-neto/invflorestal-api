package br.com.inv.florestal.api.dto;

import java.util.List;

import br.com.inv.florestal.api.models.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRepresentation {
    private String name;
    private String email;
    private List<String> roles;

    public UserRepresentation(User user){
        this.name = user.fullName();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream().map(role -> role.getName()).toList();
    }
}
