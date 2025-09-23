package com.vedalvi.CashFlow.service.impl;

import com.vedalvi.CashFlow.dto.modeldto.CategoryDto;
import com.vedalvi.CashFlow.exception.DuplicateEntryException;
import com.vedalvi.CashFlow.exception.UserNotFoundException;
import com.vedalvi.CashFlow.model.Category;
import com.vedalvi.CashFlow.repository.CategoryRepository;
import com.vedalvi.CashFlow.repository.UserRepository;
import com.vedalvi.CashFlow.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryDto categoryDto, String userEmail) {
        Category category1 = categoryRepository.findCategoryByName(categoryDto.getName());

        if (category1.getName().equals(categoryDto.getName()))
            throw new DuplicateEntryException("Category already exists");


        Category category = Category.builder()
                .user(userRepository.findByEmail(userEmail)
                        .orElseThrow(() -> new UserNotFoundException(userEmail)))
                .name(categoryDto.getName())
                .imageUrl(categoryDto.getImageUrl())
                .categoryType(categoryDto.getCategoryType())
                .build();
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategoriesForUser(String userEmail) {
        userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));
        List<Category> categories = categoryRepository.findByUserEmail(userEmail);
        return categories;
    }

    @Override
    public Category updateCategory(Long categoryId, Category categoryDetails, String userEmail) {
        return null;
    }

    @Override
    public void deleteCategory(Long categoryId, String userEmail) {

    }
}
