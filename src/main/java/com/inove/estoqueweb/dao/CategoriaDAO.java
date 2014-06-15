package com.inove.estoqueweb.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inove.estoqueweb.dominio.Categoria;


@Repository
public class CategoriaDAO extends GenericoDAO<Categoria> {

	@Autowired
	public CategoriaDAO(FabricaDeSessao fabrica){
		
		super(fabrica);
	}
	
}
