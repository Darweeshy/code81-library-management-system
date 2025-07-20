package org.example.librarymanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.AuthRequestDto;
import org.example.librarymanagementsystem.dto.AuthResponseDto;
import org.example.librarymanagementsystem.dto.UserCreationDto;
import org.example.librarymanagementsystem.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserCreationDto dto) {
        authService.registerUser(dto);
        return ResponseEntity.ok("User registered successfully");
    }
}
