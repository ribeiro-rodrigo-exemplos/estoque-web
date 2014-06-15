package com.inove.estoqueweb.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GenericoDAO<T> {

	private Session session; 
	
	@Autowired
	public GenericoDAO(FabricaDeSessao fabrica){
		
		this.session = fabrica.getSession(); 
	}
	
	
	public void salvar(T objeto)throws DAOException{
		
		try{
			
			session.save(objeto); 
			
		}catch(Exception e){
			
			throw new DAOException(e.getMessage(),e.getCause()); 
		}
		
		
				
	}
	
	public void remover(T objeto)throws DAOException{
		
		try{
			
			session.delete(objeto);
			
		}catch(Exception e){
			
			throw new DAOException(e.getMessage(),e.getCause()); 
		}
		
		
	}
	
	public T buscar(Class classe,Long id)throws DAOException{
		
		try{
			
			return (T) session.get(classe, id);
			
		}
		catch(Exception e){
			
			throw new DAOException(e.getMessage(),e.getCause());
		}
			
	}
	
	public void alterar(T objeto)throws DAOException{
		
		try{
			
			session.merge(objeto);
			
		}
		catch(Exception e){
			
			throw new DAOException(e.getMessage(),e.getCause());
		}
		
		
		
		
	}
	
	public Session getSession(){
		
		return session; 
	}
	
}
