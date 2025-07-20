package org.example.librarymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AuditLogSimpleDto {
    private String username;
    private String role;
    private String action;
    private String details;
    private LocalDateTime timestamp;
}
