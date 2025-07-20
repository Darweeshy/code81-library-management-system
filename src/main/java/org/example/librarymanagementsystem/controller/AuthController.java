package org.example.librarymanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.AuthResponseDto;
import org.example.librarymanagementsystem.dto.LoginRequestDto;
import org.example.librarymanagementsystem.dto.UserCreationDto;
import org.example.librarymanagementsystem.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Registers a new user and returns a JWT token.
     */
    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> register(@RequestBody UserCreationDto dto) {
        return ResponseEntity.ok(authService.registerUser(dto));
    }

    /**
     * Authenticates user and returns a JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}
