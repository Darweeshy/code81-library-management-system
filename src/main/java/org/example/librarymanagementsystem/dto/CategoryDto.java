package org.example.librarymanagementsystem.dto;

import lombok.Data;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private Long parentId; // null if root category
}
