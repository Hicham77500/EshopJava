package com.eshop.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.eshop.entity.Orders;

public interface IOrderRepository extends JpaRepository<Orders, Long> {
    
}
