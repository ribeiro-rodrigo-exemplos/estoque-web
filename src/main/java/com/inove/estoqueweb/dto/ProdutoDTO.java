package com.inove.estoqueweb.dto;

public class ProdutoDTO {

	private Integer id; 
	private Integer categoriaId; 
	private Integer estoqueAtualId; 
	private Integer fornecedorId; 
	private Integer quantidadeMinima; 
	private Integer quantidadeAtual; 
	private String nome; 
	private String descricao;
	
	public ProdutoDTO(Integer id){
		
		this.id = id; 
	}
	
	public Integer getQuantidadeAtual() {
		return quantidadeAtual;
	}

	public void setQuantidadeAtual(Integer quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}
	
	public Integer getId() {
		return id;
	}

	public Integer getCategoriaId() {
		return categoriaId;
	}
	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}
	public Integer getEstoqueAtualId() {
		return estoqueAtualId;
	}
	public void setEstoqueAtualId(Integer estoqueId) {
		this.estoqueAtualId = estoqueId;
	}
	public Integer getFornecedorId() {
		return fornecedorId;
	}
	public void setFornecedorId(Integer fornecedorId) {
		this.fornecedorId = fornecedorId;
	}
	public Integer getQuantidadeMinima() {
		return quantidadeMinima;
	}
	public void setQuantidadeMinima(Integer quantidadeMinima) {
		this.quantidadeMinima = quantidadeMinima;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	} 
	
	public Boolean precisaDeReposicao(){
		
		return (quantidadeAtual<=quantidadeMinima);
	}
	
}
