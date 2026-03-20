package com.libraryManagement.project1.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "issue_records")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class IssueRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many issue records belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Many issue records belong to one book
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDate issueDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    private double fine;

    private String status;
}