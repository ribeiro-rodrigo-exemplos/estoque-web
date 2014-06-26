package com.inove.estoqueweb.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.inove.estoqueweb.dominio.Estoque;

@Scope("prototype")
@Repository
public class EstoqueDAO extends GenericoDAO<Estoque>{

	@Autowired
	public EstoqueDAO(FabricaDeSessao fabrica){
		
		super(fabrica);
	}
	
	public List<Estoque> listarEstoques()throws DAOException{
		
		try{
		
			Query query = session.createQuery("from Estoque"); 
		
			List<Estoque> estoques = query.list(); 
		
			return estoques;
		 
		}
		catch(Exception e){
			
			throw new DAOException(e.getMessage(),e.getCause()); 
		}
	}
	
}
