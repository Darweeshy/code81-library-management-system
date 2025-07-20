package org.example.librarymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreationDto {
    private String isbn;
    private String title;
    private String language;
    private String edition;
    private Integer publicationYear;   // required
    private String summary;
    private String coverImageUrl;
    private List<Long> authorIds;      // multi-authors
    private Long publisherId;
    private Long categoryId;
}
