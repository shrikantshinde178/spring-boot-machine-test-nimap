package com.nimap.crudservice.pojo;

import lombok.Data;

@Data
public class ProductResponseDTO {
	
	    private Long productId;
	    private String productName;
//	    private Double price;
	    private CategoryResponseDTO category;

}
