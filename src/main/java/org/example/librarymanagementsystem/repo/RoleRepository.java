package org.example.librarymanagementsystem.repo;

import org.example.librarymanagementsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findAllByNameIn(Set<String> names); // expects Set<String>
}