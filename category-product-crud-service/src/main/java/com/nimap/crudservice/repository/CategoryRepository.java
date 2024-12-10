package com.nimap.crudservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nimap.crudservice.model.CategoryModel;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

}
