package org.example.librarymanagementsystem.repo;

import org.example.librarymanagementsystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // 🔍 Case-insensitive search by title (partial match)
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Optional: exact ISBN lookup (if needed for validation)
    boolean existsByIsbn(String isbn);

    // Optional: for future dashboard/reporting
    List<Book> findByPublisherId(Long publisherId);
}
