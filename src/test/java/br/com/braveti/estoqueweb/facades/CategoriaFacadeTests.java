package br.com.braveti.estoqueweb.facades;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*; 

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.braveti.estoqueweb.dao.CategoriaDAO;
import br.com.braveti.estoqueweb.dao.ConexaoBancoDeDados;
import br.com.braveti.estoqueweb.dao.DAOException;
import br.com.braveti.estoqueweb.dao.DataSource;
import br.com.braveti.estoqueweb.dao.FabricaDeSessao;
import br.com.braveti.estoqueweb.dao.FabricaDeSessaoImpl;
import br.com.braveti.estoqueweb.dominio.Categoria;
import br.com.braveti.estoqueweb.facades.CategoriaFacade;

public class CategoriaFacadeTests {

	private static FabricaDeSessao fabricaDeSessao; 
	private CategoriaDAO dao; 
	private CategoriaFacade categoriaFacade; 
	
	@BeforeClass
	public static void antesDeTudo(){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml"); 
		
		ConexaoBancoDeDados conexao = context.getBean("testeConexao",ConexaoBancoDeDados.class);
		DataSource dataSource = new DataSource(conexao); 
		fabricaDeSessao = new FabricaDeSessaoImpl(dataSource);
		
	}
	
	@Before
	public void antes(){
		
		dao = new CategoriaDAO(fabricaDeSessao);
		categoriaFacade = new CategoriaFacade(dao);
		dao.iniciarTransacao();
	}
	
	@After
	public void depois(){

        try{

		    dao.finalizarTransacao();

        }
        catch(DAOException e){

            e.printStackTrace();
        }
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
		
		Categoria categoria = new Categoria(nome); 
		categoria.setDescricao(descricao);
		return categoria; 
	}
}
