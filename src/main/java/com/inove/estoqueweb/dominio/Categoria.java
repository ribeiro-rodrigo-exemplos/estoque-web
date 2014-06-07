package com.inove.estoqueweb.dominio;

import java.util.List;

import javax.persistence.*;;

@Entity
public class Categoria {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; 
	
	@Column(name="descricao",nullable=true)
	@Lob
	private String descricao; 
	@Column(name="nome",nullable=false,unique=true)
	private String nome;
	
	@OneToMany(mappedBy="id",fetch=FetchType.LAZY)
	private List<Produto> produtos; 
	
	public Long getId() {
		return id;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	} 
}
