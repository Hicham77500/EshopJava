package com.eshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.eshop.entity.OrderLine;

public interface IOrderLineRepository extends JpaRepository<OrderLine, Long> {
    
}
