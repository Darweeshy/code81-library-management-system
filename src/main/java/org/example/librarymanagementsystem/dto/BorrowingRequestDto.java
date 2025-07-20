package org.example.librarymanagementsystem.dto;

import lombok.Data;

@Data
public class BorrowingRequestDto {
    private Long bookId;
    private Long memberId;
}