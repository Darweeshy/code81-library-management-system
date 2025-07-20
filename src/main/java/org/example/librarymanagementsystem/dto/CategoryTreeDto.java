package org.example.librarymanagementsystem.dto;

import lombok.Data;
import java.util.List;

@Data
public class CategoryTreeDto {
    private Long id;
    private String name;
    private List<CategoryTreeDto> subcategories;
}
