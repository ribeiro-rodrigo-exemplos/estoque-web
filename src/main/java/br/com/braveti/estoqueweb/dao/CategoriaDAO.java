package br.com.braveti.estoqueweb.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import br.com.braveti.estoqueweb.dominio.Categoria;

@Scope("prototype")
@Repository
public class CategoriaDAO extends GenericoDAO<Categoria> {

	@Autowired
	public CategoriaDAO(FabricaDeSessao fabrica){
		
		super(fabrica);
	}
	
	public List<Categoria> listarCategorias()throws DAOException{
		
		try{
		
			Query query = session.createQuery("from Categoria"); 
		
			List<Categoria> categorias = query.list(); 
		
			return categorias;
		 
		}
		catch(Exception e){
			
			throw new DAOException(e.getMessage(),e.getCause()); 
		}
	}
	
}
