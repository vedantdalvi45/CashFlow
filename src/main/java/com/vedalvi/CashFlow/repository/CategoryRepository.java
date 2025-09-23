package com.vedalvi.CashFlow.repository;

import com.vedalvi.CashFlow.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    List<Category> findByUserIdOrUserIdIsNull(Long userId);

    List<Category> findByUserEmail(String userEmail);
}
