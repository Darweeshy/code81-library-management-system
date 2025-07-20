package org.example.librarymanagementsystem.repo;

import org.example.librarymanagementsystem.enums.ERole;
import org.example.librarymanagementsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
