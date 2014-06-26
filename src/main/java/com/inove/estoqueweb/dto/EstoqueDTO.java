package com.inove.estoqueweb.dto;

public class EstoqueDTO {

	private Long id; 
	private String nome; 
	private String descricao;
	
	public EstoqueDTO(){
		
	}
	
	public EstoqueDTO(Long id){
		
		this.id = id; 
	}
	
	public void setId(Long id){
		
		this.id = id; 
	}
	
	public Long getId() {
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
