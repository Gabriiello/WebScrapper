package com.scrapper.controller;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.internal.ReturnsAreNonnullByDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.scrapper.dao.daoProduct;
import com.scrapper.dataAccesPages.PageAccces;
import com.scrapper.dataAccesPages.PageAmazonImpl;
import com.scrapper.dataAccesPages.PageSearch;
import com.scrapper.dataAccesPages.PageSearchImpl;
import com.scrapper.models.ProductFound;
import com.scrapper.models.ProductPage;
import com.scrapper.models.ProductSave;

@RestController


public class ControllerP {

	
	@Autowired
	PageAccces pag;
	
	@Autowired
	PageSearch buscarProducto;
	
	@Autowired
	daoProduct daoProduct;
	
	List<ProductFound> productosEncontrados;
	
	
	@GetMapping(value="/home/search/{productToSearch}")
	public void searchProducts(@PathVariable String productToSearch) {
		System.out.println("LOS PRODUCTOS A BUSCAR:"+ productToSearch);
		buscarProducto.loadProductSearch(productToSearch);
		buscarProducto.cargarPaginas();
		productosEncontrados= buscarProducto.getProducts();
	}
	
	

	
	
	@GetMapping(value = "/search/lessProductPrice")
	public List<ProductPage> getLess(){
		List <ProductPage> listaP= new ArrayList<ProductPage>();
		
		ProductFound menorPrecioFound= productosEncontrados.get(0);
		for(int i= 0; i<productosEncontrados.size(); i++) {
			if(productosEncontrados.get(i).getPrice() !=0 && productosEncontrados.get(i).getPrice()<menorPrecioFound.getPrice()) {
				menorPrecioFound= productosEncontrados.get(i);
			}
		}
		
		listaP.add(pag.getProductPage(menorPrecioFound.getLink()));
		
		for(int i=0 ;i<listaP.size(); i++) {
			System.out.println(listaP.get(i).getNombre());
			System.out.println(listaP.get(i).getDescripcion());
			System.out.println(listaP.get(i).getPrecio());
		}
		
		System.out.println("Link elegido:" +menorPrecioFound.getLink());
		return listaP;
		
	}
	
	@GetMapping(value = "/search/higherProductPrice")
	public List<ProductPage> getHigher(){
		List <ProductPage> listaP= new ArrayList<ProductPage>();
		
		ProductFound menorPrecioFound= productosEncontrados.get(0);
		for(int i= 0; i<productosEncontrados.size(); i++) {
			if(productosEncontrados.get(i).getPrice()>menorPrecioFound.getPrice()){
				menorPrecioFound= productosEncontrados.get(i);
			}
		}
		
		
		
		listaP.add(pag.getProductPage(menorPrecioFound.getLink()));
		
		System.out.println("Link elegido:" +menorPrecioFound.getLink());
		return listaP;
		
	}
	
	
	//Save to Bd
	@GetMapping(value = "/product/getproductSave")
	public List<ProductSave> getProductsSave(){
		return daoProduct.getProductsSave();
	}
	
	
	@RequestMapping(value = "/product/saveProduct", method = RequestMethod.POST)
	public void addProduct (@RequestBody ProductSave productToSave) {
		daoProduct.addProduct(productToSave);
		
	}
	
	@RequestMapping(value = "/product/deleteProduct", method = RequestMethod.DELETE)
	public void deleteProduct(@PathVariable int id) {
		daoProduct.deleteProduct(id);
	}
	
	
	
	
}
