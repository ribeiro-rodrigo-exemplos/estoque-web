package com.inove.estoqueweb.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.inove.estoqueweb.dominio.Estoque;
import com.inove.estoqueweb.dominio.Movimentacao;
import com.inove.estoqueweb.dominio.Produto;

@Repository
@Scope("prototype")
public class MovimentacaoDAO extends GenericoDAO<Movimentacao> {

	@Autowired
	public MovimentacaoDAO(FabricaDeSessao fabrica){
		
		super(fabrica);
	}
	
	public List<Movimentacao> listarMovimentacoesDoProdutoPorPeriodo(Long produtoId,Date dataHoraInicial, Date dataHoraFinal)
	throws DAOException{
		
		Query query = session.createQuery("from Movimentacao e where e.produto.id=:produtoId and (e.dataHora>=:dataHoraInicial and e.dataHora<=:dataHoraFinal)");
		
		query.setParameter("produtoId",produtoId);
		query.setParameter("dataHoraInicial",dataHoraInicial); 
		query.setParameter("dataHoraFinal",dataHoraFinal); 
		
		List<Movimentacao> movimentacoes = query.list(); 
		
		return movimentacoes; 
	}
	
	public List<Movimentacao> listarMovimentacoesDoProduto(Long produtoId)throws DAOException{
		
		Query query = session.createQuery("from Movimentacao e where e.produto.id=:produtoId"); 
		
		query.setParameter("produtoId",produtoId);
		
		List<Movimentacao> movimentacoes = query.list(); 
		
		return movimentacoes; 
	}
	
	public List<Movimentacao> listarMovimentacoesNoEstoque(Long estoqueId)throws DAOException{
		
		Query query = session.createQuery("from Movimentacao e where e.estoque.id=:estoqueId"); 
		
		query.setParameter("estoqueId", estoqueId);
		
		List<Movimentacao> movimentacoes = query.list(); 
		
		return movimentacoes; 
	}
	
	public List<Movimentacao> listarMovimentacoesNoEstoquePorPeriodo(Long estoqueId, Date dataHoraInicial, Date dataHoraFinal)
	throws DAOException{
		
		Query query = session.createQuery("from Movimentacao e where e.estoque.id=:estoqueId and (e.dataHora between :dataHoraInicial and :dataHoraFinal)"); 
		
		query.setParameter("estoqueId",estoqueId); 
		query.setParameter("dataHoraInicial",dataHoraInicial); 
		query.setParameter("dataHoraFinal",dataHoraFinal); 
		
		List<Movimentacao> movimentacoes = query.list(); 
		
		return movimentacoes; 
	}
		 
}
