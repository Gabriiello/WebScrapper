package com.scrapper.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.scrapper.models.ProductSave;

@Repository
@Transactional
public class daoProductImplement implements daoProduct { //clase que se encarga de conectar y ahcer querys a la bd

	@PersistenceContext
	EntityManager entityManager; //objeto que va a crear cosultar en la bd
	
	@Override
	public List<ProductSave> getProductsSave() {
		String query= "FROM productos";//consulta en la base de datos productos
		List<ProductSave> products= entityManager.createQuery(query).getResultList(); //usamos la entidad productSave que hace referencia a la tabla productsscraper
		return products;
	}

	@Override
	public void addProduct(ProductSave product) {
		entityManager.merge(product);

	}

	@Override
	public void deleteProduct(int id) {
		ProductSave productoToDelete= entityManager.find(ProductSave.class, id);//primero buscamos en la bd por id
		entityManager.remove(productoToDelete); //la entidad encontrada a eliminamos
	}

}
