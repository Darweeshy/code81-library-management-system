package org.example.librarymanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.BorrowingRequestDto;
import org.example.librarymanagementsystem.dto.TransactionResponseDto;
import org.example.librarymanagementsystem.service.BorrowingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class BorrowingTransactionController {

	private final BorrowingService borrowingService;

	@PostMapping("/borrow")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<TransactionResponseDto> borrowBook(@RequestBody BorrowingRequestDto requestDto) {
		return ResponseEntity.ok(borrowingService.borrowBook(requestDto));
	}

	@PostMapping("/return/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<TransactionResponseDto> returnBook(@PathVariable Long id) {
		return ResponseEntity.ok(borrowingService.returnBook(id));
	}

	@GetMapping("/overdue")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<List<TransactionResponseDto>> getOverdueTransactions() {
		return ResponseEntity.ok(borrowingService.getOverdueTransactions());
	}
}
