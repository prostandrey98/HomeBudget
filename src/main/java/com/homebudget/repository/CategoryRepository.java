package com.homebudget.repository;

import com.homebudget.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    long countById(Long id);
}