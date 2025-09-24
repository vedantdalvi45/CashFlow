package com.vedalvi.CashFlow.controller;


import com.vedalvi.CashFlow.dto.modeldto.CategoryDto;
import com.vedalvi.CashFlow.dto.response.CategoriesResponse;
import com.vedalvi.CashFlow.model.Category;
import com.vedalvi.CashFlow.repository.CategoryRepository;
import com.vedalvi.CashFlow.security.CustomUserDetails;
import com.vedalvi.CashFlow.service.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryServiceImpl categoryService;

    @PostMapping
    public ResponseEntity<CategoriesResponse> createCategory(
            @Valid @RequestBody CategoryDto categoryDto,
            @AuthenticationPrincipal CustomUserDetails currentUser) {

        Category createdCategory = categoryService.createCategory(categoryDto, currentUser.getUsername());
        CategoriesResponse categoriesResponse = CategoriesResponse.builder()
                .id(createdCategory.getId())
                .name(createdCategory.getName())
                .categoryType(createdCategory.getCategoryType().toString())
                .build();
        return new ResponseEntity<>(categoriesResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoriesResponse>> getCategories(@AuthenticationPrincipal CustomUserDetails currentUser) {
        List<Category> categories = categoryService.getCategoriesForUser(currentUser.getUsername());
        List<CategoriesResponse> categoriesResponse = categories.stream()
                .sorted(Comparator.comparing(Category::getId))
                .map(category -> CategoriesResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .imageUrl(category.getImageUrl())
                        .categoryType(category.getCategoryType().toString())
                        .build())
                .toList();
        return new ResponseEntity<>(categoriesResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId, @AuthenticationPrincipal CustomUserDetails currentUser) {
        categoryService.deleteCategory(categoryId, currentUser.getUsername());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/upload/{categoryName}")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile multipartFile, @PathVariable String categoryName, @AuthenticationPrincipal CustomUserDetails currentUser){
        String imageUrl = categoryService.uploadImage(categoryName, currentUser.getUsername(), multipartFile);
        return new ResponseEntity<>(imageUrl, HttpStatus.OK);
    }
}
