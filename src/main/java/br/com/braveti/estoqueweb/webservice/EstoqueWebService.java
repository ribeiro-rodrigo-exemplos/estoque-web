package br.com.braveti.estoqueweb.webservice;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.braveti.estoqueweb.dao.CategoriaDAO;
import br.com.braveti.estoqueweb.dao.DAOException;
import br.com.braveti.estoqueweb.dao.EstoqueDAO;
import br.com.braveti.estoqueweb.dao.FornecedorDAO;
import br.com.braveti.estoqueweb.dao.MovimentacaoDAO;
import br.com.braveti.estoqueweb.dao.ProdutoDAO;
import br.com.braveti.estoqueweb.dominio.Categoria;
import br.com.braveti.estoqueweb.dominio.Estoque;
import br.com.braveti.estoqueweb.dominio.Fornecedor;
import br.com.braveti.estoqueweb.dominio.Movimentacao;
import br.com.braveti.estoqueweb.dominio.Produto;
import br.com.braveti.estoqueweb.dto.CategoriaDTO;
import br.com.braveti.estoqueweb.dto.CategoriaDTOConversor;
import br.com.braveti.estoqueweb.dto.DTOConversor;
import br.com.braveti.estoqueweb.dto.EstoqueDTO;
import br.com.braveti.estoqueweb.dto.EstoqueDTOConversor;
import br.com.braveti.estoqueweb.dto.FornecedorDTO;
import br.com.braveti.estoqueweb.dto.FornecedorDTOConversor;
import br.com.braveti.estoqueweb.dto.MovimentacaoDTO;
import br.com.braveti.estoqueweb.dto.MovimentacaoDTOConversor;
import br.com.braveti.estoqueweb.dto.ProdutoDTO;
import br.com.braveti.estoqueweb.dto.ProdutoDTOConversor;
import br.com.braveti.estoqueweb.facades.CategoriaFacade;
import br.com.braveti.estoqueweb.facades.EstoqueFacade;
import br.com.braveti.estoqueweb.facades.FornecedorFacade;
import br.com.braveti.estoqueweb.facades.ProdutoFacade;


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
	
	@WebMethod(operationName="criarCategoria")
	@WebResult(name="idCategoria")
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
	
	@WebMethod(operationName="removerCategoria")
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
	
	@WebMethod(operationName="buscarCategoria")
	@WebResult(name="categoria")
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
	
	@WebMethod(operationName="alterarCategoria")
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
	
	@WebMethod(operationName="listarCategorias")
	@WebResult(name="categorias")
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
	
	@WebMethod(operationName="criarEstoque")
	@WebResult(name="estoqueId")
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
	
	@WebMethod(operationName="removerEstoque")
	public void removerEstoque(@WebParam(name="id")Long idEstoque)throws EstoqueWebServiceException{
		
		 EstoqueFacade estoqueFacade = context.getBean(EstoqueFacade.class); 
		 
		 try{
			 
			 estoqueFacade.removerEstoque(idEstoque);
		 
		 }
		 catch(DAOException e){
			 
			 dispararExcecao(e); 
		 }
	}
	
	@WebMethod(operationName="alterarEstoque")
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
	
	@WebMethod(operationName="buscarEstoque")
	@WebResult(name="estoque")
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
	
	@WebMethod(operationName="listarEstoques")
	@WebResult(name="estoques")
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
	
	@WebMethod(operationName="criarFornecedor")
	@WebResult(name="fornecedorId")
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
	
	@WebMethod(operationName="removerFornecedor")
	public void removerFornecedor(@WebParam(name="id")Long idFornecedor)throws EstoqueWebServiceException{
		
		FornecedorFacade fornecedorFacade = context.getBean(FornecedorFacade.class); 
		
		try{
		
			fornecedorFacade.removerFornecedor(idFornecedor);
		
		}
		catch(DAOException e){
			
			dispararExcecao(e);
		}
		 
	}
	
	@WebMethod(operationName="alterarFornecedor")
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
	
	@WebMethod(operationName="buscarFornecedor")
	@WebResult(name="fornecedor")
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
	
	@WebMethod(operationName="listarFornecedores")
	@WebResult(name="fornecedores")
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
	
	@WebMethod(operationName="criarProduto")
	@WebResult(name="fornecedorId")
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
	
	@WebMethod(operationName="removerProduto")
	public void removerProduto(@WebParam(name="id")Long produtoId)throws EstoqueWebServiceException{
		
		
		ProdutoFacade facade = context.getBean(ProdutoFacade.class); 
		
		try{
			
			facade.removerProduto(produtoId);
			
		}
		catch(DAOException e){
			
			dispararExcecao(e);
		}
		

		
	}
	
	@WebMethod(operationName="buscarProduto")
	@WebResult(name="produto")
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
	
	@WebMethod(operationName="alterarProduto")
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
	
	@WebMethod(operationName="listarProdutos")
	@WebResult(name="produtos")
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
	
	@WebMethod(operationName="registarEntadaDeProduto")
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
	
	@WebMethod(operationName="registarSaidaDeProduto")
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
	
	@WebMethod(operationName="listarMovimentacoesDoProdutoPorPeriodo")
	@WebResult(name="movimentacoes")
	public List<MovimentacaoDTO> listarMovimentacoesDoProdutoPorPeriodo(@WebParam(name="produtoId")Long produtoId,
			@WebParam(name="dataHoraInicial")Date dataInicial, @WebParam(name="dataHoraFinal")Date dataFinal)
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
	
	@WebMethod(operationName="listarMovimentacoesDoProduto")
	@WebResult(name="movimentacoes")
	public List<MovimentacaoDTO> listarMovimentacoesDoProduto(@WebParam(name="id")Long produtoId)throws EstoqueWebServiceException{
		
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
	
	@WebMethod(operationName="listarMovimentacoesNoEstoque")
	@WebResult(name="movimentacoes")
	public List<MovimentacaoDTO> listarMovimentacoesNoEstoque(@WebParam(name="id")Long estoqueId)throws EstoqueWebServiceException{
		
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
	
	@WebMethod(operationName="listarMovimentacoesNoEstoquePorPeriodo")
	@WebResult(name="movimentacoes")
	public List<MovimentacaoDTO> listarMovimentacoesNoEstoquePorPeriodo(@WebParam(name="estoqueId")Long estoqueId, 
			@WebParam(name="dataHoraInicial")Date dataInicial,@WebParam(name="dataHoraFinal")Date dataFinal)throws EstoqueWebServiceException{
		
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
