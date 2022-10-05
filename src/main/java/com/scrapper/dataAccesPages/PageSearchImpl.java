package com.scrapper.dataAccesPages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.scrapper.models.ProductFound;

@Component
public class PageSearchImpl implements PageSearch {

	
	//link para busqueda en amazon(search es remplazada por la palabra a buscar)
	static private  String LINKAMAZON= "https://www.amazon.com/s?k=search&__mk_es_US=%C3%85M%C3%85%C5%BD%C3%95%C3%91";
	private int numProducts;
	String nuevaBusqueda;
	private Document pageSearch; //Aqui se aloja todo el html de la pagina
	private List <ProductFound> productosCompletos;
	
	
	
	/*
	 * Metodo que carga el html y lo asigna a a pageSearch
	 * Parametro: producto a buscar
	 */
	
	

	@Override
	public void loadProductSearch(String product) {
		
		//remplaza la palabra search del url por el producto a buscar si el producto a buscar tiene espacios los remplaza por +
		nuevaBusqueda= LINKAMAZON.replaceAll("search", product.replace(" ", "+"));
		System.out.println("este es el link de los objetos: "+nuevaBusqueda);
		
		try {
			
			pageSearch= (Document) Jsoup.connect(nuevaBusqueda)
					    .userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31")
			            .maxBodySize(1024*1024*3) 
			            .followRedirects(true)
			            .timeout(100000)
			            .get(); //obtenemos el html y lo asigamos al ducument
			
			
			numProducts= Integer.parseInt(pageSearch.getElementsByClass("s-pagination-strip").get(0).getElementsByClass("s-pagination-item s-pagination-disabled").text());
			System.out.println("Las paginas son: "+numProducts);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * metodo que retorna la lista de productos encontrados en la busqueda objetos productFOund
	 */
	
	
	private void getproductsFound() {

		
		
		
		
		//Selecionamos en el html la clase que contiene tanto el precio como el link
		Elements productosBuscados= pageSearch.getElementsByClass("s-card-container s-overflow-hidden aok-relative puis-include-content-margin s-latency-cf-section s-card-border"); 
		double price= 0; 

		
		
		//Los productos que no son tecnologia se organizan diferente en la apginad e amazon por eso es necesario seleccionar otras clases
		//para tener acceso a ellos.
		if(productosBuscados.isEmpty()) { //Entonces si productosBuscados es vacio
			Elements noTecnologia= pageSearch.getElementsByClass("s-main-slot s-result-list s-search-results sg-row");
			productosBuscados= noTecnologia.get(0).getElementsByClass("s-card-container s-overflow-hidden aok-relative puis-expand-height puis-include-content-margin s-latency-cf-section s-card-border");
		}
		
		
		
		for(Element product: productosBuscados) {
			if(product.getElementsByClass("span.a-color-secondary").isEmpty()) { //si el producto no es patrocinado
				ProductFound producto= new ProductFound();
				
				Elements content= product.getElementsByClass("a-offscreen"); 
				
				    try {
				    	price= content.isEmpty()?0:Double.parseDouble(remplazarCaracteres(content.get(0).text())); //se confirma si el precio del articulo eswta disponible si no lo esta este tendra un precio de 0
					} catch (Exception e) {
						System.out.println("Error precio");
						price=0;
					}finally {
						
					}
					
				//asignamos los valores
				producto.setPrice(price);
				producto.setLink(urlAmazon+product.getElementsByClass("a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal").attr("href"));
				productosCompletos.add(producto);
			}
		}
		
		System.out.println("Los productos son:"+productosBuscados.size());
			
		
	}

	
	/*
	 * Algunos productos tienen coma
	 * 
	 */
	private String remplazarCaracteres(String contenidoConComa) {
		String nuevoPrecioNumerico=contenidoConComa.replace("US", "");
		nuevoPrecioNumerico= nuevoPrecioNumerico.replace("$", "");
		nuevoPrecioNumerico= nuevoPrecioNumerico.replace(",", "");
		return nuevoPrecioNumerico;
	}

	@Override
	public void cargarPaginas() {
		int randonTime;
		productosCompletos= new ArrayList();
		
		for(int i=0; i<numProducts; i++ ) {
			randonTime= (int) (Math.random()*5000+1000);
			try {
				Thread.sleep(randonTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			conectTopage(i+1);
			getproductsFound();
		}
		
	}
	
	
	private void conectTopage(int index) {
		String numPagina= "&page="+index;
		String newLinkAmazonNumberPage= nuevaBusqueda.substring(0,nuevaBusqueda.indexOf("&"))+numPagina+nuevaBusqueda.substring(nuevaBusqueda.indexOf("&"));
		
        try {
			
			pageSearch= (Document) Jsoup.connect(newLinkAmazonNumberPage)
					.userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31")
			           // .ignoreHttpErrors(true)
			            .maxBodySize(1024*1024*3) 
			            .followRedirects(true)
			            .timeout(100000)
			            .get();//obtenemos el html y lo asigamos al ducument
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
	}
	
	
	//getterAndSetters
	public static String getLINKAMAZON() {
		return LINKAMAZON;
	}

	public static void setLINKAMAZON(String lINKAMAZON) {
		LINKAMAZON = lINKAMAZON;
	}

	public int getNumProducts() {
		return numProducts;
	}

	public void setNumProducts(int numProducts) {
		this.numProducts = numProducts;
	}

	public Document getPageSearch() {
		return pageSearch;
	}

	public void setPageSearch(Document pageSearch) {
		this.pageSearch = pageSearch;
	}



	public void setProductosCompletos(ArrayList<ProductFound> productosCompletos) {
		this.productosCompletos = productosCompletos;
	}

	@Override
	public List<ProductFound> getProducts() {
		// TODO Auto-generated method stub
		return productosCompletos;
	}
	
	

	
	
}
