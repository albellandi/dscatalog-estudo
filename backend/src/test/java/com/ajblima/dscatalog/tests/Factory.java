package com.ajblima.dscatalog.tests;

import java.time.Instant;

import com.ajblima.dscatalog.dto.ProductDTO;
import com.ajblima.dscatalog.entities.Category;
import com.ajblima.dscatalog.entities.Product;

public class Factory {
	
	public static Product CreateProduct() {
		Product product = new Product(1L, "Phone", "Good Phone", 800.0 ,"https://raw.githubusercontent.com/img/1-big.jpg", Instant.parse("2020-10-20T03:00:00Z"));
		product.getCategories().add(new Category(1L, "Eletrônicos" ));
		return product;
	}
	
	public static ProductDTO CreateProductDTO                                                                                                                                                                   () {
		Product product = CreateProduct();
		return new ProductDTO(product, product.getCategories());
	}
	
	public static Category CreateCategory                                                                                                                                                                   () {
		Category category = new Category(1L, "Eletrônicos");
		return category;
	}
}
