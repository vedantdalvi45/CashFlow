package com.vedalvi.CashFlow.service;

import com.vedalvi.CashFlow.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category categoryDto, String userEmail);

    List<Category> getCategoriesForUser(String userEmail);

    Category updateCategory(Long categoryId, Category categoryDetails, String userEmail);

    void deleteCategory(Long categoryId, String userEmail);
}
