package com.vedalvi.CashFlow.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.vedalvi.CashFlow.config.CloudinaryConfig;
import com.vedalvi.CashFlow.dto.modeldto.CategoryDto;
import com.vedalvi.CashFlow.exception.DuplicateEntryException;
import com.vedalvi.CashFlow.exception.NotFoundException;
import com.vedalvi.CashFlow.model.Category;
import com.vedalvi.CashFlow.repository.CategoryRepository;
import com.vedalvi.CashFlow.repository.UserRepository;
import com.vedalvi.CashFlow.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private Cloudinary cloudinary;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryDto categoryDto, String userEmail) {
        Category category1 = categoryRepository.findCategoryByName(categoryDto.getName());
        if (category1 != null) {
            if (category1.getName().equals(categoryDto.getName()))
                throw new DuplicateEntryException("Category already exists");
        }

        Category category = Category.builder()
                .user(userRepository.findByEmail(userEmail)
                        .orElseThrow(() -> new NotFoundException("Username Not Found")))
                .name(categoryDto.getName())
                .categoryType(categoryDto.getCategoryType())
                .build();
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategoriesForUser(String userEmail) {
        userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("Username Not Found"));
        List<Category> categories = categoryRepository.findByUserEmail(userEmail);
        return categories;
    }

    @Override
    public Category updateCategory(Long categoryId, Category categoryDetails, String userEmail) {
        return null;
    }

    @Override
    public void deleteCategory(Long categoryId, String userEmail) {
        userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("Username Not Found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category Not Found"));
        if (!category.getUser().getEmail().equals(userEmail)) {
            throw new IllegalArgumentException("Category does not belong to the specified user.");
        }
        categoryRepository.delete(category);
    }

    @Override
    public String uploadImage(String categoryName, String userEmail, MultipartFile multipartFile) {
        String imageUrl = null;
        try {
            Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(),
                    ObjectUtils.asMap("folder", "users/" + userEmail + "/" + new Date().getTime() + multipartFile.getOriginalFilename()));
            imageUrl = uploadResult.get("secure_url").toString();
        }catch (IOException e){}
        if (imageUrl != null){
            Category category = categoryRepository.findCategoryByName(categoryName);
            category.setImageUrl(imageUrl);
            categoryRepository.save(category);
        }
        return imageUrl;
    }
}
