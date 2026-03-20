package com.libraryManagement.project1.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libraryManagement.project1.dto.BorrowedBookResponse;
import com.libraryManagement.project1.dto.IssueBookRequest;
import com.libraryManagement.project1.dto.IssueRecordResponse;
import com.libraryManagement.project1.security.CustomUserDetails;
import com.libraryManagement.project1.services.IssueRecordService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/issues")
@RequiredArgsConstructor
public class IssueRecordController {

    private final IssueRecordService issueRecordService;
    
    @PreAuthorize("hasAuthority('BOOK_ISSUE')")
    @PostMapping("/borrow")
    public IssueRecordResponse borrowBook( @Valid @RequestBody IssueBookRequest request){
        return issueRecordService.borrowBook(request);
    }
    
    @PreAuthorize("hasAuthority('BOOK_RETURN')")
    @PostMapping("/return/{id}")
    public IssueRecordResponse returnBook(@PathVariable Long id){
        return issueRecordService.returnBook(id);
    }
    
    // CURRENTLY BORROWED BOOKS
    @PreAuthorize("hasAuthority('ISSUE_VIEW_ACTIVE')")
    @GetMapping("/my-books")
    public List<BorrowedBookResponse> getMyBooks(
            @AuthenticationPrincipal CustomUserDetails userDetails){

        return issueRecordService.getMyBorrowedBooks(userDetails.getUser());
    }

    // BORROW HISTORY
    @PreAuthorize("hasAuthority('ISSUE_VIEW_HISTORY')")
    @GetMapping("/history")
    public List<BorrowedBookResponse> getBorrowHistory(
            @AuthenticationPrincipal CustomUserDetails userDetails){

        return issueRecordService.getBorrowHistory(userDetails.getUser());
    }
    
    
    // all issue records
    @PreAuthorize("hasAuthority('ISSUE_VIEWALL')")
    @GetMapping
    public List<IssueRecordResponse> getAllIssueRecords(){

        return issueRecordService.getAllIssueRecords();
    }
}