package com.libraryManagement.project1.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueBookRequest {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be a valid positive number")
    private Long userId;

    @NotNull(message = "Book ID is required")
    @Positive(message = "Book ID must be a valid positive number")
    private Long bookId;
}