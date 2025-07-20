package org.example.librarymanagementsystem.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private Set<String> roles;
}
