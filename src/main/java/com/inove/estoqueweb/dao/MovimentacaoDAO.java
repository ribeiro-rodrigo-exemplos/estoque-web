package com.inove.estoqueweb.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inove.estoqueweb.dominio.Estoque;
import com.inove.estoqueweb.dominio.Movimentacao;
import com.inove.estoqueweb.dominio.Produto;

@Repository
public class MovimentacaoDAO extends GenericoDAO<Movimentacao> {

	@Autowired
	public MovimentacaoDAO(FabricaDeSessao fabrica){
		
		super(fabrica);
	}
	
	public List<Movimentacao> listarMovimentacoesDoProdutoPorPeriodo(Produto produto,Date dataInicial, Date dataFinal)
	throws DAOException{
		
		return null; 
	}
	
	public List<Movimentacao> listarMovimentacoesDoProduto(Produto produto)throws DAOException{
		
		return null; 
	}
	
	public List<Movimentacao> listarMovimentacoesNoEstoque(Estoque estoque)throws DAOException{
		
		return null; 
	}
	
	public List<Movimentacao> listarMovimentacoesNoEstoquePorPeriodo(Estoque estoque, Date dataInicial, Date dataFinal)
	throws DAOException{
		
		return null; 
	}
	
	public List<Movimentacao> listarMovimentacoesDoProdutoNoEstoque(Estoque estoque, Produto produto)throws DAOException{
		
		return null; 
	}
	 
}
