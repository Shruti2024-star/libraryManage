package com.libraryManagement.project1.dto;

import jakarta.validation.constraints.*;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBookRequest {

    @NotNull(message = "Book ID is required")
    @Positive(message = "Book ID must be valid")
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 150, message = "Title must be between 2 and 150 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 characters")
    private String author;

    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^(?:\\d{10}|\\d{13})$", message = "ISBN must be 10 or 13 digits")
    private String isbn;

    @Positive(message = "Total copies must be greater than 0")
    private int totalCopies;

    @PositiveOrZero(message = "Available copies cannot be negative")
    private int availableCopies;
    

    @Pattern(regexp = "^(http|https)://.*$", message = "Image URL must be valid")
    private String imageUrl;
    
    @NotBlank(message = "Author is required")
    private String description;

}