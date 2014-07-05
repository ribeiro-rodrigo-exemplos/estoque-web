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
import com.inove.estoqueweb.dao.EstoqueDAO;
import com.inove.estoqueweb.dao.FornecedorDAO;
import com.inove.estoqueweb.dao.MovimentacaoDAO;
import com.inove.estoqueweb.dao.ProdutoDAO;
import com.inove.estoqueweb.dominio.Categoria;
import com.inove.estoqueweb.dominio.Estoque;
import com.inove.estoqueweb.dominio.Fornecedor;
import com.inove.estoqueweb.dominio.Movimentacao;
import com.inove.estoqueweb.dominio.Produto;
import com.inove.estoqueweb.dto.CategoriaDTO;
import com.inove.estoqueweb.dto.CategoriaDTOConversor;
import com.inove.estoqueweb.dto.DTOConversor;
import com.inove.estoqueweb.dto.EstoqueDTO;
import com.inove.estoqueweb.dto.EstoqueDTOConversor;
import com.inove.estoqueweb.dto.FornecedorDTO;
import com.inove.estoqueweb.dto.FornecedorDTOConversor;
import com.inove.estoqueweb.dto.MovimentacaoDTO;
import com.inove.estoqueweb.dto.MovimentacaoDTOConversor;
import com.inove.estoqueweb.dto.ProdutoDTO;
import com.inove.estoqueweb.dto.ProdutoDTOConversor;
import com.inove.estoqueweb.facades.CategoriaFacade;
import com.inove.estoqueweb.facades.EstoqueFacade;
import com.inove.estoqueweb.facades.FornecedorFacade;
import com.inove.estoqueweb.facades.ProdutoFacade;


@WebService(name="estoque-web")
public class EstoqueWebService {


	private DTOConversor<CategoriaDTO,Categoria> categoriaConversor; 
	private DTOConversor<EstoqueDTO,Estoque> estoqueConversor; 
	private DTOConversor<FornecedorDTO,Fornecedor> fornecedorConversor; 
	private DTOConversor<ProdutoDTO,Produto> produtoConversor; 
	private DTOConversor<MovimentacaoDTO,Movimentacao> movimentacaoConversor; 
	private ApplicationContext context; 
	
	public EstoqueWebService(){
		
		 context = new ClassPathXmlApplicationContext("spring-context.xml"); 
		
		categoriaConversor = context.getBean(CategoriaDTOConversor.class); 
		estoqueConversor = context.getBean(EstoqueDTOConversor.class); 
		fornecedorConversor = context.getBean(FornecedorDTOConversor.class); 
		produtoConversor = context.getBean(ProdutoDTOConversor.class); 
		movimentacaoConversor = context.getBean(MovimentacaoDTOConversor.class); 
		
		
	}
	
	@WebMethod
	public Long criarCategoria(@WebParam(name="categoria")CategoriaDTO categoriaDTO)
			throws EstoqueWebServiceException{
		
		
		CategoriaFacade categoriaFacade = context.getBean(CategoriaFacade.class); 
		
		Categoria categoria = categoriaConversor.converterTransferencia(categoriaDTO); 
		
		try{
		
			Long id = categoriaFacade.criarCategoria(categoria);
			return id; 
		
		}
		catch(DAOException e){
			
			dispararExcecao(e);
		}
		
		return null; 
	}
	
	@WebMethod
	public void removerCategoria(@WebParam(name="id")Long idCategoria)
			throws EstoqueWebServiceException{
		
		try{
			
			CategoriaFacade categoriaFacade = context.getBean(CategoriaFacade.class); 
			
			categoriaFacade.removerCategoria(idCategoria);
			
		}
		catch(DAOException e){
			
			dispararExcecao(e);
		}
		
	}
	
	@WebMethod
	public CategoriaDTO buscarCategoria(@WebParam(name="id")Long idCategoria)
			throws EstoqueWebServiceException{
		
		CategoriaDAO categoriaDAO = context.getBean(CategoriaDAO.class);
		
		try{
		
			
			Categoria categoria = categoriaDAO.buscar(Categoria.class,idCategoria); 
			
			if(categoria!=null){
		
				CategoriaDTO dto = (categoria!=null ? categoriaConversor.converterDominio(categoria):null); 
			
				return dto; 
			
			}
		
		}catch(DAOException e){
			
			dispararExcecao(e); 
		}
		finally{
			
			categoriaDAO.fecharConexao();
		}
		
		return null; 

	}
	
	@WebMethod
	public void alterarCategoria(@WebParam(name="categoria")CategoriaDTO categoriaDTO)
			throws EstoqueWebServiceException{
		
		try{
			
			CategoriaFacade categoriaFacade = context.getBean(CategoriaFacade.class); 
			
			Categoria categoria = categoriaConversor.converterTransferencia(categoriaDTO); 
			categoriaFacade.alterarCategoria(categoria);
			
		}
		catch(DAOException e){
			
			dispararExcecao(e);
		}
		
	}
	
	@WebMethod
	public List<CategoriaDTO> listarCategorias()throws EstoqueWebServiceException{
		
		CategoriaDAO categoriaDAO = context.getBean(CategoriaDAO.class); 
		
		try{
		
		
		List<Categoria> categorias = categoriaDAO.listarCategorias(); 
		
		List<CategoriaDTO> dtoLista = categoriaConversor.converterLista(categorias); 
		
		return dtoLista; 
		
		}catch(DAOException e){
			
			dispararExcecao(e);
		}
		finally{
			
			categoriaDAO.fecharConexao();
		}
		
		return null; 
	}
	
	@WebMethod
	public Long criarEstoque(@WebParam(name="estoque")EstoqueDTO estoqueDTO)throws EstoqueWebServiceException{
		
		EstoqueFacade estoqueFacade = context.getBean(EstoqueFacade.class); 
		
		Estoque estoque = estoqueConversor.converterTransferencia(estoqueDTO); 
		
		try{
		
			Long id = estoqueFacade.criarEstoque(estoque); 
		
			return id; 
		
		 }
		catch(DAOException e){
			
			dispararExcecao(e);
		}
		
		return null; 
	}
	
	@WebMethod
	public void removerEstoque(@WebParam(name="id")Long idEstoque)throws EstoqueWebServiceException{
		
		 EstoqueFacade estoqueFacade = context.getBean(EstoqueFacade.class); 
		 
		 try{
			 
			 estoqueFacade.removerEstoque(idEstoque);
		 
		 }
		 catch(DAOException e){
			 
			 dispararExcecao(e); 
		 }
	}
	
	@WebMethod
	public void alterarEstoque(@WebParam(name="estoque")EstoqueDTO estoqueDTO)throws EstoqueWebServiceException{
		
		EstoqueFacade estoqueFacade = context.getBean(EstoqueFacade.class); 
		
		Estoque estoque = estoqueConversor.converterTransferencia(estoqueDTO); 
		
		try{
		
			estoqueFacade.alterarEstoque(estoque);
		
		}
		catch(DAOException e){
			
			dispararExcecao(e);
		}
		 
	}
	
	@WebMethod
	public EstoqueDTO buscarEstoque(@WebParam(name="id")Long idEstoque)throws EstoqueWebServiceException{
		
		EstoqueDAO estoqueDAO  = context.getBean(EstoqueDAO.class); 
		
		try{
		
			Estoque estoque = estoqueDAO.buscar(Estoque.class, idEstoque); 
			
			if(estoque==null)
				return null; 
		
			EstoqueDTO estoqueDTO = estoqueConversor.converterDominio(estoque); 
		
			return estoqueDTO; 
		
		}catch(DAOException e){
			
			dispararExcecao(e);
		}
		finally{
			
			estoqueDAO.fecharConexao();
		}
		
		return null; 
	}
	
	@WebMethod
	public List<EstoqueDTO> listarEstoques()throws EstoqueWebServiceException{
		
		EstoqueDAO estoqueDAO  = context.getBean(EstoqueDAO.class); 
		
		try{
		
			List<Estoque> estoques = estoqueDAO.listarEstoques(); 
		
			List<EstoqueDTO> dtoLista = estoqueConversor.converterLista(estoques); 
		
			return dtoLista; 
		
		}catch(DAOException e){
			
			dispararExcecao(e); 
		}
		finally{
			
			estoqueDAO.fecharConexao(); 
		}
		
		return null; 
	}
	
	@WebMethod
	public Long criarFornecedor(@WebParam(name="fornecedor")FornecedorDTO fornecedorDTO)
			throws EstoqueWebServiceException{
		
		FornecedorFacade fornecedorFacade = context.getBean(FornecedorFacade.class); 
		Fornecedor fornecedor = fornecedorConversor.converterTransferencia(fornecedorDTO);
		
		try{
		
			Long id = fornecedorFacade.criarFornecedor(fornecedor); 
			
			return id; 
		
		}
		catch(DAOException e){
			
			dispararExcecao(e);
		}
		
		return null; 
		
	}
	
	@WebMethod
	public void removerFornecedor(@WebParam(name="id")Long idFornecedor)throws EstoqueWebServiceException{
		
		FornecedorFacade fornecedorFacade = context.getBean(FornecedorFacade.class); 
		
		try{
		
			fornecedorFacade.removerFornecedor(idFornecedor);
		
		}
		catch(DAOException e){
			
			dispararExcecao(e);
		}
		 
	}
	
	@WebMethod
	public void alterarFornecedor(@WebParam(name="fornecedor")FornecedorDTO fornecedorDTO)
			throws EstoqueWebServiceException{
		
		FornecedorFacade fornecedorFacade = context.getBean(FornecedorFacade.class); 
		
		try{
			
			Fornecedor fornecedor = fornecedorConversor.converterTransferencia(fornecedorDTO); 
			
			fornecedorFacade.alterarFornecedor(fornecedor);
			
		}
		catch(DAOException e){
			
			dispararExcecao(e);
		}
		 
	}
	
	@WebMethod
	public FornecedorDTO buscarFornecedor(@WebParam(name="id")Long fornecedorId)throws EstoqueWebServiceException{
		
		FornecedorDAO dao = context.getBean(FornecedorDAO.class); 
		
		try{
			
			Fornecedor fornecedor = dao.buscar(Fornecedor.class,fornecedorId);
			
			if(fornecedor!=null){
				
				FornecedorDTO dto = fornecedorConversor.converterDominio(fornecedor);
				
				return dto; 
			}
			 
			
		}
		catch(DAOException e){
			
			dispararExcecao(e);
		}
		finally{
			
			dao.fecharConexao();
		}
		
		return null; 
	}
	
	@WebMethod
	public List<FornecedorDTO> listarFornecedores()throws EstoqueWebServiceException{
		
		FornecedorDAO dao = context.getBean(FornecedorDAO.class); 
		
		try{
		
			List<Fornecedor> fornecedores = dao.listarFornecedores(); 
			
			List<FornecedorDTO> dtoLista = fornecedorConversor.converterLista(fornecedores);
			
			return dtoLista; 
		
		}
		catch(DAOException e){
			
			dispararExcecao(e);
		}
		
		return null; 
		
		
	}
	
	@WebMethod
	public Long criarProduto(@WebParam(name="produto")ProdutoDTO produtoDTO)throws EstoqueWebServiceException{
		
		ProdutoFacade facade = context.getBean(ProdutoFacade.class); 
		
		try{
			
			Produto produto = produtoConversor.converterTransferencia(produtoDTO); 
			
			Long id = facade.criarProduto(produto); 
			
			return id; 
			
		}
		catch(DAOException e){
			
			dispararExcecao(e);
		}
		
		return null; 
	}
	
	@WebMethod
	public void removerProduto(@WebParam(name="id")Long produtoId)throws EstoqueWebServiceException{
		
		
		ProdutoFacade facade = context.getBean(ProdutoFacade.class); 
		
		try{
			
			facade.removerProduto(produtoId);
			
		}
		catch(DAOException e){
			
			dispararExcecao(e);
		}
		

		
	}
	
	@WebMethod
	public ProdutoDTO buscarProduto(@WebParam(name="id")Long produtoId)throws EstoqueWebServiceException{
		
		ProdutoDAO dao = context.getBean(ProdutoDAO.class); 
		
		try{
			
			Produto produto = dao.buscar(Produto.class,produtoId); 
			
			if(produto!=null){
				
				ProdutoDTO dto = produtoConversor.converterDominio(produto); 
				return dto; 
			}
			
		}
		catch(DAOException e){
			
			dispararExcecao(e);
		}
		finally{
			
			dao.fecharConexao();
		}
		
		return null;
	}
	
	@WebMethod
	public void alterarProduto(@WebParam(name="produto")ProdutoDTO produtoDTO)throws EstoqueWebServiceException{
		
		ProdutoFacade facade = context.getBean(ProdutoFacade.class); 
		
		try{
			
			Produto produto = produtoConversor.converterTransferencia(produtoDTO);
			
			facade.alterarProduto(produto);
		}
		catch(DAOException e){
			
			dispararExcecao(e);
		}
		
	}
	
	@WebMethod
	public List<ProdutoDTO> listarProdutos()throws EstoqueWebServiceException{
		
		ProdutoDAO dao = context.getBean(ProdutoDAO.class); 
		
		try{
			
			List<Produto> produtos = dao.listarProdutos(); 
			
			List<ProdutoDTO> dtoLista = produtoConversor.converterLista(produtos); 
			
			return dtoLista; 
			
		}
		catch(DAOException e){
			
			dispararExcecao(e); 
		}
		finally{
			
			dao.fecharConexao();
		}
		
		return null; 
	}
	
	@WebMethod
	public void registrarEntradaDeProduto(@WebParam(name="produtoId")Long produtoId, 
			@WebParam(name="quantidade")Integer quantidade)	throws EstoqueWebServiceException{
		
		ProdutoFacade facade = context.getBean(ProdutoFacade.class); 
		
		try{
		
			facade.registrarEntrada(produtoId, quantidade);
		
		}
		catch(DAOException e){
			
			dispararExcecao(e);
		}
		
	}
	
	@WebMethod
	public void registrarSaidaDeProduto(@WebParam(name="produtoId")Long produtoId, 
			@WebParam(name="quantidade")Integer quantidade)throws EstoqueWebServiceException{
		
		ProdutoFacade facade = context.getBean(ProdutoFacade.class); 
		
		try{
			
			
			facade.registrarSaida(produtoId, quantidade);
			
			
		}catch(DAOException e){
			
			dispararExcecao(e); 
			
		}catch(IllegalStateException e){
			
			dispararExcecao(e); 
		}
		
	}
		
	public List<MovimentacaoDTO> listarMovimentacoesDoProdutoPorPeriodo(Long produtoId,Date dataInicial, Date dataFinal)
	throws EstoqueWebServiceException{
		
		MovimentacaoDAO dao = context.getBean(MovimentacaoDAO.class); 
		
		try{
		
			List<Movimentacao> movimentacoes = dao.listarMovimentacoesDoProdutoPorPeriodo(produtoId, dataInicial, dataFinal); 
			
			List<MovimentacaoDTO> dtoLista = movimentacaoConversor.converterLista(movimentacoes);
			
			return dtoLista; 
			
		}
		catch(DAOException e){
			
			dispararExcecao(e);
		}
		
		return null; 
	}
	
	public List<MovimentacaoDTO> listarMovimentacoesDoProduto(Long produtoId)throws EstoqueWebServiceException{
		
		MovimentacaoDAO dao = context.getBean(MovimentacaoDAO.class); 
		
		try{
			
			List<Movimentacao> movimentacoes = dao.listarMovimentacoesDoProduto(produtoId); 
			List<MovimentacaoDTO> dtoLista = movimentacaoConversor.converterLista(movimentacoes);
			
			return dtoLista; 
			
		}catch(DAOException e){
			
			dispararExcecao(e);
		}
		
		return null; 
	}
	
	public List<MovimentacaoDTO> listarMovimentacoesNoEstoque(Long estoqueId)throws EstoqueWebServiceException{
		
		MovimentacaoDAO dao = context.getBean(MovimentacaoDAO.class); 
		
		try{
			
			List<Movimentacao> movimentacoes = dao.listarMovimentacoesNoEstoque(estoqueId); 
			List<MovimentacaoDTO> dtoLista = movimentacaoConversor.converterLista(movimentacoes); 
			
			return dtoLista; 
			
		}catch(DAOException e){
			
			dispararExcecao(e); 
		}
		
		return null; 
	}
	
	public List<MovimentacaoDTO> listarMovimentacoesNoEstoquePorPeriodo(Long estoqueId, Date dataInicial, Date dataFinal)
	throws EstoqueWebServiceException{
		
		MovimentacaoDAO dao = context.getBean(MovimentacaoDAO.class); 
		
		try{
			
			List<Movimentacao> movimentacoes = dao.listarMovimentacoesNoEstoquePorPeriodo(estoqueId,dataInicial,dataFinal); 
			List<MovimentacaoDTO> dtoLista = movimentacaoConversor.converterLista(movimentacoes); 
			
			return dtoLista; 
			
		}catch(DAOException e){
			
			dispararExcecao(e); 
		}
		
		return null; 
		 
	}
	
	
	private void dispararExcecao(Exception e)throws EstoqueWebServiceException{
		
		throw new EstoqueWebServiceException(e.getMessage(),new EstoqueWebServiceFault(),e.getCause());
	}
	 
}
