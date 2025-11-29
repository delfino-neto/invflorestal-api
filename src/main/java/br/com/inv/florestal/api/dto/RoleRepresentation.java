package br.com.inv.florestal.api.dto;

import br.com.inv.florestal.api.models.user.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRepresentation {
    private Long id;
    private String name;

    public RoleRepresentation(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
