package com.inove.estoqueweb.dto;

public class CategoriaDTO {

	private Integer id; 
	private String nome; 
	private String descricao;
	
	public CategoriaDTO(Integer id){
		
		this.id = id; 
	}
	
	public Integer getId() {
		return id;
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
}
