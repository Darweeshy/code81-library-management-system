package org.example.librarymanagementsystem.repo;

import org.example.librarymanagementsystem.model.BorrowingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BorrowingTransactionRepository extends JpaRepository<BorrowingTransaction, Long> {

    // This method checks for an active borrowing transaction for a specific book.
    boolean existsByBookIdAndReturnDateIsNull(Long bookId);
    List<BorrowingTransaction> findByDueDateBeforeAndReturnDateIsNull(LocalDate date);

}