package com.libraryManagement.project1.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be a valid number")
    private Long userId;

    @NotNull(message = "Book ID is required")
    @Positive(message = "Book ID must be a valid number")
    private Long bookId;
}