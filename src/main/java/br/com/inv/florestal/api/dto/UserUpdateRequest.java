package br.com.inv.florestal.api.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserUpdateRequest {
    
    private String firstName;
    
    private String lastName;
    
    private LocalDate dateOfBirth;
    
    private String email;
    
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    private Boolean enabled;
    
    private Boolean accountLocked;
    
    private List<Long> roleIds;
}
