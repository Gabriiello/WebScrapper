package com.scrapper.dao;

import java.util.List;

import com.scrapper.models.ProductSave;

public interface daoProduct {

	List<ProductSave> getProductsSave();
	void addProduct(ProductSave product);
	void deleteProduct(int id);
	
}
