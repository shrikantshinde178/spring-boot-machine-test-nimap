package com.nimap.crudservice.dao;

import java.util.List;

import com.nimap.crudservice.pojo.CategoryRequestDTO;
import com.nimap.crudservice.pojo.CategoryResponseDTO;

public interface ICategoryDAO {

    List<CategoryResponseDTO> fethAllCategory();

    CategoryResponseDTO fetchCategoryInfoById(Long categoryId);

    // Add new category
    CategoryResponseDTO categoryAddReq(CategoryRequestDTO request);

    // Delete category by ID
    boolean deleteCategoryById(Long categoryId);

    // Update category by ID
    CategoryResponseDTO updateCategoryById(Long categoryId, CategoryRequestDTO request);
}


