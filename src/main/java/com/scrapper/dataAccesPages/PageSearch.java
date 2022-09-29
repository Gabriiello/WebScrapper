package com.scrapper.dataAccesPages;

import java.util.ArrayList;
import java.util.List;

import com.scrapper.models.ProductFound;

public interface PageSearch {

	String urlAmazon= "https://www.amazon.com";
	
	void loadProductSearch(String product);
	void cargarPaginas();
	List<ProductFound> getProducts();
	
}
