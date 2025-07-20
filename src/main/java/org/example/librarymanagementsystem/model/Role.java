package org.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.librarymanagementsystem.enums.RoleType;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleType name;
}
