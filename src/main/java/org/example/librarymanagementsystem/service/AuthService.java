package org.example.librarymanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.AuthRequestDto;
import org.example.librarymanagementsystem.dto.AuthResponseDto;
import org.example.librarymanagementsystem.dto.UserCreationDto;
import org.example.librarymanagementsystem.model.SystemUser;
import org.example.librarymanagementsystem.repo.SystemUserRepository;
import org.example.librarymanagementsystem.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final SystemUserService userService;
    private final SystemUserRepository systemUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDto login(AuthRequestDto request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SystemUser user = systemUserRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtils.generateToken(user);
        return new AuthResponseDto(token);
    }

    public void registerUser(UserCreationDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        userService.createUser(dto);
    }
}
