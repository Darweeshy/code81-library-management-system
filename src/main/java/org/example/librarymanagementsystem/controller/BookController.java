package org.example.librarymanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.BookCreationDto;
import org.example.librarymanagementsystem.dto.BookDto;
import org.example.librarymanagementsystem.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;

	/** ADMIN and LIBRARIAN can create books */
	@PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
	@PostMapping
	public ResponseEntity<BookDto> createBook(@RequestBody BookCreationDto dto) {
		return ResponseEntity.ok(bookService.createBook(dto));
	}

	/** Anyone authenticated can list books */
	@GetMapping
	public ResponseEntity<List<BookDto>> getAllBooks() {
		return ResponseEntity.ok(bookService.getAllBooks());
	}

	/** Anyone authenticated can view a single book */
	@GetMapping("/{id}")
	public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
		return ResponseEntity.ok(bookService.getBookById(id));
	}

	/** ADMIN only can update books */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<BookDto> updateBook(
			@PathVariable Long id,
			@RequestBody BookCreationDto dto
	) {
		return ResponseEntity.ok(bookService.updateBook(id, dto));
	}

	/** ADMIN only can delete books */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
		return ResponseEntity.noContent().build();
	}
}
