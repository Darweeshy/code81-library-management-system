package org.example.librarymanagementsystem.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TransactionResponseDto {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private Long memberId;
    private String memberName;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
}