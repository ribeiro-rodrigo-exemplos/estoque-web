package com.inove.estoqueweb.webservice;

import com.inove.estoqueweb.dto.*;

import java.util.List; 

public class EstoqueWebService {

	public Long criarCategoria(CategoriaDTO categoria){
		
		return null; 
	}
	
	public Boolean removerCategoria(Long idCategoria){
		
		return false; 
	}
	
	public CategoriaDTO buscarCategoria(Long idCategoria){
		
		return null; 
	}
	
	public CategoriaDTO alterarCategoria(CategoriaDTO categoria){
		
		return null; 
	}
	
	public List<CategoriaDTO> listarCategorias(){
		
		return null; 
	}
	
	public Long criarEstoque(EstoqueDTO estoque){
		
		return null; 
	}
	
	public Boolean removerEstoque(Long idEstoque){
		
		return false; 
	}
	
	public EstoqueDTO alterarEstoque(EstoqueDTO estoque){
		
		return null; 
	}
	
	public EstoqueDTO buscarEstoque(Long idEstoque){
		
		return null; 
	}
	
	public List<EstoqueDTO> listarEstoques(){
		
		return null; 
	}
	
	public Long criarFornecedor(FornecedorDTO fornecedor){
		
		return null; 
		
	}
	
	public Boolean removerFornecedor(Integer idFornecedor){
		
		return false; 
	}
	
	public FornecedorDTO alterarFornecedor(FornecedorDTO fornecedor){
		
		return null; 
	}
	
	public FornecedorDTO buscarFornecedor(Long fornecedorId){
		
		return null; 
	}
	
	public List<FornecedorDTO> listarFornecedores(){
		
		return null; 
	}
	
	public Long criarProduto(ProdutoDTO produto){
		
		return null; 
	}
	
	public Boolean removerProduto(Long produtoId){
		
		return null; 
	}
	
	public ProdutoDTO buscarProduto(Long produtoId){
		
		return null;
	}
	
	public ProdutoDTO alterarProduto(ProdutoDTO produto){
		
		return null; 
	}
	
	public List<ProdutoDTO> listarProdutos(){
		
		return null; 
	}
	
	public void registrarEntradaDeProduto(Long produtoId, Integer quantidade){
		
		
	}
	
	public void registrarSaidaDeProduto(Long produtoId, Integer quantidade){
		
		
	}
}
