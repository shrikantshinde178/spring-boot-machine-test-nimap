package com.nimap.crudservice.pojo;

import lombok.Data;

@Data
public class CategoryResponseDTO {
	
    private Long categoryId;
    private String categoryName;
    private String categoryDescription;
}
