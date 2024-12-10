package com.nimap.crudservice.service;

import com.nimap.crudservice.pojo.CategoryRequestDTO;
import com.nimap.crudservice.pojo.CategoryResponseDTO;

import java.util.List;

public interface ICategoryService {

	List<CategoryResponseDTO> getAllCategories(int page, int size);

	CategoryResponseDTO getCategoryById(Long categoryId);

	// Create a new category
	CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);

	// Update a category by ID
	CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO);

	// Delete a category by ID
	void deleteCategory(Long id);

}
