package org.example.librarymanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.UserCreationDto;
import org.example.librarymanagementsystem.model.Role;
import org.example.librarymanagementsystem.model.SystemUser;
import org.example.librarymanagementsystem.repo.RoleRepository;
import org.example.librarymanagementsystem.repo.SystemUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SystemUserService {

    private final SystemUserRepository systemUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(UserCreationDto userDto) {
        // Check if username already exists
        if (systemUserRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Create and populate new user
        SystemUser user = new SystemUser();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Convert RoleType enum values to String names (e.g., "ROLE_ADMIN")
        Set<String> roleNames = userDto.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.toSet());

        // Fetch Role entities from DB
        Set<Role> roles = new HashSet<>(roleRepository.findAllByNameIn(roleNames));
        user.setRoles(roles);

        systemUserRepository.save(user);
    }
}
