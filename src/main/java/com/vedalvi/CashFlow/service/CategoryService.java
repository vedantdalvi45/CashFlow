package com.vedalvi.CashFlow.service;

import com.vedalvi.CashFlow.dto.modeldto.CategoryDto;
import com.vedalvi.CashFlow.model.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    Category createCategory(CategoryDto categoryDto, String userEmail);

    List<Category> getCategoriesForUser(String userEmail);

    Category updateCategory(Long categoryId, Category categoryDetails, String userEmail);

    void deleteCategory(Long categoryId, String userEmail);

    String uploadImage(String categoryName, String userEmail, MultipartFile multipartFile);
}
