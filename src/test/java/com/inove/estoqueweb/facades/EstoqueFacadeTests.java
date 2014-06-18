package com.inove.estoqueweb.facades;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*; 
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inove.estoqueweb.dao.ConexaoBancoDeDados;
import com.inove.estoqueweb.dao.DAOException;
import com.inove.estoqueweb.dao.EstoqueDAO;
import com.inove.estoqueweb.dao.FabricaDeSessao;
import com.inove.estoqueweb.dao.FabricaDeSessaoImpl;
import com.inove.estoqueweb.dominio.Estoque;

public class EstoqueFacadeTests {

	private EstoqueFacade facade;
	private static FabricaDeSessao fabricaDeSessao; 
	private EstoqueDAO dao; 
	
	@BeforeClass
	public static void antesDeTudo(){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml"); 
		
		ConexaoBancoDeDados conexao = context.getBean("testeConexao",ConexaoBancoDeDados.class);
		fabricaDeSessao = new FabricaDeSessaoImpl(conexao);
		
	}
	
	@Before
	public void antes(){
		
		dao = new EstoqueDAO(fabricaDeSessao); 
		facade = new EstoqueFacade(dao); 
		dao.getSession().beginTransaction(); 
		
	}
	
	@After
	public void depois(){
		
		dao.getSession().getTransaction().rollback(); 
		dao.getSession().close(); 
	}
	
	@Test
	public void testDeveCadastrarEstoque()throws DAOException{
		
		Estoque estoque = gerarEstoque(); 
		
		Long id = facade.criarEstoque(estoque); 
		
		assertNotNull(id); 
		assertEquals(estoque.getId(),id); 
		 
		Estoque estoquePesquisado = dao.buscar(Estoque.class, id); 
		
		assertNotNull(estoquePesquisado); 
	}
	
	@Test
	public void testDeveRemoverEstoque()throws DAOException{
		
		Estoque estoque = gerarEstoque(); 
		
		Long id = facade.criarEstoque(estoque); 
		
		Estoque estoquePesquisado = dao.buscar(Estoque.class, id); 
		
		assertNotNull(estoquePesquisado); 
		
		facade.removerEstoque(estoquePesquisado);
		
		Estoque estoqueRemovido = dao.buscar(Estoque.class, id); 
		
		assertNull(estoqueRemovido); 
	}
	
	@Test
	public void testDeveAlterarEstoque()throws DAOException{
		
		String novoNome = "estoque-leitores"; 
		
		Estoque estoque = gerarEstoque(); 
		
		Long id = facade.criarEstoque(estoque); 
		
		Estoque estoquePesquisado = dao.buscar(Estoque.class,id); 
		
		assertNotNull(estoquePesquisado); 
		
		estoquePesquisado.setNome(novoNome); 
		
		facade.alterarEstoque(estoquePesquisado);
		
		Estoque estoqueAlterado = dao.buscar(Estoque.class, id); 
		
		assertEquals(novoNome,estoqueAlterado.getNome()); 
		
	}
	
	private Estoque gerarEstoque(){
		
		return gerarEstoque("roupas-estoque","estoque de roupas da loja"); 
	}
	
	private Estoque gerarEstoque(String nome, String descricao){
		
		Estoque estoque = new Estoque(nome); 
		estoque.setDescricao(descricao); 
		
		return estoque; 
	}
	
}
