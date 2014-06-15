package com.inove.estoqueweb.facades;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*; 
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inove.estoqueweb.dao.CategoriaDAO;
import com.inove.estoqueweb.dao.ConexaoBancoDeDados;
import com.inove.estoqueweb.dao.DAOException;
import com.inove.estoqueweb.dao.FabricaDeSessao;
import com.inove.estoqueweb.dao.FabricaDeSessaoImpl;
import com.inove.estoqueweb.dominio.Categoria;

public class CategoriaFacadeTests {

	private static FabricaDeSessao fabricaDeSessao; 
	private CategoriaDAO dao; 
	private CategoriaFacade categoriaFacade; 
	
	@BeforeClass
	public static void antesDeTudo(){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml"); 
		
		ConexaoBancoDeDados conexao = context.getBean("testeConexao",ConexaoBancoDeDados.class);
		fabricaDeSessao = new FabricaDeSessaoImpl(conexao);
		
	}
	
	@Before
	public void antes(){
		
		dao = new CategoriaDAO(fabricaDeSessao);
		categoriaFacade = new CategoriaFacade(dao);
		dao.getSession().beginTransaction(); 
	}
	
	@After
	public void depois(){
		
		dao.getSession().getTransaction().rollback();
		dao.getSession().close(); 
	}
	
	@Test
	public void testDeveCadastrarCategoria()throws DAOException{
		
		Categoria categoria = gerarCategoria(); 
		
		Long id = categoriaFacade.criarCategoria(categoria); 
		
		assertNotNull(id); 
		assertEquals(categoria.getId(),id);
		
		Categoria categoriaPesquisada = dao.buscar(Categoria.class, id); 
		
		assertNotNull(categoriaPesquisada); 
	}
	
	@Test
	public void testDeveRemoverCategoria()throws DAOException{
		
		Categoria categoria = gerarCategoria(); 
		Long id = categoriaFacade.criarCategoria(categoria); 
		
		Categoria categoriaSalva = dao.buscar(Categoria.class, id);
		
		assertNotNull(categoriaSalva);
		
		categoriaFacade.removerCategoria(categoria); 
		
		Categoria categoriaPesquisada = dao.buscar(Categoria.class, id); 
		
		assertNull(categoriaPesquisada); 
		
	}
	
	@Test
	public void testDeveAlterarCategoria()throws DAOException{
		
		String novoNome = "bermuda";
		
		Categoria categoria = gerarCategoria(); 
		Long id = categoriaFacade.criarCategoria(categoria); 
		
		Categoria categoriaSalva = dao.buscar(Categoria.class, id);
		
		assertEquals(categoria.getNome(),categoriaSalva.getNome());
		
		categoriaSalva.setNome(novoNome);
		
		categoriaFacade.alterarCategoria(categoriaSalva); 
		
		Categoria categoriaAlterada = dao.buscar(Categoria.class, id);
		
		assertEquals(novoNome,categoriaAlterada.getNome()); 
		
	}
	
	private Categoria gerarCategoria(){
		
		return gerarCategoria("camisas","camisas da loja"); 
	}
	
	private Categoria gerarCategoria(String nome, String descricao){
		
		Categoria categoria = new Categoria(); 
		categoria.setNome(nome);
		categoria.setDescricao(descricao);
		return categoria; 
	}
}
