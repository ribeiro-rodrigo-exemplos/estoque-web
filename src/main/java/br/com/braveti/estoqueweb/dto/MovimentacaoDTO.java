package br.com.braveti.estoqueweb.dto;

import java.util.*; 

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import br.com.braveti.estoqueweb.dominio.TipoMovimentacao;

@XmlAccessorType(XmlAccessType.FIELD)
public class MovimentacaoDTO {

	private Long id; 
	private Long produtoId;
	private Long estoqueId; 
	private Integer quantidade; 
	private Date dataHora; 
	private TipoMovimentacao tipo;
	
	public MovimentacaoDTO(){
		
	}
	
	public MovimentacaoDTO(Long id){
		
		this.id = id; 
	}
	
	public void setId(Long id){
		
		this.id = id; 
	}
	
	
	public Long getId() {
		return id;
	}
	
	public Long getEstoqueId(){
		
		return estoqueId;
	}
	
	public void setEstoqueId(Long estoqueId){
		
		this.estoqueId = estoqueId; 
	}
	
	public Long getProdutoId() {
		return produtoId;
	}
	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Date getDataHora() {
		return dataHora;
	}
	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
	public TipoMovimentacao getTipo() {
		return tipo;
	}
	public void setTipo(TipoMovimentacao tipo) {
		this.tipo = tipo;
	} 
	
}
