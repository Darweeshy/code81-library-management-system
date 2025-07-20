package org.example.librarymanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.BookCreationDto;
import org.example.librarymanagementsystem.dto.BookDto;
import org.example.librarymanagementsystem.model.Author;
import org.example.librarymanagementsystem.model.Book;
import org.example.librarymanagementsystem.model.Category;
import org.example.librarymanagementsystem.model.Publisher;
import org.example.librarymanagementsystem.repo.AuthorRepository;
import org.example.librarymanagementsystem.repo.BookRepository;
import org.example.librarymanagementsystem.repo.CategoryRepository;
import org.example.librarymanagementsystem.repo.PublisherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    public BookDto createBook(BookCreationDto dto) {
        if (dto.getPublicationYear() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "publicationYear is required");
        }

        // load & validate authors
        List<Author> authors = authorRepository.findAllById(dto.getAuthorIds());
        if (authors.size() != dto.getAuthorIds().size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more authors not found");
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found"));

        Book book = Book.builder()
                .isbn(dto.getIsbn())
                .title(dto.getTitle())
                .language(dto.getLanguage())
                .edition(dto.getEdition())
                .publicationYear(dto.getPublicationYear())
                .summary(dto.getSummary())
                .coverImageUrl(dto.getCoverImageUrl())
                .authors(new HashSet<>(authors))
                .category(category)
                .publisher(publisher)
                .build();

        Book saved = bookRepository.save(book);
        return mapToDto(saved);
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        return mapToDto(book);
    }

    public BookDto updateBook(Long id, BookCreationDto dto) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        if (dto.getPublicationYear() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "publicationYear is required");
        }

        // validate authors again
        List<Author> authors = authorRepository.findAllById(dto.getAuthorIds());
        if (authors.size() != dto.getAuthorIds().size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more authors not found");
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found"));

        existing.setIsbn(dto.getIsbn());
        existing.setTitle(dto.getTitle());
        existing.setLanguage(dto.getLanguage());
        existing.setEdition(dto.getEdition());
        existing.setPublicationYear(dto.getPublicationYear());
        existing.setSummary(dto.getSummary());
        existing.setCoverImageUrl(dto.getCoverImageUrl());
        existing.setAuthors(new HashSet<>(authors));
        existing.setCategory(category);
        existing.setPublisher(publisher);

        Book updated = bookRepository.save(existing);
        return mapToDto(updated);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        bookRepository.deleteById(id);
    }

    private BookDto mapToDto(Book b) {
        return new BookDto(
                b.getId(),
                b.getIsbn(),
                b.getTitle(),
                b.getLanguage(),
                b.getEdition(),
                b.getPublicationYear(),
                b.getSummary(),
                b.getCoverImageUrl(),
                b.getAuthors().stream().map(Author::getId).collect(Collectors.toList()),
                b.getPublisher().getId(),
                b.getCategory().getId(),
                b.getCreatedAt(),
                b.getUpdatedAt()
        );
    }
}
