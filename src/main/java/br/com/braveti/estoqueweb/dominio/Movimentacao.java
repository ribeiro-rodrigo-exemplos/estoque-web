package br.com.braveti.estoqueweb.dominio;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Movimentacao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; 
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHora;
	@Column(name="quantidade",nullable=false)
	private Integer quantidade; 
	@ManyToOne
	private Produto produto;
	@Enumerated(EnumType.STRING)
	private TipoMovimentacao tipo; 
	@ManyToOne
	private Estoque estoque; 
	
	public Movimentacao(){
		
		this(null,null,null);
	}
	
	public Movimentacao(Produto produto,TipoMovimentacao tipo,Integer quantidade){
		
		setProduto(produto);
		setTipo(tipo);
		setQuantidade(quantidade);
		setDataHora(new Date());
	}
	
	
	public TipoMovimentacao getTipo() {
		return tipo;
	}
	public void setTipo(TipoMovimentacao tipo) {
		this.tipo = tipo;
	}
	public Date getDataHora() {
		return dataHora;
	}
	
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
		
		if(produto!=null)
			setEstoque(produto.getEstoqueAtual()); 
	}
	public Estoque getEstoque() {
		return estoque;
	}
	private void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}  
	
	private void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	public void setId(Long id){
		
		this.id = id; 
	}
	
}
