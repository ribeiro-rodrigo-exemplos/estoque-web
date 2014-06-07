package com.inove.estoqueweb.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.inove.estoqueweb.dominio.Movimentacao;
import com.inove.estoqueweb.dominio.Produto;

public class MovimentacaoDAO extends GenericoDAO<Movimentacao> {

	public MovimentacaoDAO(Session session){
		
		super(session);
	}
	
	public List<Movimentacao> listarMovimentacoesPorData(Date dataInicial, Date dataFinal){
		
		return null; 
	}
	
	
	public List<Movimentacao> listarMovimentacoesDoProdutoPorData(Produto produto,Date dataInicial, Date dataFinal){
		
		return null; 
	}
	
	public List<Movimentacao> listarMovimentacoesDoProduto(Produto produto){
		
		return null; 
	}
	
}
