package org.example.librarymanagementsystem.repo;

import org.example.librarymanagementsystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
