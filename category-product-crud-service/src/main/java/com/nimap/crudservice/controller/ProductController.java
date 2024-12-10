package com.nimap.crudservice.controller;

import com.nimap.crudservice.exception.ProductException;
import com.nimap.crudservice.exception.ProductNotFoundException;
import com.nimap.crudservice.pojo.ProductRequestDTO;
import com.nimap.crudservice.pojo.ProductResponseDTO;
import com.nimap.crudservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private IProductService productService;

 // Get all products with pagination
    @GetMapping
    public ResponseEntity<?> getAllProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            // Fetch products with pagination
            List<ProductResponseDTO> products = productService.getAllProducts(page, size);
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException ex) {
            // Handle invalid page/size arguments
            return ResponseEntity.badRequest().body("Invalid pagination parameters: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching products: " + ex.getMessage());
        }
    }


    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long productId) {
        try {
            if (productId <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid product ID. ID must be greater than zero.");
            }
            ProductResponseDTO response = productService.getProductById(productId);
            if (response == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Product not found for ID: " + productId);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the product: " + e.getMessage());
        }
    }


    // Create a new product
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO request) {
        try {
            ProductResponseDTO response = productService.createProduct(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ProductException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    // Update a product by ID
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequestDTO request) {
        try {
            ProductResponseDTO response = productService.updateProduct(id, request);
            return ResponseEntity.ok(response);
        } catch (ProductNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        } catch (ProductException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    // Delete a product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found for ID: " + id);
        }
    }
}


