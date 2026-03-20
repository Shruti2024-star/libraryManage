package com.libraryManagement.project1.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.libraryManagement.project1.dto.BorrowedBookResponse;
import com.libraryManagement.project1.dto.IssueBookRequest;
import com.libraryManagement.project1.dto.IssueRecordResponse;
import com.libraryManagement.project1.entities.Book;
import com.libraryManagement.project1.entities.IssueRecord;
import com.libraryManagement.project1.entities.User;
import com.libraryManagement.project1.exceptions.BadRequestException;
import com.libraryManagement.project1.exceptions.ConflictException;
import com.libraryManagement.project1.exceptions.ResourceNotFoundException;
import com.libraryManagement.project1.mapper.IssueRecordMapper;
import com.libraryManagement.project1.repository.IssueRecordRepository;
import com.libraryManagement.project1.repository.UserRepository;
import com.libraryManagement.project1.repository.bookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueRecordService {

    private final IssueRecordRepository issueRecordRepository;
    private final UserRepository userRepository;
    private final bookRepository bookRepository;
    private final ReservationService reservationService;

    // BORROW BOOK
    public IssueRecordResponse borrowBook(IssueBookRequest request){

        // CHECK USER
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        // CHECK BOOK
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found with id: " + request.getBookId()));

        // CHECK BOOK AVAILABILITY
        if(book.getAvailableCopies() <= 0){
            throw new ConflictException("Book not available");
        }

        // MAX 3 BOOK RULE
        long borrowedBooks = issueRecordRepository
                .countByUserIdAndStatus(request.getUserId(), "ISSUED");

        if(borrowedBooks >= 3){
            throw new BadRequestException("User can borrow only 3 books at a time");
        }

        IssueRecord issue = IssueRecord.builder()
                .user(user)
                .book(book)
                .issueDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(14))
                .status("ISSUED")
                .fine(0)
                .build();

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        issueRecordRepository.save(issue);
        bookRepository.save(book);

        return IssueRecordMapper.toResponse(issue);
    }

    // RETURN BOOK
    public IssueRecordResponse returnBook(Long issueId){

        // CHECK ISSUE RECORD
        IssueRecord issue = issueRecordRepository.findById(issueId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Issue record not found with id: " + issueId));

        // CHECK IF ALREADY RETURNED
        if("RETURNED".equals(issue.getStatus())){
            throw new BadRequestException("Book already returned");
        }

        Book book = issue.getBook();

        issue.setReturnDate(LocalDate.now());
        issue.setStatus("RETURNED");

        // CHECK LATE RETURN
        if(LocalDate.now().isAfter(issue.getDueDate())){
            long daysLate = ChronoUnit.DAYS.between(issue.getDueDate(), LocalDate.now());
            issue.setFine(daysLate * 5);
        }

        book.setAvailableCopies(book.getAvailableCopies() + 1);

        issueRecordRepository.save(issue);
        bookRepository.save(book);

        // PROCESS RESERVATIONS
        reservationService.processReservationsOnReturn(book);

        return IssueRecordMapper.toResponse(issue);
    }

    // ACTIVE BORROWED BOOKS
    public List<BorrowedBookResponse> getMyBorrowedBooks(User user){

        List<IssueRecord> issues =
                issueRecordRepository.findByUserAndReturnDateIsNull(user);

        return issues.stream()
                .map(issue -> BorrowedBookResponse.builder()
                        .bookId(issue.getBook().getId())
                        .title(issue.getBook().getTitle())
                        .author(issue.getBook().getAuthor())
                        .issueDate(issue.getIssueDate())
                        .dueDate(issue.getDueDate())
                        .returnDate(issue.getReturnDate())
                        .status(issue.getStatus())
                        .fine(issue.getFine())
                        .id(issue.getId())
                        .build())
                .toList();
    }

    // BORROW HISTORY
    public List<BorrowedBookResponse> getBorrowHistory(User user){

        List<IssueRecord> issues =
                issueRecordRepository.findByUser(user);

        return issues.stream()
        	    .map(issue -> BorrowedBookResponse.builder()
        	        .id(issue.getId())
        	        .bookId(issue.getBook().getId())
        	        .title(issue.getBook().getTitle())
        	        .author(issue.getBook().getAuthor())
        	        .issueDate(issue.getIssueDate())
        	        .dueDate(issue.getDueDate())
        	        .returnDate(issue.getReturnDate())
        	        .status(issue.getStatus())
        	        .fine(issue.getFine())
        	        .build())
        	    .toList();
    }

    // GET ALL ISSUE RECORDS
    public List<IssueRecordResponse> getAllIssueRecords(){

        List<IssueRecord> issues = issueRecordRepository.findAll();

        return issues.stream()
                .map(issue -> IssueRecordResponse.builder()
                        .id(issue.getId())
                        .bookTitle(issue.getBook().getTitle())
                        .userName(issue.getUser().getName())
                        .status(issue.getStatus())
                        .build())
                .toList();
    }
}