package com.scrapper.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;import java.util.Comparator;
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
	
	@GetMapping("/home")
	public ModelAndView pagHome() {
		ModelAndView modelAndView= new ModelAndView("home");
		return modelAndView;
	}
	
	@GetMapping("/producto")
	public ModelAndView pagProductCheck() {
		ModelAndView modelAndView= new ModelAndView("productoCheck");
		return modelAndView;
	}
	
	@GetMapping("/producto/viewProductsSave")
	public ModelAndView pagViewProductsSave() {
		ModelAndView modelAndView= new ModelAndView("tableProducts");
		return modelAndView;
	}
	
	
	@GetMapping(value="/home/search/{productToSearch}")
	public void searchProducts(@PathVariable String productToSearch) {
		System.out.println("LOS PRODUCTOS A BUSCAR:"+ productToSearch);
		buscarProducto.loadProductSearch(productToSearch);
		buscarProducto.cargarPaginas();
		productosEncontrados= buscarProducto.getProducts();
	}
	
	

	
	
	@GetMapping(value = "/search/lessProductPrice")
	public List<ProductPage> getLess(){
		
		System.out.println("SE ESCOGIO MENOR PRECIO");
		List <ProductPage> listaP= new ArrayList<ProductPage>();
		
		
			Collections.sort(productosEncontrados, new Comparator<ProductFound>() { //se ordena el arreglo de una manera ascendeste

				@Override
				public int compare(ProductFound o1, ProductFound o2) {//instanciamos la  clase comparator y ordenamos el arraylist por el atributo precio de menor a mayor
					if(o1.getPrice()<o2.getPrice()) {
						return -1;
					}
					if(o1.getPrice()>o2.getPrice()){
						return 1;
					}
					
					return 0;
				}
			});//ordenamos de menor a mayor
			
			
		
		
		//listaP.add(pag.getProductPage(menorPrecioFound.getLink()));
		
		listaP.add(pag.getProductPage(productosEncontrados.get(0).getLink()));
		listaP.add(pag.getProductPage(productosEncontrados.get(1).getLink()));
		listaP.add(pag.getProductPage(productosEncontrados.get(2).getLink()));
		
		//System.out.println("Link elegido:" +menorPrecioFound.getLink());
		return listaP;
		
	}
	
	@GetMapping(value = "/search/higherProductPrice")
	public List<ProductPage> getHigher(){
		List <ProductPage> listaP= new ArrayList<ProductPage>();
		
		
		
		Collections.sort(productosEncontrados, new Comparator<ProductFound>() {

			@Override
			public int compare(ProductFound o1, ProductFound o2) {
				if(o1.getPrice()<o2.getPrice()) {
					return 1;
				}
				if(o1.getPrice()>o2.getPrice()){
					return -1;
				}
				
				return 0;
			}
		});
		
		listaP.add(pag.getProductPage(productosEncontrados.get(0).getLink()));
		listaP.add(pag.getProductPage(productosEncontrados.get(1).getLink()));
		listaP.add(pag.getProductPage(productosEncontrados.get(2).getLink()));
		
		
		
		
		
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
	
	@RequestMapping(value = "/product/deleteProduct/{id}", method = RequestMethod.DELETE)
	public void deleteProduct(@PathVariable int id) {
		daoProduct.deleteProduct(id);
	}
	
	
	
	
}
