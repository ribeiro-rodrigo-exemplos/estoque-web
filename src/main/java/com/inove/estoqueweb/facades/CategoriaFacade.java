package com.inove.estoqueweb.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.inove.estoqueweb.dao.CategoriaDAO;
import com.inove.estoqueweb.dao.DAOException;
import com.inove.estoqueweb.dominio.Categoria;

@Scope("prototype")
@Component
public class CategoriaFacade {

	private CategoriaDAO categoriaDAO; 
	
	@Autowired
	public CategoriaFacade(CategoriaDAO categoriaDAO){
		
		this.categoriaDAO = categoriaDAO; 
	}
	
	public Long criarCategoria(Categoria categoria)throws DAOException{
		
		categoriaDAO.iniciarTransacao();
		categoriaDAO.salvar(categoria);
		categoriaDAO.finalizarTransacao();

		
		return categoria.getId();  
		
	}
	
	public void removerCategoria(Categoria categoria)throws DAOException{
		
		removerCategoria(categoria.getId()); 
	}
	
	public void removerCategoria(Long id)throws DAOException{
		
		Categoria categoria = categoriaDAO.buscar(Categoria.class, id); 
		
		if(categoria!=null){
			
			categoriaDAO.iniciarTransacao();
			categoriaDAO.remover(categoria);
			categoriaDAO.finalizarTransacao();
			
		}
			
		
	}
	
	public void alterarCategoria(Categoria categoria)throws DAOException{
		
		Categoria categoriaPesquisada = categoriaDAO.buscar(Categoria.class,categoria.getId()); 
		
		if(categoriaPesquisada==null)
			return; 
		
		categoriaPesquisada.setDescricao(categoria.getDescricao());
		categoriaPesquisada.setNome(categoria.getNome());
		
		categoriaDAO.iniciarTransacao();
		categoriaDAO.alterar(categoriaPesquisada);
		categoriaDAO.finalizarTransacao();
			
	}
	
}

