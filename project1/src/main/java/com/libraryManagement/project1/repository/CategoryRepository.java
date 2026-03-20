package com.libraryManagement.project1.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.libraryManagement.project1.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	boolean existsByName(String name);

}