package br.com.braveti.estoqueweb.facades;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import static org.junit.Assert.*; 

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.braveti.estoqueweb.dao.ConexaoBancoDeDados;
import br.com.braveti.estoqueweb.dao.DAOException;
import br.com.braveti.estoqueweb.dao.DataSource;
import br.com.braveti.estoqueweb.dao.EstoqueDAO;
import br.com.braveti.estoqueweb.dao.FabricaDeSessao;
import br.com.braveti.estoqueweb.dao.FabricaDeSessaoImpl;
import br.com.braveti.estoqueweb.dominio.Estoque;
import br.com.braveti.estoqueweb.facades.EstoqueFacade;

public class EstoqueFacadeTests {

	private EstoqueFacade facade;
	private static FabricaDeSessao fabricaDeSessao; 
	private EstoqueDAO dao; 
	
	@BeforeClass
	public static void antesDeTudo(){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml"); 
		
		ConexaoBancoDeDados conexao = context.getBean("testeConexao",ConexaoBancoDeDados.class);
		DataSource dataSource = new DataSource(conexao); 
		fabricaDeSessao = new FabricaDeSessaoImpl(dataSource);
		
	}
	
	@Before
	public void antes(){
		
		dao = new EstoqueDAO(fabricaDeSessao); 
		facade = new EstoqueFacade(dao); 
		dao.iniciarTransacao(); 
		
	}
	
	@After
	public void depois(){
		
		dao.finalizarTransacao(); 
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
