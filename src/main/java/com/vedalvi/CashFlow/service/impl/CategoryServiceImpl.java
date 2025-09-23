package com.vedalvi.CashFlow.service.impl;

import com.vedalvi.CashFlow.dto.modeldto.CategoryDto;
import com.vedalvi.CashFlow.exception.UserNotFoundException;
import com.vedalvi.CashFlow.model.Category;
import com.vedalvi.CashFlow.repository.CategoryRepository;
import com.vedalvi.CashFlow.repository.UserRepository;
import com.vedalvi.CashFlow.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryDto categoryDto, String userEmail) {
        Category category = Category.builder()
                .user(userRepository.findByEmail(userEmail)
                        .orElseThrow(()->new UserNotFoundException(userEmail)))
                .name(categoryDto.getName())
                .imageUrl(categoryDto.getImageUrl())
                .categoryType(categoryDto.getCategoryType())
                .build();
        return categoryRepository.save(category);
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
