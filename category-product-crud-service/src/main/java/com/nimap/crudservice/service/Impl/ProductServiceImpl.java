package com.nimap.crudservice.service.Impl;

import com.nimap.crudservice.dao.IProductDAO;
import com.nimap.crudservice.exception.ProductException;
import com.nimap.crudservice.exception.ProductNotFoundException;
import com.nimap.crudservice.model.CategoryModel;
import com.nimap.crudservice.model.ProductModel;
import com.nimap.crudservice.pojo.CategoryResponseDTO;
import com.nimap.crudservice.pojo.ProductRequestDTO;
import com.nimap.crudservice.pojo.ProductResponseDTO;
import com.nimap.crudservice.repository.ProductRepository;
import com.nimap.crudservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDAO productDAOService;
    
    @Autowired
    private ProductRepository productRepository;
    
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponseDTO> getAllProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<ProductModel> productPage = productRepository.findAll(pageRequest);
        return productPage.getContent().stream().map(product -> {
            ProductResponseDTO response = new ProductResponseDTO();
            response.setProductId(product.getProductId());
            response.setProductName(product.getProductName());
//            response.setPrice(product.getPrice());
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<ProductModel> products = productRepository.findAll();
        return products.stream().map(product -> {
            ProductResponseDTO response = new ProductResponseDTO();
            response.setProductId(product.getProductId());
            response.setProductName(product.getProductName());
//            response.setPrice(product.getPrice()); // Uncomment if price is needed

            // Map CategoryModel to CategoryResponseDTO
            if (product.getCategory() != null) {
                CategoryResponseDTO categoryResponse = new CategoryResponseDTO();
                categoryResponse.setCategoryId(product.getCategory().getCategoryId());
                categoryResponse.setCategoryName(product.getCategory().getCategoryName());
                categoryResponse.setCategoryDescription(product.getCategory().getCategoryDescription());
                response.setCategory(categoryResponse);
            } else {
                response.setCategory(null);
            }

            return response;
        }).collect(Collectors.toList());
    }


    @Override
    public ProductResponseDTO getProductById(Long productId) {
        ProductResponseDTO productResponseDTO = productDAOService.fetchProductInfoById(productId);
        if (productResponseDTO != null) {
            CategoryModel category = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"))
                    .getCategory();

            CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
            categoryResponseDTO.setCategoryId(category.getCategoryId());
            categoryResponseDTO.setCategoryName(category.getCategoryName());
            categoryResponseDTO.setCategoryDescription(category.getCategoryDescription());
            productResponseDTO.setCategory(categoryResponseDTO);
        }
        return productResponseDTO;
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO product) {
        if (product.getCategoryId() == null) {
            throw new IllegalArgumentException("Category must not be null");
        }
        
//        if (product.getPrice() == null) {
//            throw new IllegalArgumentException("Product price must not be null");
//        }

        return productDAOService.productAddReq(product);
    }
    
    
    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
        if (productRequestDTO.getProductName() == null || productRequestDTO.getProductName().isEmpty()) {
            throw new ProductException("Product name cannot be null or empty");
        }
        return productDAOService.updateProductById(id, productRequestDTO);
    }

    @Override
    public void deleteProduct(Long id) {
        boolean deleted = productDAOService.deleteProductById(id);
        if (!deleted) {
            throw new RuntimeException("Product not found for ID: " + id);
        }
    }
}
