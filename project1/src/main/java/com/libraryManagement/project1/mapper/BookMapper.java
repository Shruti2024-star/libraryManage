package com.libraryManagement.project1.mapper;

import com.libraryManagement.project1.dto.CreateBookRequest;
import com.libraryManagement.project1.dto.BookResponse;
import com.libraryManagement.project1.entities.Book;

public class BookMapper {

    // Convert Entity -> Response DTO
    public static BookResponse toResponse(Book book){

        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .totalCopies(book.getTotalCopies())
                .availableCopies(book.getAvailableCopies())
                .description(book.getDescription())
                .imageUrl(book.getImageUrl())
                .build();
    }

    // Convert Request DTO -> Entity
    public static Book toEntity(CreateBookRequest request){

        return Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .totalCopies(request.getTotalCopies())
                .availableCopies(request.getTotalCopies())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .build();
    }
}