package com.libraryManagement.project1.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libraryManagement.project1.entities.Book;

@Repository
public interface bookRepository extends JpaRepository<Book, Long> {

    // find book by ISBN
    Optional<Book> findByIsbn(String isbn);

    // check if book exists by ISBN
    boolean existsByIsbn(String isbn);
    
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);

}