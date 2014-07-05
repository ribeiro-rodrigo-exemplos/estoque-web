package com.inove.estoqueweb.facades;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inove.estoqueweb.dao.CategoriaDAO;
import com.inove.estoqueweb.dao.ConexaoBancoDeDados;
import com.inove.estoqueweb.dao.DataSource;
import com.inove.estoqueweb.dao.EstoqueDAO;
import com.inove.estoqueweb.dao.FabricaDeSessao;
import com.inove.estoqueweb.dao.FabricaDeSessaoImpl;
import com.inove.estoqueweb.dao.FornecedorDAO;
import com.inove.estoqueweb.dao.MovimentacaoDAO;
import com.inove.estoqueweb.dao.ProdutoDAO;
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
