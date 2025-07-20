package org.example.librarymanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.librarymanagementsystem.enums.RoleType;

import java.util.Set;

@Getter
@Setter
public class UserCreationDto {
    private String username;
    private String password;
    private Set<RoleType> roles;
}
