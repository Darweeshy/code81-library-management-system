package org.example.librarymanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.MemberDto;
import org.example.librarymanagementsystem.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
	public ResponseEntity<MemberDto> createMember(@RequestBody MemberDto memberDto) {
		return ResponseEntity.ok(memberService.createMember(memberDto));
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN', 'STAFF')")
	public ResponseEntity<List<MemberDto>> getAllMembers() {
		return ResponseEntity.ok(memberService.findAll());
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN', 'STAFF')")
	public ResponseEntity<MemberDto> getMember(@PathVariable Long id) {
		return ResponseEntity.ok(memberService.findById(id));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
	public ResponseEntity<MemberDto> updateMember(@PathVariable Long id, @RequestBody MemberDto memberDto) {
		return ResponseEntity.ok(memberService.updateMember(id, memberDto));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
		memberService.deleteMember(id);
		return ResponseEntity.noContent().build();
	}

}
