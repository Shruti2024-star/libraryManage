package com.libraryManagement.project1.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueRecordResponse {

    private Long id;
    private String userName;
    private String bookTitle;
    private String status;
    private double fine;
    private Long bookId;

}