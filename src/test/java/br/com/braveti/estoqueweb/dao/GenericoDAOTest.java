package br.com.braveti.estoqueweb.dao;

import org.junit.*; 

import static org.junit.Assert.*; 

import org.hibernate.*; 
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import br.com.braveti.estoqueweb.dao.ConexaoBancoDeDados;
import br.com.braveti.estoqueweb.dao.DAOException;
import br.com.braveti.estoqueweb.dao.DataSource;
import br.com.braveti.estoqueweb.dao.FabricaDeSessao;
import br.com.braveti.estoqueweb.dao.FabricaDeSessaoImpl;
import br.com.braveti.estoqueweb.dao.GenericoDAO;
import br.com.braveti.estoqueweb.dominio.*;

public class GenericoDAOTest {

	private GenericoDAO<Categoria> dao; 
	private static FabricaDeSessao fabrica; 
	
	@BeforeClass
	public static void antesDeTodos(){
		
		ApplicationContext appContext = new ClassPathXmlApplicationContext("spring-context.xml");
		
		ConexaoBancoDeDados conexao = appContext.getBean("testeConexao",ConexaoBancoDeDados.class);
		DataSource dataSource = new DataSource(conexao); 
		fabrica = new FabricaDeSessaoImpl(dataSource);
				
	}
	
	@Before
	public void antes(){
		
		dao = new GenericoDAO<Categoria>(fabrica); 
		dao.iniciarTransacao();
	}
	
	@After
	public void depois(){
		
		dao.reverterTransacao();
	}
	
	@Test
	public void deveSalvarObjetos()throws DAOException{
				
		Categoria categoria = new Categoria();
		categoria.setNome("Video Game");
		categoria.setDescricao("Categoria de video games"); 
		
		dao.salvar(categoria);
		
		Categoria categoriaSalva = (Categoria) dao.buscar(Categoria.class,categoria.getId()); 
		
		assertNotNull(categoriaSalva);
		assertEquals(categoria.getId(),categoriaSalva.getId()); 
		
	}
	
	@Test
	public void deveBuscarObjetos()throws DAOException{
		
		Categoria categoria = new Categoria(); 
		categoria.setNome("Arroz");
		
		dao.salvar(categoria);
				
		Categoria categoriaSalva = dao.buscar(Categoria.class,categoria.getId()); 
		
		assertNotNull(categoriaSalva);
		assertEquals(categoria.getId(),categoriaSalva.getId()); 
		
	}
	
	@Test
	public void deveAlterarObjetos()throws DAOException{
		
		Categoria categoria = new Categoria(); 
		categoria.setNome("Blusa");
		
		dao.salvar(categoria);
		
		Categoria categoriaSalva = (Categoria) dao.buscar(Categoria.class,categoria.getId()); 
		
		assertEquals(categoria.getNome(),categoriaSalva.getNome()); 
		
		categoria.setNome("Camisa");
		
		dao.alterar(categoria);
		
		Categoria categoriaAlterada = (Categoria) dao.buscar(Categoria.class,categoria.getId()); 
		
		assertEquals(categoria.getNome(),categoriaAlterada.getNome()); 
	}
	
	@Test
	public void deveRemoverObjetos()throws DAOException{
		
		Categoria categoria = new Categoria(); 
		categoria.setNome("Camisa"); 
		
		dao.salvar(categoria);
		
		assertNotNull(dao.buscar(Categoria.class,categoria.getId())); 
				
		dao.remover(categoria);
		
		assertNull(dao.buscar(Categoria.class,categoria.getId())); 
		
	}
	
	@Test(expected=DAOException.class)
	public void deveDispararErroDeValidacao()throws DAOException{
		
		Categoria categoria = new Categoria(); 
		categoria.setDescricao("Categoria de camisas"); 
		
		dao.salvar(categoria); 
	}
}
