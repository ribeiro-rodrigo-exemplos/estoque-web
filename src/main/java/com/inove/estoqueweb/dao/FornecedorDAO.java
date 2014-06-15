package com.inove.estoqueweb.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inove.estoqueweb.dominio.Fornecedor;

@Repository
public class FornecedorDAO extends GenericoDAO<Fornecedor> {

	@Autowired
	public FornecedorDAO(FabricaDeSessao fabrica){
		
		super(fabrica);
	}
	
}
