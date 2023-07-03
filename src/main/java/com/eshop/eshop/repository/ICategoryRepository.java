package com.eshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.eshop.entity.Category;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
    
}
