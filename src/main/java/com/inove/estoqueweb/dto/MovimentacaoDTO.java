package com.inove.estoqueweb.dto;

import java.util.*; 

import com.inove.estoqueweb.dominio.TipoMovimentacao;

public class MovimentacaoDTO {

	private Integer id; 
	private Integer produtoId; 
	private Integer quantidade; 
	private Date dataHora; 
	private TipoMovimentacao tipo;
	
	public MovimentacaoDTO(Integer id){
		
		this.id = id; 
	}
	
	public Integer getId() {
		return id;
	}
	
	public Integer getProdutoId() {
		return produtoId;
	}
	public void setProdutoId(Integer produtoId) {
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
