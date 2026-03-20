package com.libraryManagement.project1.mapper;

import com.libraryManagement.project1.dto.IssueRecordResponse;
import com.libraryManagement.project1.entities.IssueRecord;

public class IssueRecordMapper {

    public static IssueRecordResponse toResponse(IssueRecord record){

        return IssueRecordResponse.builder()
                .id(record.getId())
                .userName(record.getUser().getName())
                .bookTitle(record.getBook().getTitle())
                .status(record.getStatus())
                .fine(record.getFine())
                .bookId(record.getBook().getId())
                .build();
    }
}