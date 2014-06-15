package com.inove.estoqueweb.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inove.estoqueweb.dao.DAOException;
import com.inove.estoqueweb.dao.EstoqueDAO;
import com.inove.estoqueweb.dominio.Estoque;

@Component
public class EstoqueFacade {

	private EstoqueDAO estoqueDAO;  
	
	@Autowired
	public EstoqueFacade(EstoqueDAO estoqueDAO){
		
		this.estoqueDAO = estoqueDAO; 
	}
	
	public Long criarEstoque(Estoque estoque)throws DAOException{
		
		estoqueDAO.salvar(estoque);
		
		estoqueDAO.getSession().flush();
		
		return estoque.getId(); 
		
	}
	
	public void removerEstoque(Estoque estoque)throws DAOException{
		
		removerEstoque(estoque.getId());
	}
	
	public void removerEstoque(Long id)throws DAOException{
		
		Estoque estoque = estoqueDAO.buscar(Estoque.class, id); 
		
		if(estoque==null)
			return; 
		
		estoqueDAO.remover(estoque);
		estoqueDAO.getSession().flush(); 
		
	}
	
	public void alterarEstoque(Estoque estoque)throws DAOException{
		
		Estoque estoquePesquisado = estoqueDAO.buscar(Estoque.class,estoque.getId()); 
		
		if(estoquePesquisado==null)
			return; 
		
		estoquePesquisado.setDescricao(estoque.getDescricao());
		estoquePesquisado.setNome(estoque.getNome());
		
		estoqueDAO.alterar(estoquePesquisado);
		estoqueDAO.getSession().flush(); 
		
	
	}
	
}
