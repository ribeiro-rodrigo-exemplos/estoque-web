package com.inove.estoqueweb.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Produto {

	@Column(name="nome",nullable=false,unique=true)
	private String nome;
	@Column(name="descricao",nullable=true)
	@Lob
	private String descricao; 
	@Column(name="quantidadeMinima",nullable=true)
	private Integer quantidadeMinima = 0; 
	@Column(name="quantidadeAtual",nullable=true)
	private Integer quantidadeAtual = 0; 
	@ManyToOne
	private Categoria categoria;
	@ManyToOne
	private Estoque estoqueAtual; 
	@ManyToOne
	private Fornecedor fornecedor;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id; 
	
	public Integer getQuantidadeAtual() {
		return quantidadeAtual;
	}
	public void setQuantidadeAtual(Integer quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}
	public Integer getId() {
		return id;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	public String getNome() {
		return nome;
	}
	public Estoque getEstoqueAtual() {
		return estoqueAtual;
	}
	public void setEstoqueAtual(Estoque estoqueAtual) {
		this.estoqueAtual = estoqueAtual;
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
	public Integer getQuantidadeMinima() {
		return quantidadeMinima;
	}
	public void setQuantidadeMinima(Integer quantidadeMinima) {
		this.quantidadeMinima = quantidadeMinima;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	} 
}
