package com.vedalvi.CashFlow.repository;

import com.vedalvi.CashFlow.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserIdOrUserIdIsNull(Long userId);

    List<Category> findByUserEmail(String userEmail);

    Category findCategoryByName(String name);
}
