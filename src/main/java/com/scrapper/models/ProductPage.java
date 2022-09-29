package com.scrapper.models;

public class ProductPage {

	private String nombre;
	private double precio;
	private String urlImage;
	private int productosRevisados;
	private String descripcion;
	private String url;
	
	
   
	public ProductPage(String nombre, double precio, String urlImage, int productosRevisados, String descripcion,
			String url) {
		this.nombre = nombre;
		this.precio = precio;
		this.urlImage = urlImage;
		this.productosRevisados = productosRevisados;
		this.descripcion = descripcion;
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ProductPage() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public int getProductosRevisados() {
		return productosRevisados;
	}

	public void setProductosRevisados(int productosRevisados) {
		this.productosRevisados = productosRevisados;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	

	

	
	
	
	
}
