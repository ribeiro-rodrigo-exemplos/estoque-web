package com.inove.estoqueweb.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.inove.estoqueweb.dao.CategoriaDAO;
import com.inove.estoqueweb.dao.DAOException;
import com.inove.estoqueweb.dao.EstoqueDAO;
import com.inove.estoqueweb.dao.FornecedorDAO;
import com.inove.estoqueweb.dao.ProdutoDAO;
import com.inove.estoqueweb.dominio.Categoria;
import com.inove.estoqueweb.dominio.Estoque;
import com.inove.estoqueweb.dominio.Fornecedor;
import com.inove.estoqueweb.dominio.Produto;

@Scope("prototype")
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
		
		Categoria categoria = categoriaDAO.buscar(Categoria.class,produto.getCategoria().getId()); 
		Estoque estoque = estoqueDAO.buscar(Estoque.class,produto.getEstoqueAtual().getId()); 
		Fornecedor fornecedor = fornecedorDAO.buscar(Fornecedor.class,produto.getFornecedor().getId()); 
		
		produto.setCategoria(categoria); 
		produto.setFornecedor(fornecedor);
		produto.setEstoqueAtual(estoque);
		
		produtoDAO.iniciarTransacao();
		produtoDAO.salvar(produto);
		produtoDAO.finalizarTransacao();
		
		return produto.getId();  
		
	}
	
	public void removerProduto(Produto produto)throws DAOException{
		
		removerProduto(produto.getId()); 
	}
	
	public void removerProduto(Long id)throws DAOException{
		
		Produto produto = produtoDAO.buscar(Produto.class, id); 
		
		if(produto!=null){
			
			produtoDAO.iniciarTransacao();
			produtoDAO.remover(produto);
			produtoDAO.finalizarTransacao();
			
		}
		
		
	}
	
	public void alterarProduto(Produto produto)throws DAOException{
		
		Produto produtoPesquisado = produtoDAO.buscar(Produto.class,produto.getId()); 
		Categoria categoriaPesquisada = categoriaDAO.buscar(Categoria.class,produto.getCategoria().getId());
		Fornecedor fornecedorPesquisado = fornecedorDAO.buscar(Fornecedor.class,produto.getFornecedor().getId()); 
		Estoque estoquePesquisado = estoqueDAO.buscar(Estoque.class,produto.getEstoqueAtual().getId()); 
		
		if(produtoPesquisado==null)
			return; 
		
		produtoPesquisado.setCategoria(categoriaPesquisada);
		produtoPesquisado.setDescricao(produto.getDescricao());
		produtoPesquisado.setEstoqueAtual(estoquePesquisado);
		produtoPesquisado.setFornecedor(fornecedorPesquisado);
		produtoPesquisado.setNome(produto.getNome());
		produtoPesquisado.setQuantidadeMinima(produto.getQuantidadeMinima());
		
		produtoDAO.iniciarTransacao(); 
		produtoDAO.alterar(produtoPesquisado);
		produtoDAO.finalizarTransacao();
			
	}
	
}

