package com.libraryManagement.project1.services;


import java.util.List;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.libraryManagement.project1.dto.BookResponse;
import com.libraryManagement.project1.dto.CreateBookRequest;
import com.libraryManagement.project1.dto.MessageResponse;
import com.libraryManagement.project1.dto.UpdateBookRequest;
import com.libraryManagement.project1.entities.Book;
import com.libraryManagement.project1.entities.Category;
import com.libraryManagement.project1.exceptions.BadRequestException;
import com.libraryManagement.project1.exceptions.ConflictException;
import com.libraryManagement.project1.exceptions.ResourceNotFoundException;
import com.libraryManagement.project1.mapper.BookMapper;
import com.libraryManagement.project1.repository.CategoryRepository;
import com.libraryManagement.project1.repository.bookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final bookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    // ADD BOOK
    public BookResponse addBook(CreateBookRequest request){

        // CHECK CATEGORY
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        // CHECK DUPLICATE ISBN
        if(bookRepository.existsByIsbn(request.getIsbn())){
            throw new ConflictException("Book with this ISBN already exists");
        }

        // VALIDATE COPIES
        if(request.getTotalCopies() <= 0){
            throw new BadRequestException("Total copies must be greater than 0");
        }



        Book book = BookMapper.toEntity(request);
        book.setCategory(category);

        Book savedBook = bookRepository.save(book);

        return BookMapper.toResponse(savedBook);
    }

    // GET BOOK BY ID
    public BookResponse getBookById(Long id){

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found with id: " + id));

        return BookMapper.toResponse(book);
    }

    // GET ALL BOOKS
    public List<BookResponse> getAllBooks(){

        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toResponse)
                .collect(Collectors.toList());
    }

    // UPDATE BOOK
    public BookResponse updateBook(Long id, UpdateBookRequest request){

        // CHECK BOOK
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found with id: " + id));

        // CHECK DUPLICATE ISBN
        if(!book.getIsbn().equals(request.getIsbn())
                && bookRepository.existsByIsbn(request.getIsbn())){
            throw new ConflictException("Another book with this ISBN already exists");
        }

        // VALIDATE COPIES
        if(request.getTotalCopies() <= 0){
            throw new BadRequestException("Total copies must be greater than 0");
        }

        if(request.getAvailableCopies() > request.getTotalCopies()){
            throw new BadRequestException("Available copies cannot exceed total copies");
        }

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setTotalCopies(request.getTotalCopies());
        book.setAvailableCopies(request.getAvailableCopies());

        Book updatedBook = bookRepository.save(book);

        return BookMapper.toResponse(updatedBook);
    }

    // DELETE BOOK
    public MessageResponse deleteBook(Long id){

        // CHECK BOOK
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found with id: " + id));

        // CHECK BORROWED COPIES
        if(book.getAvailableCopies() < book.getTotalCopies()){
            throw new ConflictException("Cannot delete book. Some copies are currently borrowed.");
        }

        bookRepository.delete(book);
        return new MessageResponse("Book deleted successfully");
    }

    // SEARCH BOOK
    public List<BookResponse> searchBooks(String keyword){

        List<Book> books = bookRepository
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword);

        return books.stream()
                .map(BookMapper::toResponse)
                .collect(Collectors.toList());
    }
}