package org.example.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "system_users")
public class SystemUser extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // Should be encoded (BCrypt)

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "system_user_roles",
            joinColumns = @JoinColumn(name = "system_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}
