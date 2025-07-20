package org.example.librarymanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.BookDto;
import org.example.librarymanagementsystem.service.BookService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;

	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
	@PostMapping
	public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
		return ResponseEntity.ok(bookService.createBook(bookDto));
	}

	@GetMapping
	@Cacheable("books")
	public ResponseEntity<Page<BookDto>> getAllBooks(Pageable pageable) {
		return ResponseEntity.ok(bookService.findAllBooks(pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
		return ResponseEntity.ok(bookService.findById(id));
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
	@PutMapping("/{id}")
	public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
		return ResponseEntity.ok(bookService.updateBook(id, bookDto));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/search")
	public ResponseEntity<List<BookDto>> searchBooks(@RequestParam(required = false) String title,
													 @RequestParam(required = false) String author,
													 @RequestParam(required = false) String category) {
		return ResponseEntity.ok(bookService.searchBooks(title, author, category));
	}

	@GetMapping("/semantic")
	public ResponseEntity<List<BookDto>> semanticSearch(@RequestParam String query) {
		return ResponseEntity.ok(bookService.semanticSearch(query));
	}
}
