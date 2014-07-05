package com.inove.estoqueweb.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.inove.estoqueweb.dao.CategoriaDAO;
import com.inove.estoqueweb.dao.DAOException;
import com.inove.estoqueweb.dao.EstoqueDAO;
import com.inove.estoqueweb.dao.FornecedorDAO;
import com.inove.estoqueweb.dao.MovimentacaoDAO;
import com.inove.estoqueweb.dao.ProdutoDAO;
import com.inove.estoqueweb.dominio.Categoria;
import com.inove.estoqueweb.dominio.Estoque;
import com.inove.estoqueweb.dominio.Fornecedor;
import com.inove.estoqueweb.dominio.Movimentacao;
import com.inove.estoqueweb.dominio.Produto;
import com.inove.estoqueweb.dominio.TipoMovimentacao;

@Scope("prototype")
@Component
public class ProdutoFacade {

	private ProdutoDAO produtoDAO; 
	private CategoriaDAO categoriaDAO; 
	private EstoqueDAO estoqueDAO; 
	private FornecedorDAO fornecedorDAO; 
	private MovimentacaoDAO movimentacaoDAO; 
	
	@Autowired
	public ProdutoFacade(ProdutoDAO produtoDAO,CategoriaDAO categoriaDAO,EstoqueDAO estoqueDAO,
			FornecedorDAO fornecedorDAO,MovimentacaoDAO movimentacaoDAO){
		
		this.produtoDAO = produtoDAO; 
		this.categoriaDAO = categoriaDAO; 
		this.estoqueDAO = estoqueDAO; 
		this.fornecedorDAO = fornecedorDAO; 
		this.movimentacaoDAO  = movimentacaoDAO; 
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
		produtoDAO.finalizarTransacaoEFecharConexao();
		
		categoriaDAO.fecharConexao();
		fornecedorDAO.fecharConexao();
		
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
		
		produtoDAO.fecharConexao();
		
		
	}
	
	public void alterarProduto(Produto produto)throws DAOException{
		
		Produto produtoPesquisado = produtoDAO.buscar(Produto.class,produto.getId()); 
		Categoria categoriaPesquisada = categoriaDAO.buscar(Categoria.class,produto.getCategoria().getId());
		Fornecedor fornecedorPesquisado = fornecedorDAO.buscar(Fornecedor.class,produto.getFornecedor().getId()); 
		Estoque estoquePesquisado = estoqueDAO.buscar(Estoque.class,produto.getEstoqueAtual().getId()); 
		
		if(produtoPesquisado==null){
			
		
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
		
		categoriaDAO.fecharConexao();
		fornecedorDAO.fecharConexao();
		produtoDAO.fecharConexao();
			
	}
	
	public void registrarEntrada(Long produtoId, Integer quantidade)throws DAOException,IllegalStateException{
		
		Produto produto = null; 
		
		synchronized(produtoId.toString().intern()){
			
			produto = produtoDAO.buscar(Produto.class, produtoId); 
			
			if(produto!=null){
							
				produto.adicionarQuantidade(quantidade);
			
				produtoDAO.iniciarTransacao();
				produtoDAO.salvar(produto);
				produtoDAO.finalizarTransacao();
			
			}
			
		}
		
		produtoDAO.fecharConexao();
		
		if(produto!=null)		
			criarMovimentacaoDoProduto(produto,TipoMovimentacao.ENTRADA,quantidade); 
				
		 
	}
	
	public void registrarSaida(Long produtoId, Integer quantidade)throws DAOException,IllegalStateException{
		
		Produto produto = null; 
		
		synchronized(produtoId.toString().intern()){
			
			produto = produtoDAO.buscar(Produto.class,produtoId); 
			
			if(produto!=null){
				
				if((produto.getQuantidadeAtual()-quantidade)<0)
					throw new IllegalStateException("O estoque não possui a quantidade especificada");
				
				produto.removerQuantidade(quantidade);
			
				produtoDAO.iniciarTransacao();
				produtoDAO.salvar(produto); 
				produtoDAO.finalizarTransacao(); 
			
			}
		}
		
		produtoDAO.fecharConexao();
		
		if(produto!=null)
			criarMovimentacaoDoProduto(produto,TipoMovimentacao.SAIDA,quantidade); 
	}
	
	
	private void criarMovimentacaoDoProduto(Produto produto, TipoMovimentacao tipo,Integer quantidade)
			throws DAOException{
		
		Movimentacao movimentacao = new Movimentacao(); 
		movimentacao.setProduto(produto);
		movimentacao.setQuantidade(quantidade);
		movimentacao.setTipo(tipo);
		
		movimentacaoDAO.iniciarTransacao();
		movimentacaoDAO.salvar(movimentacao);
		movimentacaoDAO.finalizarTransacaoEFecharConexao();
	
	
	}
	
	private void fecharConexoes(){
		
		movimentacaoDAO.fecharConexao();
		produtoDAO.fecharConexao();
		categoriaDAO.fecharConexao();
		estoqueDAO.fecharConexao();
		fornecedorDAO.fecharConexao();
	}
}

