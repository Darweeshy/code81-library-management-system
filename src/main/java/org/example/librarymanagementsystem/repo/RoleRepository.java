package org.example.librarymanagementsystem.repo;

import org.example.librarymanagementsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
