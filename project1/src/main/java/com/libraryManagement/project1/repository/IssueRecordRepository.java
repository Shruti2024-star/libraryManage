package com.libraryManagement.project1.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.libraryManagement.project1.entities.IssueRecord;
import com.libraryManagement.project1.entities.User;

public interface IssueRecordRepository extends JpaRepository<IssueRecord, Long> {
	 long countByUserIdAndStatus(Long userId, String status);
	 
	 List<IssueRecord> findByUserAndReturnDateIsNull(User user);
	 
	 List<IssueRecord> findByUser(User user);

}