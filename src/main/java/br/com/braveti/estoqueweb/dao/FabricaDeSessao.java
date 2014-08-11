package com.inove.estoqueweb.dao;

import org.hibernate.Session;

public interface FabricaDeSessao {

	public Session getSession(); 
}
