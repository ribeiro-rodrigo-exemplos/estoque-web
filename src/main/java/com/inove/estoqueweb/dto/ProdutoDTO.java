package com.inove.estoqueweb.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProdutoDTO {

	private Long id; 
	private Long categoriaId; 
	private Long estoqueAtualId; 
	private Long fornecedorId; 
	private Integer quantidadeMinima; 
	private Integer quantidadeAtual; 
	private String nome; 
	private String descricao;
	
	public ProdutoDTO(){
		
		
	}
	
	public ProdutoDTO(Long id){
		
		this.id = id; 
	}
	
	public void setQuantidadeAtual(Integer quantidade){
		
		this.quantidadeAtual = quantidade; 
	}
	
	public Integer getQuantidadeAtual() {
		return quantidadeAtual;
	}
	
	
	public void setId(Long id){
		
		this.id = id; 
	}
	
	
	public Long getId() {
		return id;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}
	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}
	public Long getEstoqueAtualId() {
		return estoqueAtualId;
	}
	public void setEstoqueAtualId(Long estoqueId) {
		this.estoqueAtualId = estoqueId;
	}
	public Long getFornecedorId() {
		return fornecedorId;
	}
	public void setFornecedorId(Long fornecedorId) {
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
