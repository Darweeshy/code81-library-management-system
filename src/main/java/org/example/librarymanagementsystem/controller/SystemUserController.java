package org.example.librarymanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.UserCreationDto;
import org.example.librarymanagementsystem.dto.UserResponseDto;
import org.example.librarymanagementsystem.enums.ERole;
import org.example.librarymanagementsystem.service.SystemUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class SystemUserController {

	private final SystemUserService systemUserService;

	/** ADMIN can create STAFF accounts */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/staff")
	public ResponseEntity<UserResponseDto> createStaff(@RequestBody UserCreationDto dto) {
		return ResponseEntity.ok(
				systemUserService.createUserWithRole(dto, ERole.ROLE_STAFF)
		);
	}

	/** STAFF can create LIBRARIAN accounts */
	@PreAuthorize("hasRole('STAFF')")
	@PostMapping("/librarian")
	public ResponseEntity<UserResponseDto> createLibrarian(@RequestBody UserCreationDto dto) {
		return ResponseEntity.ok(
				systemUserService.createUserWithRole(dto, ERole.ROLE_LIBRARIAN)
		);
	}

	/** ADMIN & STAFF can list users */
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	@GetMapping("/list")
	public ResponseEntity<List<UserResponseDto>> getAllUsers() {
		return ResponseEntity.ok(systemUserService.getAllUsers());
	}

	/** ADMIN only can delete users */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		systemUserService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}
