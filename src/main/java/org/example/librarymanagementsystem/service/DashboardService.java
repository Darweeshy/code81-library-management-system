package org.example.librarymanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.DashboardDto;
import org.example.librarymanagementsystem.repo.BookRepository;
import org.example.librarymanagementsystem.repo.BorrowingTransactionRepository;
import org.example.librarymanagementsystem.repo.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BorrowingTransactionRepository borrowingRepository;

    public DashboardDto getStats() {
        DashboardDto dto = new DashboardDto();
        dto.setTotalBooks(bookRepository.count());
        dto.setTotalMembers(memberRepository.count());
        dto.setTotalBorrowed(borrowingRepository.count());
        // Optional: Add more stats like most borrowed books
        return dto;
    }
}