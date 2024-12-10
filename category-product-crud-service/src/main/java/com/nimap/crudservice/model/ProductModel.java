package com.nimap.crudservice.model;

import java.io.Serializable;
import java.util.Locale.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "products")
public class ProductModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	
	@NotBlank(message = "Product name is required")
	@Column(name = "productName", nullable = false)
	private String productName;
	
//	@Column(name = "price", nullable = false)
//	private Double price;
	
	 @ManyToOne(fetch = FetchType.EAGER)
	 @JoinColumn(name = "category_id", nullable = false)
	 private CategoryModel category;
}
