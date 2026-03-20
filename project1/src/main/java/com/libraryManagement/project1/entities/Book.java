package com.libraryManagement.project1.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "books")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    private String isbn;

    private int totalCopies;

    private int availableCopies;

    private String description;
    
   
    private String imageUrl;

    // Many books belong to one category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // One book can be issued many times
    @OneToMany(mappedBy = "book")
    private List<IssueRecord> issues;

    // One book can have many reservations
    @OneToMany(mappedBy = "book")
    private List<Reservation> reservations;
}