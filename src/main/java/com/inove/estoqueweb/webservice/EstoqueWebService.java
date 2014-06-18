package com.inove.estoqueweb.webservice;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inove.estoqueweb.dao.CategoriaDAO;
import com.inove.estoqueweb.dao.DAOException;
import com.inove.estoqueweb.dominio.Categoria;
import com.inove.estoqueweb.dominio.Movimentacao;
import com.inove.estoqueweb.dto.CategoriaDTO;
import com.inove.estoqueweb.dto.CategoriaDTOConversor;
import com.inove.estoqueweb.dto.DTOConversor;
import com.inove.estoqueweb.dto.EstoqueDTO;
import com.inove.estoqueweb.dto.FornecedorDTO;
import com.inove.estoqueweb.dto.ProdutoDTO;
import com.inove.estoqueweb.facades.CategoriaFacade;


@WebService(name="estoque-web")
public class EstoqueWebService {

	private CategoriaFacade categoriaFacade; 
	private DTOConversor<CategoriaDTO,Categoria> categoriaConversor; 
	private CategoriaDAO categoriaDAO; 
	
	public EstoqueWebService(){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml"); 
		
		categoriaFacade = context.getBean(CategoriaFacade.class); 
		categoriaConversor = context.getBean(CategoriaDTOConversor.class); 
		categoriaDAO = context.getBean(CategoriaDAO.class); 
		
	}
	
	@WebMethod
	public Long criarCategoria(@WebParam(name="categoria")CategoriaDTO categoriaDTO){
		
		Categoria categoria = categoriaConversor.converterTransferencia(categoriaDTO); 
		
		try{
		
			Long id = categoriaFacade.criarCategoria(categoria);
			return id; 
		
		}
		catch(DAOException e){
			
			e.printStackTrace();
		}
		
		return null; 
		
		
	}
	
	public void removerCategoria(Long idCategoria){
		
		
	}
	
	@WebMethod
	public CategoriaDTO buscarCategoria(@WebParam(name="id")Long idCategoria){
		
		try{
		
			Categoria categoria = categoriaDAO.buscar(Categoria.class,idCategoria); 
		
			CategoriaDTO dto = categoriaConversor.converterDominio(categoria); 
			
			return dto; 
		
		}catch(DAOException e){
			
			e.printStackTrace();
		}
		
		return null; 
	}
	
	public void alterarCategoria(CategoriaDTO categoria){
		
		
	}
	
	public List<CategoriaDTO> listarCategorias(){
		
		return null; 
	}
	
	public Long criarEstoque(EstoqueDTO estoque){
		
		return null; 
	}
	
	public Boolean removerEstoque(Long idEstoque){
		
		return false; 
	}
	
	public EstoqueDTO alterarEstoque(EstoqueDTO estoque){
		
		return null; 
	}
	
	public EstoqueDTO buscarEstoque(Long idEstoque){
		
		return null; 
	}
	
	public List<EstoqueDTO> listarEstoques(){
		
		return null; 
	}
	
	public Long criarFornecedor(FornecedorDTO fornecedor){
		
		return null; 
		
	}
	
	public Boolean removerFornecedor(Integer idFornecedor){
		
		return false; 
	}
	
	public FornecedorDTO alterarFornecedor(FornecedorDTO fornecedor){
		
		return null; 
	}
	
	public FornecedorDTO buscarFornecedor(Long fornecedorId){
		
		return null; 
	}
	
	public List<FornecedorDTO> listarFornecedores(){
		
		return null; 
	}
	
	public Long criarProduto(ProdutoDTO produto){
		
		return null; 
	}
	
	public Boolean removerProduto(Long produtoId){
		
		return null; 
	}
	
	public ProdutoDTO buscarProduto(Long produtoId){
		
		return null;
	}
	
	public ProdutoDTO alterarProduto(ProdutoDTO produto){
		
		return null; 
	}
	
	public List<ProdutoDTO> listarProdutos(){
		
		return null; 
	}
	
	public void registrarEntradaDeProduto(Long produtoId, Long estoqueId, Integer quantidade){
		
		
	}
	
	public void registrarSaidaDeProduto(Long produtoId, Integer quantidade){
		
		
	}
		
	public List<Movimentacao> listarMovimentacoesDoProdutoPorPeriodo(Long produtoId,Date dataInicial, Date dataFinal){
		
		return null; 
	}
	
	public List<Movimentacao> listarMovimentacoesDoProduto(Long produtoId){
		
		return null; 
	}
	
	public List<Movimentacao> listarMovimentacoesNoEstoque(Long estoqueId){
		
		return null; 
	}
	
	public List<Movimentacao> listarMovimentacoesNoEstoquePorPeriodo(Long estoqueId, Date dataInicial, Date dataFinal){
		
		return null; 
	}
	
	public List<Movimentacao> listarMovimentacoesDoProdutoNoEstoque(Long estoqueId, Long produtoId){
		
		return null; 
	}
	 
}
