package com.libraryManagement.project1.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.libraryManagement.project1.dto.*;
import com.libraryManagement.project1.entities.Category;
import com.libraryManagement.project1.exceptions.BadRequestException;
import com.libraryManagement.project1.exceptions.ConflictException;
import com.libraryManagement.project1.exceptions.ResourceNotFoundException;
import com.libraryManagement.project1.mapper.CategoryMapper;
import com.libraryManagement.project1.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // ADD CATEGORY
    public CategoryResponse addCategory(CategoryRequest request){

        // CHECK DUPLICATE CATEGORY
        if(categoryRepository.existsByName(request.getName())){
            throw new ConflictException("Category already exists with name: " + request.getName());
        }

        Category category = CategoryMapper.toEntity(request);

        Category saved = categoryRepository.save(category);

        return CategoryMapper.toResponse(saved);
    }

    // GET ALL
    public List<CategoryResponse> getAllCategories(){

        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public CategoryResponse getCategoryById(Long id){

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id: " + id));

        return CategoryMapper.toResponse(category);
    }

    // UPDATE
    public CategoryResponse updateCategory(Long id, CategoryRequest request){

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id: " + id));

        // CHECK DUPLICATE NAME
        if(!category.getName().equals(request.getName())
                && categoryRepository.existsByName(request.getName())){
            throw new ConflictException("Another category already exists with name: " + request.getName());
        }

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category updated = categoryRepository.save(category);

        return CategoryMapper.toResponse(updated);
    }

    // DELETE
    public  MessageResponse deleteCategory(Long id){

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id: " + id));

        categoryRepository.delete(category);
        
        return new MessageResponse("Category Deleted Sucessfully");
    }
}