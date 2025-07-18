package org.example.librarymanagementsystem.repo;

import org.example.librarymanagementsystem.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
