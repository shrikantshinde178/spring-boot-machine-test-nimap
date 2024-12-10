package com.nimap.crudservice.dao.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.nimap.crudservice.dao.ICategoryDAO;
import com.nimap.crudservice.model.CategoryModel;
import com.nimap.crudservice.pojo.CategoryRequestDTO;
import com.nimap.crudservice.pojo.CategoryResponseDTO;
import com.nimap.crudservice.repository.CategoryRepository;

@Service
public class CategoryDAOImpl implements ICategoryDAO {

	@Autowired 
	private CategoryRepository categoryRepository;

	@Override
	public List<CategoryResponseDTO> fethAllCategory() {
	    try {
	        List<CategoryModel> categoryList = categoryRepository.findAll();

	        // Mapping CategoryModel to CategoryResponseDTO
	        List<CategoryResponseDTO> responseList = categoryList.stream().map(category -> {
	            CategoryResponseDTO response = new CategoryResponseDTO();
	            response.setCategoryId(category.getCategoryId());
	            response.setCategoryName(category.getCategoryName());
	            response.setCategoryDescription(category.getCategoryDescription());
	            return response;
	        }).toList();

	        return responseList;
	    } catch (Exception e) {
	        throw new RuntimeException("Error fetching categories: " + e.getMessage(), e);
	    }
	}

	@Override
	public CategoryResponseDTO fetchCategoryInfoById(Long categoryId) {
		
		CategoryResponseDTO response = new CategoryResponseDTO();
		try {
			Optional<CategoryModel> categoryResponse = categoryRepository.findById(categoryId);
			if (categoryResponse.isPresent()) {
				CategoryModel categoryModel = categoryResponse.get();
				response.setCategoryName(categoryModel.getCategoryName());
				response.setCategoryDescription(categoryModel.getCategoryDescription());
				response.setCategoryId(categoryModel.getCategoryId());
			}
		} catch (Exception e) {
			throw new RuntimeException("Error while fetching category details by Id");
		}
		
		return null;
	}
	
	  @Override
	    public CategoryResponseDTO categoryAddReq(CategoryRequestDTO request) {
	        try {
	            CategoryModel categoryModel = new CategoryModel();
	            categoryModel.setCategoryName(request.getCategoryName());
	            categoryModel.setCategoryDescription(request.getCategoryDescription());
	            CategoryModel savedCategory = categoryRepository.save(categoryModel);

	            CategoryResponseDTO response = new CategoryResponseDTO();
	            response.setCategoryId(savedCategory.getCategoryId());
	            response.setCategoryName(savedCategory.getCategoryName());
	            response.setCategoryDescription(savedCategory.getCategoryDescription());
	            return response;
	        } catch (Exception e) {
	            throw new RuntimeException("Error adding category: " + e.getMessage(), e);
	        }
	    }

	    @Override
	    public boolean deleteCategoryById(Long categoryId) {
	        try {
	            if (categoryRepository.existsById(categoryId)) {
	                categoryRepository.deleteById(categoryId);
	                return true;
	            } else {
	                return false;
	            }
	        } catch (Exception e) {
	            throw new RuntimeException("Error deleting category: " + e.getMessage(), e);
	        }
	    }

	    @Override
	    public CategoryResponseDTO updateCategoryById(Long categoryId, CategoryRequestDTO request) {
	        try {
	            Optional<CategoryModel> existingCategory = categoryRepository.findById(categoryId);
	            if (existingCategory.isPresent()) {
	                CategoryModel categoryModel = existingCategory.get();
	                categoryModel.setCategoryName(request.getCategoryName());
	                categoryModel.setCategoryDescription(request.getCategoryDescription());
	                CategoryModel updatedCategory = categoryRepository.save(categoryModel);

	                CategoryResponseDTO response = new CategoryResponseDTO();
	                response.setCategoryId(updatedCategory.getCategoryId());
	                response.setCategoryName(updatedCategory.getCategoryName());
	                response.setCategoryDescription(updatedCategory.getCategoryDescription());
	                return response;
	            } else {
	                throw new RuntimeException("Category not found for ID: " + categoryId);
	            }
	        } catch (Exception e) {
	            throw new RuntimeException("Error updating category: " + e.getMessage(), e);
	        }
	    }

	
	

}
