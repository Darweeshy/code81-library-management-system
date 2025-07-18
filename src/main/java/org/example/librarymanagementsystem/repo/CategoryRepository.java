package org.example.librarymanagementsystem.repo;

import org.example.librarymanagementsystem.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
