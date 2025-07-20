package org.example.librarymanagementsystem.service;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.BookDto; // Assuming you have this DTO
import org.example.librarymanagementsystem.model.Author;
import org.example.librarymanagementsystem.model.Book;
import org.example.librarymanagementsystem.model.Publisher;
import org.example.librarymanagementsystem.repo.AuthorRepository;
import org.example.librarymanagementsystem.repo.BookRepository;
import org.example.librarymanagementsystem.repo.PublisherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    @Transactional
    public BookDto createBook(BookDto bookDto) {
        Book book = new Book();

        // Find and set the publisher
        Publisher publisher = publisherRepository.findById(bookDto.getPublisherId())
                .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + bookDto.getPublisherId()));
        book.setPublisher(publisher);

        // Find and set the authors
        Set<Author> authors = new HashSet<>(authorRepository.findAllById(bookDto.getAuthorIds()));
        if (authors.size() != bookDto.getAuthorIds().size()) {
            throw new RuntimeException("One or more authors not found.");
        }
        book.setAuthors(authors);

        // Set book details
        book.setTitle(bookDto.getTitle());
        book.setIsbn(bookDto.getIsbn());

        Book savedBook = bookRepository.save(book);
        return convertToDto(savedBook);
    }

    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // You would add methods for getById, update, delete, and search here...

    private BookDto convertToDto(Book book) {
        BookDto dto = new BookDto();
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        if (book.getPublisher() != null) {
            dto.setPublisherId(book.getPublisher().getId());
        }
        if (book.getAuthors() != null) {
            dto.setAuthorIds(book.getAuthors().stream()
                    .map(Author::getId)
                    .collect(Collectors.toSet()));
        }
        return dto;
    }

    public void deleteBook(Long id)
    {
        bookRepository.deleteById(id);

    }
}