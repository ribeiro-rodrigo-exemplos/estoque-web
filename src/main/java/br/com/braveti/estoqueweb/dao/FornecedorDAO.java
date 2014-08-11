package com.inove.estoqueweb.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.inove.estoqueweb.dominio.Fornecedor;

@Scope("prototype")
@Repository
public class FornecedorDAO extends GenericoDAO<Fornecedor> {

	@Autowired
	public FornecedorDAO(FabricaDeSessao fabrica){
		
		super(fabrica);
	}
	
	public List<Fornecedor> listarFornecedores()throws DAOException{
		
		try{
		
			Query query = session.createQuery("from Fornecedor"); 
		
			List<Fornecedor> fornecedores = query.list(); 
		
			return fornecedores; 
		
		}
		catch(Exception e){
			
			throw new DAOException(e.getMessage(),e.getCause()); 
		}
	}
	
}
