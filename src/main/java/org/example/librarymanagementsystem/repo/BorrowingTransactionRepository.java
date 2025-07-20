package org.example.librarymanagementsystem.repo;

import org.example.librarymanagementsystem.model.BorrowingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingTransactionRepository extends JpaRepository<BorrowingTransaction, Long> {

    // This method checks for an active borrowing transaction for a specific book.
    boolean existsByBookIdAndReturnDateIsNull(Long bookId);
}