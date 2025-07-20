package org.example.librarymanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.UserCreationDto;
import org.example.librarymanagementsystem.dto.UserResponseDto;
import org.example.librarymanagementsystem.enums.ERole;
import org.example.librarymanagementsystem.model.Role;
import org.example.librarymanagementsystem.model.SystemUser;
import org.example.librarymanagementsystem.repo.RoleRepository;
import org.example.librarymanagementsystem.repo.SystemUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SystemUserService {

    private final SystemUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user with ROLE_USER assigned by default.
     */
    public UserResponseDto createUser(UserCreationDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

        SystemUser user = SystemUser.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(Set.of(userRole))
                .build();

        SystemUser savedUser = userRepository.save(user);
        return mapToDto(savedUser);
    }

    /**
     * Retrieves all users in a safe format (no password, no internal details).
     */
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Gets a single user by ID.
     */
    public UserResponseDto getUserById(Long id) {
        SystemUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDto(user);
    }

    /**
     * Deletes a user safely.
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    /**
     * Converts SystemUser entity to a safe UserResponseDto.
     */
    private UserResponseDto mapToDto(SystemUser user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRoles(
                user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet())
        );
        return dto;
    }
    public UserResponseDto createUserWithRole(UserCreationDto userDto, ERole roleEnum) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        Role role = roleRepository.findByName(roleEnum)
                .orElseThrow(() -> new RuntimeException(roleEnum.name() + " not found"));

        SystemUser user = SystemUser.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(Collections.singleton(role))
                .build();

        SystemUser saved = userRepository.save(user);
        return mapToDto(saved);
    }
}
