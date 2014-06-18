package com.inove.estoqueweb.facades;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inove.estoqueweb.dao.CategoriaDAO;
import com.inove.estoqueweb.dao.ConexaoBancoDeDados;
import com.inove.estoqueweb.dao.EstoqueDAO;
import com.inove.estoqueweb.dao.FabricaDeSessao;
import com.inove.estoqueweb.dao.FabricaDeSessaoImpl;
import com.inove.estoqueweb.dao.FornecedorDAO;
import com.inove.estoqueweb.dao.ProdutoDAO;
import com.inove.estoqueweb.testdatabuilders.ProdutoDataBuilder;

public class ProdutoFacadeTests {

	private static FabricaDeSessao fabricaDeSessao; 
	private ProdutoDAO produtoDAO; 
	private CategoriaDAO categoriaDAO; 
	private EstoqueDAO estoqueDAO; 
	private FornecedorDAO fornecedorDAO; 
	private ProdutoFacade facade; 
	private ProdutoDataBuilder produtoBuilder; 
	private static ApplicationContext context; 
	
	@BeforeClass
	public static void antesDeTudo(){
		
		context = new ClassPathXmlApplicationContext("spring-context.xml"); 
		
		ConexaoBancoDeDados conexao = context.getBean("testeConexao",ConexaoBancoDeDados.class);
		fabricaDeSessao = new FabricaDeSessaoImpl(conexao);
	}
	
	@Before
	public void antes(){
		
		produtoDAO = new ProdutoDAO(fabricaDeSessao); 
		categoriaDAO = new CategoriaDAO(fabricaDeSessao); 
		estoqueDAO = new EstoqueDAO(fabricaDeSessao); 
		fornecedorDAO = new FornecedorDAO(fabricaDeSessao);
		facade = new ProdutoFacade(produtoDAO,categoriaDAO,estoqueDAO,fornecedorDAO); 
		produtoDAO.getSession().beginTransaction(); 
		categoriaDAO.getSession().beginTransaction();
		estoqueDAO.getSession().beginTransaction();
		fornecedorDAO.getSession().beginTransaction();
		produtoBuilder = new ProdutoDataBuilder(); 
	}
	
	@After
	public void depois(){
		
		produtoDAO.getSession().getTransaction().rollback();
		categoriaDAO.getSession().getTransaction().rollback();
		estoqueDAO.getSession().getTransaction().rollback();
		fornecedorDAO.getSession().getTransaction().rollback();
		produtoDAO.getSession().close(); 
		categoriaDAO.getSession().close(); 
		estoqueDAO.getSession().close(); 
		fornecedorDAO.getSession().close(); 
		
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
