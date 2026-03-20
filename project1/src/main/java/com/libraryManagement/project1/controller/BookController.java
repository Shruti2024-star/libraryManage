package com.libraryManagement.project1.controller;


import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.libraryManagement.project1.dto.BookResponse;
import com.libraryManagement.project1.dto.CreateBookRequest;
import com.libraryManagement.project1.dto.UpdateBookRequest;
import com.libraryManagement.project1.entities.Permission;
import com.libraryManagement.project1.services.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // ADD BOOK
    @PreAuthorize("hasAuthority('BOOK_CREATE')")
    @PostMapping
    public BookResponse addBook(@Valid @RequestBody CreateBookRequest request){
        return bookService.addBook(request);
    }

    // GET ALL BOOKS
    @PreAuthorize("hasAuthority('BOOK_VIEWALL')")
    @GetMapping
    public List<BookResponse> getAllBooks(){
        return bookService.getAllBooks();
    }

    // GET BOOK BY ID
    @PreAuthorize("hasAuthority('BOOK_VIEW')")
    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable Long id){
        return bookService.getBookById(id);
    }

    // UPDATE BOOK
    @PreAuthorize("hasAuthority('BOOK_UPDATE')")
    @PutMapping("/{id}")
    public BookResponse updateBook( @Valid @PathVariable Long id,
                                   @RequestBody UpdateBookRequest request){
        return bookService.updateBook(id, request);
    }

    // DELETE BOOK
    @PreAuthorize("hasAuthority('BOOK_DELETE')")
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return "Book deleted successfully";
    }
    
    //SEARCH BOOK
    @PreAuthorize("hasAuthority('BOOK_SEARCH')")
    @GetMapping("/search")
    public List<BookResponse> searchBooks(@RequestParam String keyword){
        return bookService.searchBooks(keyword);
    }
}