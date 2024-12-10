package com.nimap.crudservice.service;

import com.nimap.crudservice.pojo.ProductRequestDTO;
import com.nimap.crudservice.pojo.ProductResponseDTO;

import java.util.List;

public interface IProductService {
	
	//Fetch all products with pagination
    List<ProductResponseDTO> getAllProducts(int page, int size); 
    
 // Without Pagination
    List<ProductResponseDTO> getAllProducts(); 

    ProductResponseDTO getProductById(Long productId);

    // Create a new product
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);

    // Update a product by ID
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO);

    // Delete a product by ID
    void deleteProduct(Long id);
    
}
