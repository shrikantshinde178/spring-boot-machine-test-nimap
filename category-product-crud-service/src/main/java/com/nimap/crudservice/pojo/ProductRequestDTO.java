package com.nimap.crudservice.pojo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequestDTO {
	
	 private Long categoryId;
	 
	 private String productName;
	 
//	 @NotNull(message = "Product price cannot be null")
//	 private Double price;
	    
}
