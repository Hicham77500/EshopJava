package com.eshop.eshop.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.eshop.entity.Orders;
import com.eshop.eshop.repository.IOrderRepository;

@Service
public class OrderDao {
	@Autowired
	IOrderRepository orderRepository;

	// Liste de order
	public List<Orders> getOrders() {
		return orderRepository.findAll();

	}

	// Save
	public Orders saveOrder(Orders order) {
		return orderRepository.save(order);

	}

	// get a order
	public Orders getOrderById(Long idOrder) {
		return orderRepository.findById(idOrder).get();
	}

	// Delete a order

	public void deleteOrder(Long id) {
		orderRepository.deleteById(id);

	}

	// Update
	public Orders updateOrder(Orders order) {
		return orderRepository.save(order);

	}
}
