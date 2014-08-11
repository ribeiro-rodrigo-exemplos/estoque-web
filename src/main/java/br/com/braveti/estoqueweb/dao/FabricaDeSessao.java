package br.com.braveti.estoqueweb.dao;

import org.hibernate.Session;

public interface FabricaDeSessao {

	public Session getSession(); 
}
