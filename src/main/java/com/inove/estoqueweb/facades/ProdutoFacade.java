package com.inove.estoqueweb.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inove.estoqueweb.dao.CategoriaDAO;
import com.inove.estoqueweb.dao.DAOException;
import com.inove.estoqueweb.dao.EstoqueDAO;
import com.inove.estoqueweb.dao.FornecedorDAO;
import com.inove.estoqueweb.dao.ProdutoDAO;
import com.inove.estoqueweb.dominio.Produto;

@Component
public class ProdutoFacade {

	private ProdutoDAO produtoDAO; 
	private CategoriaDAO categoriaDAO; 
	private EstoqueDAO estoqueDAO; 
	private FornecedorDAO fornecedorDAO; 
	
	@Autowired
	public ProdutoFacade(ProdutoDAO produtoDAO,CategoriaDAO categoriaDAO,EstoqueDAO estoqueDAO,
			FornecedorDAO fornecedorDAO){
		
		this.produtoDAO = produtoDAO; 
		this.categoriaDAO = categoriaDAO; 
		this.estoqueDAO = estoqueDAO; 
		this.fornecedorDAO = fornecedorDAO; 
	}
	
	public Long criarProduto(Produto produto)throws DAOException{
		
		produtoDAO.salvar(produto);
		produtoDAO.getSession().flush();
		
		return produto.getId();  
		
	}
	
	public void removerProduto(Produto produto)throws DAOException{
		
		removerProduto(produto.getId()); 
	}
	
	public void removerProduto(Long id)throws DAOException{
		
		Produto produto = produtoDAO.buscar(Produto.class, id); 
		
		if(produto!=null)
			produtoDAO.remover(produto);
		
		produtoDAO.getSession().flush();
		
	}
	
	public void alterarProduto(Produto produto)throws DAOException{
		
		Produto produtoPesquisado = produtoDAO.buscar(Produto.class,produto.getId()); 
		
		if(produtoPesquisado==null)
			return; 
		
		produtoPesquisado.setCategoria(produto.getCategoria());
		produtoPesquisado.setDescricao(produto.getDescricao());
		produtoPesquisado.setEstoqueAtual(produto.getEstoqueAtual());
		produtoPesquisado.setFornecedor(produto.getFornecedor());
		produtoPesquisado.setNome(produto.getNome());
		produtoPesquisado.setQuantidadeMinima(produto.getQuantidadeMinima());
		
		produtoDAO.alterar(produtoPesquisado);
		produtoDAO.getSession().flush();
			
	}
	
}

