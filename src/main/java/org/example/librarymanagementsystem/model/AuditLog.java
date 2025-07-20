package org.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String action;       // e.g., LOGIN_SUCCESS, LOGIN_FAILED
    private LocalDateTime timestamp;
    private String ipAddress;    // optional

    private String details;      // e.g., error messages
}
