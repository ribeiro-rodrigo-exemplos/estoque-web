package com.inove.estoqueweb.dao;

import org.hibernate.Session;

import com.inove.estoqueweb.dominio.Estoque;

public class EstoqueDAO extends GenericoDAO<Estoque>{

	public EstoqueDAO(Session session){
		
		super(session);
	}
}
