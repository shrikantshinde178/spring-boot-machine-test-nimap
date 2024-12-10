package com.nimap.crudservice.controller;

import com.nimap.crudservice.exception.CategoryException;
import com.nimap.crudservice.exception.CategoryNotFoundException;
import com.nimap.crudservice.pojo.CategoryRequestDTO;
import com.nimap.crudservice.pojo.CategoryResponseDTO;
import com.nimap.crudservice.service.ICategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private ICategoryService categoryService;

	// Get all categories with pagination
	@GetMapping
	public ResponseEntity<?> getAllCategories(@RequestParam(value = "page", defaultValue = "2") int page,
			@RequestParam(value = "size", defaultValue = "2") int size) {
		List<CategoryResponseDTO> categories = categoryService.getAllCategories(page, size);
		if (categories.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No categories found.");
		}
		return ResponseEntity.ok(categories);
	}

	// Get category by ID
	@GetMapping("/{id}")
	public ResponseEntity<?> fetchCategoryInfo(@PathVariable("id") Long categoryId) {
		try {
			if (categoryId <= 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Invalid category ID. ID must be greater than zero.");
			}
			CategoryResponseDTO response = categoryService.getCategoryById(categoryId);
			if (response == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found for ID: " + categoryId);
			}
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while fetching the category: " + e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO request) {
		try {
			CategoryResponseDTO response = categoryService.createCategory(request);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (CategoryException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
		}
	}

	// Update a category by ID
	@PutMapping("/{id}")
	public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable("id") Long id,
			@RequestBody CategoryRequestDTO request) {
		try {
			CategoryResponseDTO response = categoryService.updateCategory(id, request);
			return ResponseEntity.ok(response);
		} catch (CategoryNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		} catch (CategoryException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
		}
	}

	// Delete a category by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {
		try {
			categoryService.deleteCategory(id);
			return ResponseEntity.ok("Category deleted successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found for ID: " + id);
		}
	}

}