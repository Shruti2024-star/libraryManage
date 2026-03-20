package com.libraryManagement.project1.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BorrowedBookResponse {

    private Long bookId;
    private String title;
    private String author;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String status;
    private double fine;
    private Long id;
    

}