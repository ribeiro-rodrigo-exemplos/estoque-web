package com.inove.estoqueweb.dao;

import com.inove.estoqueweb.dominio.*;

import org.hibernate.*;
import java.util.*; 

public class ProdutoDAO extends GenericoDAO<Produto> {

	public ProdutoDAO(Session session){
		
		super(session);
	}
	
}
