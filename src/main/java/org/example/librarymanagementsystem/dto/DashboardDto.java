package org.example.librarymanagementsystem.dto;

import lombok.Data;

@Data
public class DashboardDto {
    private long totalBooks;
    private long totalMembers;
    private long totalBorrowed;
}
