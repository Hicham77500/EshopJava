package com.eshop.eshop.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.eshop.entity.Product;
import com.eshop.eshop.repository.IProductRepository;

@Service
public class ProductDao {
    @Autowired
	IProductRepository productRepository;
	
	// Liste de product
	public List<Product> getProducts() {
		return productRepository.findAll();

	}

	//Save
	public Product saveProduct(Product product) {
		return productRepository.save(product);

	}

	// get a product
	public Product getProductById(Long idProduct) {
		return productRepository.findById(idProduct).get();
	}


	// Delete a product

	public void deleteProduct(Long id) {
		productRepository.deleteById(id);

	}
	//Update
	public Product updateProduct(Product product) {
		return productRepository.save(product);

	}
}
