package org.example.librarymanagementsystem.repo;

import org.example.librarymanagementsystem.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    boolean existsByName(String name);
}
