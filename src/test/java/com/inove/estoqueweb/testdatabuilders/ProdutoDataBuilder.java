package com.inove.estoqueweb.testdatabuilders;


import org.springframework.stereotype.Component;

import br.com.braveti.estoqueweb.dominio.Categoria;
import br.com.braveti.estoqueweb.dominio.Estoque;
import br.com.braveti.estoqueweb.dominio.Fornecedor;
import br.com.braveti.estoqueweb.dominio.Produto;


public class ProdutoDataBuilder {

	private Produto produto; 
	
	
	public ProdutoDataBuilder(){
		
		produto = new Produto(); 
	}
	
	public ProdutoDataBuilder nome(String nome){
		
		produto.setNome(nome); 
		
		return this; 
	}
	
	public ProdutoDataBuilder daCategoria(String nome){
		
		Categoria categoria = new Categoria();
		categoria.setNome(nome);
		produto.setCategoria(categoria);
		return this; 
	}
	
	public ProdutoDataBuilder noEstoque(String nome){
		
		Estoque estoque = new Estoque(); 
		estoque.setNome(nome);
		produto.setEstoqueAtual(estoque);
		return this; 
	}
	
	public ProdutoDataBuilder fornecidoPor(String razaoSocial){
		
		Fornecedor fornecedor = new Fornecedor(); 
		fornecedor.setRazaoSocial(razaoSocial);
		produto.setFornecedor(fornecedor);
		return this; 
	}
	
	public Produto criar(){
		
		return produto; 
	}
	
	
}
