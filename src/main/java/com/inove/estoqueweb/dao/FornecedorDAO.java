package com.inove.estoqueweb.dao;

import org.hibernate.Session;

import com.inove.estoqueweb.dominio.Fornecedor;

public class FornecedorDAO extends GenericoDAO<Fornecedor> {

	public FornecedorDAO(Session session){
		
		super(session); 
	}
}
