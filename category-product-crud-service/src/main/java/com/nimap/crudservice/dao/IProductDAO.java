
package com.nimap.crudservice.dao;

import com.nimap.crudservice.pojo.CategoryResponseDTO;
import com.nimap.crudservice.pojo.ProductRequestDTO;
import com.nimap.crudservice.pojo.ProductResponseDTO;

import java.util.List;

import com.nimap.crudservice.model.ProductModel;

public interface IProductDAO {
	
	 List<ProductResponseDTO> fethAllproducts();

    // Fetch product by ID
    ProductResponseDTO fetchProductInfoById(Long productId);

    // Add a new product
    ProductResponseDTO productAddReq(ProductRequestDTO productRequestDTO);

    // Update product by ID
    ProductResponseDTO updateProductById(Long productId, ProductRequestDTO productRequestDTO);

    // Delete product by ID
    boolean deleteProductById(Long productId);
}

