package org.example.librarymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String isbn;
    private String title;
    private String language;
    private String edition;
    private Integer publicationYear;
    private String summary;
    private String coverImageUrl;
    private List<Long> authorIds;
    private Long publisherId;
    private Long categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
