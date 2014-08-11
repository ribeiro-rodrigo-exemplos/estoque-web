package br.com.braveti.estoqueweb.dominio;

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
	private Long id; 
	
	
	public Produto(){
		
		this(null,null,null);
		
	}
	
	
	public Produto(String nome, Categoria categoria, Estoque estoqueAtual){
		
		this(nome,categoria,estoqueAtual,null,0,0); 
	}
	
	public Produto(String nome, Categoria categoria, Estoque estoqueAtual,Fornecedor fornecedor,
			Integer quantidadeAtual, Integer quantidadeMinima){
		
		setNome(nome);
		setCategoria(categoria);
		setEstoqueAtual(estoqueAtual);
		setFornecedor(fornecedor); 
		setQuantidadeAtual(quantidadeAtual); 
		setQuantidadeMinima(quantidadeMinima); 
		
	}
		
	public Integer getQuantidadeAtual() {
		return quantidadeAtual;
	}
	public void setQuantidadeAtual(Integer quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}
	public Long getId() {
		return id;
	}
	
	public void setId(Long id){
		
		this.id = id; 
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
	
	public void adicionarQuantidade(Integer quantidade){
		
		this.quantidadeAtual+=quantidade; 
	}
	
	public void removerQuantidade(Integer quantidade){
		
		this.quantidadeAtual-=quantidade; 
	}
}
