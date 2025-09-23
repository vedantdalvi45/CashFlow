package com.vedalvi.CashFlow.service.impl;

import com.vedalvi.CashFlow.model.Category;
import com.vedalvi.CashFlow.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    @Override
    public Category createCategory(Category categoryDto, String userEmail) {
        return null;
    }

    @Override
    public List<Category> getCategoriesForUser(String userEmail) {
        return List.of();
    }

    @Override
    public Category updateCategory(Long categoryId, Category categoryDetails, String userEmail) {
        return null;
    }

    @Override
    public void deleteCategory(Long categoryId, String userEmail) {

    }
}
