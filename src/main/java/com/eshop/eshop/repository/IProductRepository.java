package com.eshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.eshop.entity.Product;

public interface IProductRepository extends JpaRepository<Product, Long>{
    
}
