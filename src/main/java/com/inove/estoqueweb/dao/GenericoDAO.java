package com.inove.estoqueweb.dao;

import org.hibernate.*;

public class GenericoDAO<T> {

	private Session session; 
	
	public GenericoDAO(Session session){
		
		this.session = session; 
	}
	
	
	public void salvar(T objeto){
						
		session.save(objeto); 
				
	}
	
	public void remover(T objeto){
		
		session.delete(objeto);
		
	}
	
	public T buscar(Class classe,Long id){
				
		return (T) session.get(classe, id);
	}
	
	public void alterar(T objeto){
		
		session.merge(objeto);
		
		
	}
	
}
