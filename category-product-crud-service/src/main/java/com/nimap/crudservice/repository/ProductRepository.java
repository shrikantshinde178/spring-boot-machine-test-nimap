package com.nimap.crudservice.repository;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nimap.crudservice.model.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {

//	Page<ProductModel> findAll(Pageable pageable);

}
