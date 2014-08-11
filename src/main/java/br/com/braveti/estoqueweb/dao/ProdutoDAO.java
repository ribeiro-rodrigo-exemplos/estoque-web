package com.inove.estoqueweb.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.inove.estoqueweb.dominio.Produto;

@Scope("prototype")
@Repository
public class ProdutoDAO extends GenericoDAO<Produto> {

	@Autowired
	public ProdutoDAO(FabricaDeSessao fabrica){
		
		super(fabrica); 
	}
	
	public List<Produto> listarProdutos()throws DAOException{
		
		try{
		
			Query query = session.createQuery("from Produto");
		
			List<Produto> produtos = (List<Produto>) query.list(); 
		
			return produtos; 
		
		}
		catch(Exception e){
			
			throw new DAOException(e.getMessage(),e.getCause()); 
		}
		
	}
	
}
