package com.inove.estoqueweb.dao;

import org.junit.*; 
import static org.junit.Assert.*; 
import org.hibernate.*; 

import com.inove.estoqueweb.dominio.*;

public class GenericDAOTests {

	private Session session; 
	
	@BeforeClass
	public static void antesDeTodos(){
		
		FabricaDeSessao.utilizarBancoDeDados(FabricaDeSessao.DBConfig.HSQLDB);
		
	}
	
	@Before
	public void antes(){
		
		session = FabricaDeSessao.getSession(); 
		session.beginTransaction(); 
	}
	
	@After
	public void depois(){
		
		session.getTransaction().rollback();
		session.close();
	}
	
	@Test
	public void deveSalvarObjetos(){
				
		GenericoDAO<Categoria> categoriaDAO = new GenericoDAO<Categoria>(session);
		
		Categoria categoria = new Categoria();
		categoria.setNome("Video Game");
		categoria.setDescricao("Categoria de video games"); 
		
		categoriaDAO.salvar(categoria);
		
		session.flush();
		
		Categoria categoriaSalva = (Categoria) session.get(Categoria.class,categoria.getId()); 
		
		assertNotNull(categoriaSalva);
		assertEquals(categoria.getId(),categoriaSalva.getId()); 
		
	}
	
	@Test
	public void deveBuscarObjetos(){
		
		Categoria categoria = new Categoria(); 
		categoria.setNome("Arroz");
		
		session.persist(categoria);
		
		session.flush();
		
		GenericoDAO<Categoria> dao = new GenericoDAO<Categoria>(session); 
		
		Categoria categoriaSalva = dao.buscar(Categoria.class,categoria.getId()); 
		
		assertNotNull(categoriaSalva);
		assertEquals(categoria.getId(),categoriaSalva.getId()); 
		
	}
	
	@Test
	public void deveAlterarObjetos(){
		
		GenericoDAO<Categoria> dao = new GenericoDAO<Categoria>(session); 
		
		Categoria categoria = new Categoria(); 
		categoria.setNome("Blusa");
		
		session.persist(categoria);
		
		Categoria categoriaSalva = (Categoria) session.get(Categoria.class,categoria.getId()); 
		
		assertEquals(categoria.getNome(),categoriaSalva.getNome()); 
		
		categoria.setNome("Camisa");
		
		dao.alterar(categoria);
		
		Categoria categoriaAlterada = (Categoria) session.get(Categoria.class,categoria.getId()); 
		
		assertEquals(categoria.getNome(),categoriaAlterada.getNome()); 
	}
	
	@Test
	public void deveRemoverObjetos(){
		
		Categoria categoria = new Categoria(); 
		categoria.setNome("Camisa"); 
		
		session.persist(categoria);
		
		assertNotNull(session.get(Categoria.class,categoria.getId())); 
		
		GenericoDAO<Categoria> dao = new GenericoDAO<Categoria>(session); 
		
		dao.remover(categoria);
		
		assertNull(session.get(Categoria.class,categoria.getId())); 
		
	}
}
