package org.example.librarymanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BookDto {
    private String title;
    private String isbn;
    private Long publisherId;
    private Set<Long> authorIds;
}
