package org.example.librarymanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.AuthResponseDto;
import org.example.librarymanagementsystem.dto.LoginRequestDto;
import org.example.librarymanagementsystem.dto.UserCreationDto;
import org.example.librarymanagementsystem.enums.ERole;
import org.example.librarymanagementsystem.model.Role;
import org.example.librarymanagementsystem.model.SystemUser;
import org.example.librarymanagementsystem.repo.RoleRepository;
import org.example.librarymanagementsystem.repo.SystemUserRepository;
import org.example.librarymanagementsystem.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final SystemUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    /**
     * Registers a user with ROLE_USER only — secure signup flow.
     */
    @Transactional
    public AuthResponseDto registerUser(UserCreationDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new IllegalStateException("ROLE_USER not found in DB"));

        SystemUser user = SystemUser.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(Collections.singleton(userRole))
                .build();

        userRepository.save(user);

        // Auto login on registration
        String token = jwtUtils.generateToken(user.getUsername(), userRole.getName().name());
        return new AuthResponseDto(token);
    }

    /**
     * Authenticates user and returns JWT token.
     */
    // DON'T log manually here
    public AuthResponseDto login(LoginRequestDto dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        SystemUser user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String roleName = user.getRoles().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User has no roles assigned"))
                .getName().name();

        String token = jwtUtils.generateToken(user.getUsername(), roleName);

        return new AuthResponseDto(token); // Just return — let the listener handle logging
    }


}
