package com.libraryManagement.project1.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {

    @NotBlank(message = "Book title is required")
    @Size(min = 2, max = 150, message = "Title must be between 2 and 150 characters")
    private String title;

    @NotBlank(message = "Author name is required")
    @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 characters")
    private String author;

    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^(?:\\d{10}|\\d{13})$", message = "ISBN must be 10 or 13 digits")
    private String isbn;

    @Positive(message = "Total copies must be greater than 0")
    private int totalCopies;

    @NotNull(message = "Category is required")
    private Long categoryId;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Pattern(
        regexp = "^(http|https)://.*$",
        message = "Image URL must be a valid URL"
    )
    private String imageUrl;
}