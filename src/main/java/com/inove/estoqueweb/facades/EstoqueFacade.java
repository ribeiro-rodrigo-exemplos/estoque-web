package com.inove.estoqueweb.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.inove.estoqueweb.dao.DAOException;
import com.inove.estoqueweb.dao.EstoqueDAO;
import com.inove.estoqueweb.dominio.Estoque;

@Scope("prototype")
@Component
public class EstoqueFacade {

	private EstoqueDAO estoqueDAO;  
	
	@Autowired
	public EstoqueFacade(EstoqueDAO estoqueDAO){
		
		this.estoqueDAO = estoqueDAO; 
	}
	
	public Long criarEstoque(Estoque estoque)throws DAOException{
		
		estoqueDAO.iniciarTransacao();
		
		estoqueDAO.salvar(estoque);
		
		estoqueDAO.finalizarTransacao();
		
		return estoque.getId(); 
		
	}
	
	public void removerEstoque(Estoque estoque)throws DAOException{
		
		removerEstoque(estoque.getId());
	}
	
	public void removerEstoque(Long id)throws DAOException{
		
		Estoque estoque = estoqueDAO.buscar(Estoque.class, id); 
		
		if(estoque==null)
			return; 
		
		estoqueDAO.iniciarTransacao();
		estoqueDAO.remover(estoque);
		estoqueDAO.finalizarTransacao();
		
	}
	
	public void alterarEstoque(Estoque estoque)throws DAOException{
		
		Estoque estoquePesquisado = estoqueDAO.buscar(Estoque.class,estoque.getId()); 
		
		if(estoquePesquisado==null)
			return; 
		
		estoquePesquisado.setDescricao(estoque.getDescricao());
		estoquePesquisado.setNome(estoque.getNome());
		
		estoqueDAO.iniciarTransacao();
		estoqueDAO.alterar(estoquePesquisado);
		estoqueDAO.finalizarTransacao();
		
	
	}
	
}
