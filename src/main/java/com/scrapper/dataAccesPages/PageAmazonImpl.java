package com.scrapper.dataAccesPages;




import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.scrapper.models.ProductPage;

@Component
//Esta clase traera la informacion de la pagina web (amazon) por medio de Jsoup
public class PageAmazonImpl implements PageAccces {

	private Document pagAmazon;
	
	
	


	private String getNombrePage() {
		
		Elements content= pagAmazon.getElementsByClass("a-size-large product-title-word-break"); //busucamos por clase
		String result=confirmContent(content)?content.get(0).text():null;
		return result;
		
	}


	private double getPrecioPage() {
		
		Elements content= pagAmazon.getElementsByClass("a-offscreen");
		
		Double result=  confirmContent(content)?Double.parseDouble(remplazarCaracteres(content.get(0).text())):0;
		
		return result;
	}

	
	private String getUrlimagePage() {
		String link;
		Elements content = pagAmazon.getElementsByClass("imgTagWrapper");
		link= content.select("img").attr("src");
		
		
		
		return link;
	}

	
	private int getProductsCheckPage() {
		// TODO Auto-
		return 0;
	}

	
	private String getdescriptionPage() {
		String description="";
		
		Elements content= pagAmazon.select("#feature-bullets ul li span.a-list-item");
		
		for(Element list: content) {
			String txt= list.text();
			description +="\n"+txt;
		}
		
		
		return description;
	}
	
	
	/*
	 * metodo para confirmar si se haencontrado almenos uno de los elements buscados
	 */
	private boolean confirmContent(Elements elements) {
		
		if(!elements.isEmpty()) {
			return true;
		}
		return false;
		
		
		
	}
	
	private String remplazarCaracteres(String contenidoConComa) {
		String nuevoPrecioNumerico=contenidoConComa.replace("US", "");
		nuevoPrecioNumerico= nuevoPrecioNumerico.replace("$", "");
		nuevoPrecioNumerico= nuevoPrecioNumerico.replace(",", "");
		return nuevoPrecioNumerico;
	}
	
	

	@Override
	public ProductPage getProductPage(String url) {
		
		ProductPage producto= new ProductPage();
		
		 try {
			    
			    
				pagAmazon= (Document) Jsoup.connect(url).get(); //creamos una conexion entre la pagina y la guardamos en un document
				//producto.setNombre(pagAmazon.select("title").text());
				producto.setUrl(url);
				producto.setNombre(getNombrePage());
				producto.setPrecio(getPrecioPage());
				producto.setUrlImage(getUrlimagePage());
				producto.setDescripcion(getdescriptionPage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 return producto;
		
	}

	public Document getPagAmazon() {
		return pagAmazon;
	}

	public void setPagAmazon(Document pagAmazon) {
		this.pagAmazon = pagAmazon;
	}

	
	
	
	

}
