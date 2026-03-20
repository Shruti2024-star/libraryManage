package com.libraryManagement.project1.controller;



import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.libraryManagement.project1.dto.*;
import com.libraryManagement.project1.entities.Permission;
import com.libraryManagement.project1.services.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // ADD CATEGORY
    @PostMapping
    @PreAuthorize("hasAuthority('CATEGORY_CREATE')")
    public CategoryResponse addCategory( @Valid @RequestBody CategoryRequest request){
        return categoryService.addCategory(request);
    }

    // GET ALL
    @GetMapping
    @PreAuthorize("hasAuthority('CATEGORY_VIEWALL')")
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }

    // GET BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_VIEW')")
    public CategoryResponse getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_UPDATE')")
    public CategoryResponse updateCategory(@Valid @PathVariable Long id,
                                           @RequestBody CategoryRequest request){
        return categoryService.updateCategory(id, request);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CATEGORY_DELETE')")
    public void deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
    }
}
