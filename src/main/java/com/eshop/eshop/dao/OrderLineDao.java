package com.eshop.eshop.dao;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.eshop.entity.OrderLine;
import com.eshop.eshop.repository.IOrderLineRepository;

@Service
public class OrderLineDao {
    @Autowired
	IOrderLineRepository orderLineRepository;
	
	// Liste de orderLine
	public List<OrderLine> getOrderLines() {
		return orderLineRepository.findAll();

	}

	//Save
	public OrderLine saveOrderLine(OrderLine orderLine) {
		return orderLineRepository.save(orderLine);

	}

	// get a orderLine 
	public OrderLine getOrderLineById(Long idOrderLine) {
		return orderLineRepository.findById(idOrderLine).get();
	}


	// Delete a orderLine

	public void deleteOrderLine(Long id) {
		orderLineRepository.deleteById(id);

	}
	//Update
	public OrderLine updateOrderLine(OrderLine orderLine) {
		return orderLineRepository.save(orderLine);

	}
}
