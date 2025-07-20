package org.example.librarymanagementsystem.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.enums.ERole;
import org.example.librarymanagementsystem.model.Role;
import org.example.librarymanagementsystem.repo.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        Arrays.stream(ERole.values()).forEach(roleEnum -> {
            roleRepository.findByName(roleEnum).orElseGet(() -> {
                Role role = new Role();
                role.setName(roleEnum);
                return roleRepository.save(role);
            });
        });
    }
}
