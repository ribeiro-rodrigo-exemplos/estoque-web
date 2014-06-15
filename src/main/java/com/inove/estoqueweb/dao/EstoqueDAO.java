package com.inove.estoqueweb.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inove.estoqueweb.dominio.Estoque;

@Repository
public class EstoqueDAO extends GenericoDAO<Estoque>{

	@Autowired
	public EstoqueDAO(FabricaDeSessao fabrica){
		
		super(fabrica);
	}
	
}
