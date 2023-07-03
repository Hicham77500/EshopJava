package com.eshop.eshop.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.eshop.entity.Category;
import com.eshop.eshop.repository.ICategoryRepository;

@Service
public class CategoryDao {
    

	@Autowired
	ICategoryRepository categoryRepository;
	
	// Liste de category
	public List<Category> getCategories() {
		return categoryRepository.findAll();

	}

	//Save
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);

	}

	// get a category 
	public Category getCategoryById(Long idCategory) {
		return categoryRepository.findById(idCategory).get();
	}


	// Delete a category

	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);

	}
	//Update
	public Category updateCategory(Category category) {
		return categoryRepository.save(category);

	}
}
