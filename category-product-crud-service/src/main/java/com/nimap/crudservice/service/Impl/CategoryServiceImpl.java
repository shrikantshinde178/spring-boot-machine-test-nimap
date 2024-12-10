package com.nimap.crudservice.service.Impl;

import com.nimap.crudservice.dao.ICategoryDAO;
import com.nimap.crudservice.exception.CategoryException;
import com.nimap.crudservice.exception.CategoryNotFoundException;
import com.nimap.crudservice.model.CategoryModel;
import com.nimap.crudservice.pojo.CategoryRequestDTO;
import com.nimap.crudservice.pojo.CategoryResponseDTO;
import com.nimap.crudservice.repository.CategoryRepository;
import com.nimap.crudservice.service.ICategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryDAO categoryDAOService;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override	
    public List<CategoryResponseDTO> getAllCategories(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<CategoryModel> categoryPage = categoryRepository.findAll(pageRequest);
        return categoryPage.getContent().stream().map(category -> {
            CategoryResponseDTO response = new CategoryResponseDTO();
            response.setCategoryId(category.getCategoryId());
            response.setCategoryName(category.getCategoryName());
            response.setCategoryDescription(category.getCategoryDescription());
            return response;
        }).collect(Collectors.toList());
    }


    @Override
    public CategoryResponseDTO getCategoryById(Long categoryId) {
        Optional<CategoryModel> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new CategoryNotFoundException("Category not found with ID: " + categoryId);
        }
        CategoryModel categoryModel = category.get();
        // Convert CategoryModel to CategoryResponseDTO
        CategoryResponseDTO response = new CategoryResponseDTO();
        response.setCategoryId(categoryModel.getCategoryId());
        response.setCategoryName(categoryModel.getCategoryName());
        response.setCategoryDescription(categoryModel.getCategoryDescription());
        return response;
    }

	
	  @Override
	    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
		  if (categoryRequestDTO.getCategoryName() == null || categoryRequestDTO.getCategoryName().isEmpty()) {
		        throw new CategoryException("Category name cannot be null or empty");
		    }
	        return categoryDAOService.categoryAddReq(categoryRequestDTO);
	    }

	    @Override
	    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
	    	 CategoryModel category = categoryRepository.findById(id)
	    		        .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));

	    		    if (categoryRequestDTO.getCategoryName() == null || categoryRequestDTO.getCategoryName().isEmpty()) {
	    		        throw new CategoryException("Category name cannot be null or empty");
	    		    }
	        return categoryDAOService.updateCategoryById(id, categoryRequestDTO);
	    }

	    @Override
	    public void deleteCategory(Long id) {
	        boolean deleted = categoryDAOService.deleteCategoryById(id);
	        if (!deleted) {
	            throw new RuntimeException("Category not found for ID: " + id);
	        }
	    }

   
}
