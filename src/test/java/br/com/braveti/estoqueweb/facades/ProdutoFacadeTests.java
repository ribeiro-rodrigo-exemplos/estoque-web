package br.com.braveti.estoqueweb.facades;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.braveti.estoqueweb.dao.CategoriaDAO;
import br.com.braveti.estoqueweb.dao.ConexaoBancoDeDados;
import br.com.braveti.estoqueweb.dao.DataSource;
import br.com.braveti.estoqueweb.dao.EstoqueDAO;
import br.com.braveti.estoqueweb.dao.FabricaDeSessao;
import br.com.braveti.estoqueweb.dao.FabricaDeSessaoImpl;
import br.com.braveti.estoqueweb.dao.FornecedorDAO;
import br.com.braveti.estoqueweb.dao.MovimentacaoDAO;
import br.com.braveti.estoqueweb.dao.ProdutoDAO;
import br.com.braveti.estoqueweb.facades.ProdutoFacade;

import com.inove.estoqueweb.testdatabuilders.ProdutoDataBuilder;

public class ProdutoFacadeTests {

	private static FabricaDeSessao fabricaDeSessao; 
	private ProdutoDAO produtoDAO; 
	private CategoriaDAO categoriaDAO; 
	private EstoqueDAO estoqueDAO; 
	private FornecedorDAO fornecedorDAO; 
	private MovimentacaoDAO movimentacaoDAO; 
	private ProdutoFacade facade; 
	private ProdutoDataBuilder produtoBuilder; 
	private static ApplicationContext context; 
	
	@BeforeClass
	public static void antesDeTudo(){
		
		context = new ClassPathXmlApplicationContext("spring-context.xml"); 
		
		ConexaoBancoDeDados conexao = context.getBean("testeConexao",ConexaoBancoDeDados.class);
		DataSource dataSource = new DataSource(conexao); 
		fabricaDeSessao = new FabricaDeSessaoImpl(dataSource);
	}
	
	@Before
	public void antes(){
		
		produtoDAO = new ProdutoDAO(fabricaDeSessao); 
		categoriaDAO = new CategoriaDAO(fabricaDeSessao); 
		estoqueDAO = new EstoqueDAO(fabricaDeSessao); 
		fornecedorDAO = new FornecedorDAO(fabricaDeSessao);
		movimentacaoDAO = new MovimentacaoDAO(fabricaDeSessao); 
		facade = new ProdutoFacade(produtoDAO,categoriaDAO,estoqueDAO,fornecedorDAO,movimentacaoDAO); 
		produtoDAO.iniciarTransacao(); 
		categoriaDAO.iniciarTransacao();
		estoqueDAO.iniciarTransacao();
		fornecedorDAO.iniciarTransacao();
		produtoBuilder = new ProdutoDataBuilder(); 
	}
	
	@After
	public void depois(){
		
		produtoDAO.finalizarTransacao();
		categoriaDAO.finalizarTransacao();
		estoqueDAO.finalizarTransacao();
		fornecedorDAO.finalizarTransacao();
		
	}
	
	@Test
	public void deveSalvarProduto(){
		
		
		
	}
	
	@Test
	public void deveRemoverProduto(){
		
		
	}
	
	@Test
	public void deveAlterarProduto(){
		
		
	}
		

}
