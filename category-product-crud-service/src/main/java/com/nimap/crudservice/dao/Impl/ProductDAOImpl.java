package com.nimap.crudservice.dao.Impl;

import com.nimap.crudservice.dao.IProductDAO;
import com.nimap.crudservice.pojo.CategoryResponseDTO;
import com.nimap.crudservice.pojo.ProductRequestDTO;
import com.nimap.crudservice.pojo.ProductResponseDTO;
import com.nimap.crudservice.model.CategoryModel;
import com.nimap.crudservice.model.ProductModel;
import com.nimap.crudservice.repository.CategoryRepository;
import com.nimap.crudservice.repository.ProductRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ProductDAOImpl implements IProductDAO {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override
    public List<ProductResponseDTO> fethAllproducts() {
        try {
            List<ProductModel> productList = productRepository.findAll(); // Check if this fetches category details

            return productList.stream().map(product -> {
                ProductResponseDTO response = new ProductResponseDTO();
                response.setProductId(product.getProductId());
                response.setProductName(product.getProductName());

                if (product.getCategory() != null) {
                    CategoryResponseDTO categoryResponse = new CategoryResponseDTO();
                    categoryResponse.setCategoryId(product.getCategory().getCategoryId());
                    categoryResponse.setCategoryName(product.getCategory().getCategoryName());
                    response.setCategory(categoryResponse);
                } else {
                    System.out.println("Category is null for product: " + product.getProductName());
                }

                return response;
            }).toList();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching products", e);
        }
    }

    @Override
    public ProductResponseDTO fetchProductInfoById(Long productId) {
        Optional<ProductModel> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            ProductModel product = productOptional.get();
            ProductResponseDTO responseDTO = new ProductResponseDTO();
            responseDTO.setProductId(product.getProductId());
            responseDTO.setProductName(product.getProductName());
//            responseDTO.setPrice(product.getPrice());
            return responseDTO;
        }
        return null;
    }

    @Override
    public ProductResponseDTO productAddReq(ProductRequestDTO productRequestDTO) {
        // Fetch the category from the database based on the categoryId
        Optional<CategoryModel> categoryOptional = categoryRepository.findById(productRequestDTO.getCategoryId());
        if (!categoryOptional.isPresent()) {
            throw new IllegalArgumentException("Category not found with ID: " + productRequestDTO.getCategoryId());
        }

        ProductModel newProduct = new ProductModel();
        newProduct.setProductName(productRequestDTO.getProductName());
//        newProduct.setPrice(productRequestDTO.getPrice());
        newProduct.setCategory(categoryOptional.get()); // Set the category for the product

        // Save the new product entity
        ProductModel savedProduct = productRepository.save(newProduct);

        // Convert the saved entity to a response DTO
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        responseDTO.setProductId(savedProduct.getProductId());
        responseDTO.setProductName(savedProduct.getProductName());
//        responseDTO.setPrice(savedProduct.getPrice());

        // Now map the category
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setCategoryId(savedProduct.getCategory().getCategoryId());
        categoryResponseDTO.setCategoryName(savedProduct.getCategory().getCategoryName());

        responseDTO.setCategory(categoryResponseDTO);

        return responseDTO;
    }


    @Override
    public ProductResponseDTO updateProductById(Long productId, ProductRequestDTO productRequestDTO) {
        Optional<ProductModel> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            ProductModel product = productOptional.get();
            product.setProductName(productRequestDTO.getProductName());
//            product.setPrice(productRequestDTO.getPrice());

            // Save the updated product
            ProductModel updatedProduct = productRepository.save(product);

            // Convert the updated entity to a response DTO
            ProductResponseDTO responseDTO = new ProductResponseDTO();
            responseDTO.setProductId(updatedProduct.getProductId());
            responseDTO.setProductName(updatedProduct.getProductName());
//            responseDTO.setPrice(updatedProduct.getPrice());

            return responseDTO;
        }
        return null;
    }

    @Override
    public boolean deleteProductById(Long productId) {
        Optional<ProductModel> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            // Delete the product
            productRepository.delete(productOptional.get());
            return true;
        }
        return false;
    }

	
}
