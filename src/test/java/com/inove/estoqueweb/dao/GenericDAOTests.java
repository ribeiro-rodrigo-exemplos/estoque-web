package com.inove.estoqueweb.dao;

import org.junit.*; 
import static org.junit.Assert.*; 
import org.hibernate.*; 
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import com.inove.estoqueweb.dominio.*;

public class GenericDAOTests {

	private GenericoDAO<Categoria> dao; 
	private static FabricaDeSessao fabrica; 
	
	@BeforeClass
	public static void antesDeTodos(){
		
		ApplicationContext appContext = new ClassPathXmlApplicationContext("spring-context.xml");
		
		ConexaoBancoDeDados conexao = appContext.getBean("testeConexao",ConexaoBancoDeDados.class);
		
		fabrica = new FabricaDeSessaoImpl(conexao);
				
	}
	
	@Before
	public void antes(){
		
		dao = new GenericoDAO<Categoria>(fabrica); 
		dao.getSession().beginTransaction(); 
	}
	
	@After
	public void depois(){
		
		dao.getSession().getTransaction().rollback();
		dao.getSession().close();
	}
	
	@Test
	public void deveSalvarObjetos()throws DAOException{
				
		Categoria categoria = new Categoria();
		categoria.setNome("Video Game");
		categoria.setDescricao("Categoria de video games"); 
		
		dao.salvar(categoria);
		
		dao.getSession().flush();
		
		Categoria categoriaSalva = (Categoria) dao.getSession().get(Categoria.class,categoria.getId()); 
		
		assertNotNull(categoriaSalva);
		assertEquals(categoria.getId(),categoriaSalva.getId()); 
		
	}
	
	@Test
	public void deveBuscarObjetos()throws DAOException{
		
		Categoria categoria = new Categoria(); 
		categoria.setNome("Arroz");
		
		dao.getSession().persist(categoria);
		
		dao.getSession().flush();
		
		Categoria categoriaSalva = dao.buscar(Categoria.class,categoria.getId()); 
		
		assertNotNull(categoriaSalva);
		assertEquals(categoria.getId(),categoriaSalva.getId()); 
		
	}
	
	@Test
	public void deveAlterarObjetos()throws DAOException{
		
		Categoria categoria = new Categoria(); 
		categoria.setNome("Blusa");
		
		dao.getSession().persist(categoria);
		
		Categoria categoriaSalva = (Categoria) dao.getSession().get(Categoria.class,categoria.getId()); 
		
		assertEquals(categoria.getNome(),categoriaSalva.getNome()); 
		
		categoria.setNome("Camisa");
		
		dao.alterar(categoria);
		
		Categoria categoriaAlterada = (Categoria) dao.getSession().get(Categoria.class,categoria.getId()); 
		
		assertEquals(categoria.getNome(),categoriaAlterada.getNome()); 
	}
	
	@Test
	public void deveRemoverObjetos()throws DAOException{
		
		Categoria categoria = new Categoria(); 
		categoria.setNome("Camisa"); 
		
		dao.getSession().persist(categoria);
		
		assertNotNull(dao.getSession().get(Categoria.class,categoria.getId())); 
				
		dao.remover(categoria);
		
		assertNull(dao.getSession().get(Categoria.class,categoria.getId())); 
		
	}
	
	@Test(expected=DAOException.class)
	public void deveDispararErroDeValidacao()throws DAOException{
		
		Categoria categoria = new Categoria(); 
		categoria.setDescricao("Categoria de camisas"); 
		
		dao.salvar(categoria); 
	}
}
