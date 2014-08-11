package br.com.braveti.estoqueweb.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.braveti.estoqueweb.dao.DAOException;
import br.com.braveti.estoqueweb.dao.EstoqueDAO;
import br.com.braveti.estoqueweb.dominio.Estoque;

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
		
		estoqueDAO.finalizarTransacaoEFecharConexao();
		
		return estoque.getId(); 
		
	}
	
	public void removerEstoque(Estoque estoque)throws DAOException{
		
		removerEstoque(estoque.getId());
	}
	
	public void removerEstoque(Long id)throws DAOException{
		
		Estoque estoque = estoqueDAO.buscar(Estoque.class, id); 
		
		if(estoque!=null){
			
			estoqueDAO.iniciarTransacao();
			estoqueDAO.remover(estoque);
			estoqueDAO.finalizarTransacao();
		
		}
		
		estoqueDAO.fecharConexao();
		
	}
	
	public void alterarEstoque(Estoque estoque)throws DAOException{
		
		Estoque estoquePesquisado = estoqueDAO.buscar(Estoque.class,estoque.getId()); 
		
		if(estoquePesquisado!=null){
			
		
			estoquePesquisado.setDescricao(estoque.getDescricao());
			estoquePesquisado.setNome(estoque.getNome());
		
			estoqueDAO.iniciarTransacao();
			estoqueDAO.alterar(estoquePesquisado);
			estoqueDAO.finalizarTransacao();
		
		}
		
		estoqueDAO.fecharConexao();
		
	
	}
	
}
