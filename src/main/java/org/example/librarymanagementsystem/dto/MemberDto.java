package org.example.librarymanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private Long id;
    private String fullName; // ✅ should match entity
    private String email;
}
