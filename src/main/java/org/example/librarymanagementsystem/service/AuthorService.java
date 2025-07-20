package org.example.librarymanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.AuthorCreationDto;
import org.example.librarymanagementsystem.dto.AuthorDto;
import org.example.librarymanagementsystem.model.Author;
import org.example.librarymanagementsystem.repo.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorDto createAuthor(AuthorCreationDto dto) {
        Author author = Author.builder()
                .name(dto.getName())
                .build();
        Author saved = authorRepository.save(author);
        return mapToDto(saved);
    }

    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public AuthorDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id " + id));
        return mapToDto(author);
    }

    public AuthorDto updateAuthor(Long id, AuthorCreationDto dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id " + id));
        author.setName(dto.getName());
        Author updated = authorRepository.save(author);
        return mapToDto(updated);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    private AuthorDto mapToDto(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }
}
