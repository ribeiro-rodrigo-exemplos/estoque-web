package com.inove.estoqueweb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inove.estoqueweb.dominio.Produto;

@Repository
public class ProdutoDAO extends GenericoDAO<Produto> {

	@Autowired
	public ProdutoDAO(FabricaDeSessao fabrica){
		
		super(fabrica); 
	}
	
}
