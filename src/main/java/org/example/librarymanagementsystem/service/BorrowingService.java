package org.example.librarymanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.BorrowingRequestDto;
import org.example.librarymanagementsystem.dto.TransactionResponseDto;
import org.example.librarymanagementsystem.model.Book;
import org.example.librarymanagementsystem.model.BorrowingTransaction;
import org.example.librarymanagementsystem.model.Member;
import org.example.librarymanagementsystem.repo.BookRepository;
import org.example.librarymanagementsystem.repo.BorrowingTransactionRepository;
import org.example.librarymanagementsystem.repo.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowingService {

    private final BorrowingTransactionRepository transactionRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private static final int BORROWING_DAYS = 14;

    @Transactional
    public TransactionResponseDto borrowBook(BorrowingRequestDto request) {
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + request.getBookId()));

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + request.getMemberId()));


        // Check if the book is already borrowed and not yet returned
        if (transactionRepository.existsByBookIdAndReturnDateIsNull(book.getId())) {
            throw new IllegalStateException("Book is currently unavailable.");
        }

        BorrowingTransaction transaction = new BorrowingTransaction();
        transaction.setBook(book);
        transaction.setMember(member);
        transaction.setBorrowDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now().plusDays(BORROWING_DAYS));

        BorrowingTransaction savedTransaction = transactionRepository.save(transaction);
        return convertToDto(savedTransaction);
    }

    @Transactional
    public TransactionResponseDto returnBook(Long transactionId) {
        BorrowingTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + transactionId));

        if (transaction.getReturnDate() != null) {
            throw new IllegalStateException("This book has already been returned on " + transaction.getReturnDate());
        }

        // Set the return date to mark the book as returned
        transaction.setReturnDate(LocalDate.now());

        BorrowingTransaction savedTransaction = transactionRepository.save(transaction);
        return convertToDto(savedTransaction);
    }

    private TransactionResponseDto convertToDto(BorrowingTransaction transaction) {
        TransactionResponseDto dto = new TransactionResponseDto();
        dto.setId(transaction.getId());
        dto.setBorrowDate(transaction.getBorrowDate());
        dto.setDueDate(transaction.getDueDate());
        dto.setReturnDate(transaction.getReturnDate());

        if (transaction.getBook() != null) {
            dto.setBookId(transaction.getBook().getId());
            dto.setBookTitle(transaction.getBook().getTitle());
        }

        if (transaction.getMember() != null) {
            dto.setMemberId(transaction.getMember().getId());
            dto.setMemberName(transaction.getMember().getFullName());
        }

        return dto;
    }
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> getOverdueTransactions() {
        LocalDate today = LocalDate.now();
        List<BorrowingTransaction> overdue = transactionRepository.findByDueDateBeforeAndReturnDateIsNull(today);
        return overdue.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


}