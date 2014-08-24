package br.com.braveti.estoqueweb.facades;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*; 

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.braveti.estoqueweb.dao.ConexaoBancoDeDados;
import br.com.braveti.estoqueweb.dao.DAOException;
import br.com.braveti.estoqueweb.dao.DataSource;
import br.com.braveti.estoqueweb.dao.FabricaDeSessao;
import br.com.braveti.estoqueweb.dao.FabricaDeSessaoImpl;
import br.com.braveti.estoqueweb.dao.FornecedorDAO;
import br.com.braveti.estoqueweb.dominio.Fornecedor;
import br.com.braveti.estoqueweb.facades.FornecedorFacade;

public class FornecedorFacadeTests {

	private FornecedorFacade fornecedorFacade; 
	private static FabricaDeSessao fabricaDeSessao; 
	private FornecedorDAO dao; 
	
	@BeforeClass
	public static void antesDeTodos(){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml"); 
		ConexaoBancoDeDados conexao = context.getBean("testeConexao",ConexaoBancoDeDados.class);
		DataSource dataSource = new DataSource(conexao); 
		fabricaDeSessao = new FabricaDeSessaoImpl(dataSource);
	}
	
	@Before
	public void antes(){
		
		dao = new FornecedorDAO(fabricaDeSessao); 
		fornecedorFacade = new FornecedorFacade(dao);
		dao.iniciarTransacao();
		
	}
	
	@After
	public void depois(){

        try{

		dao.finalizarTransacao();

        }catch(DAOException e){

            e.printStackTrace();
        }
	}
	
	@Test
	public void testDeveCriarFornecedor()throws DAOException{
		
		Fornecedor fornecedor = gerarFornecedor();
		
		Long id = fornecedorFacade.criarFornecedor(fornecedor); 
		
		Fornecedor fornecedorPesquisado = dao.buscar(Fornecedor.class, id); 
		
		assertEquals(fornecedor.getId(),fornecedorPesquisado.getId());
	}

	
	@Test
	public void testDeveRemoverFornecedor()throws DAOException{
		
		Fornecedor fornecedor = gerarFornecedor(); 
		Long id = fornecedorFacade.criarFornecedor(fornecedor); 
		
		assertNotNull(id); 
		
		fornecedorFacade.removerFornecedor(fornecedor);
		
		Fornecedor fornecedorPesquisado = dao.buscar(Fornecedor.class, id); 
		
		assertNull(fornecedorPesquisado); 
		
	}
	
	@Test
	public void testDeveAlterarFornecedor()throws DAOException{
		
		String novoEmail = "rodrigo@inove.com"; 
		
		Fornecedor fornecedor = gerarFornecedor(); 
		
		Long id = fornecedorFacade.criarFornecedor(fornecedor); 
		
		assertNotNull(id);
		
		fornecedor.setEmail(novoEmail);
		
		fornecedorFacade.alterarFornecedor(fornecedor);
		
		Fornecedor fornecedorAlterado = dao.buscar(Fornecedor.class, id); 
		
		assertEquals(novoEmail,fornecedorAlterado.getEmail()); 
	}
	
	private Fornecedor gerarFornecedor(){
		
		return gerarFornecedor("123","1234","fornecedor@inove.com","bla bla","inove","123"); 
	}
	
	private Fornecedor gerarFornecedor(String cnpj,String cpf, String email, String endereco, String razao,
			String telefone) {
		
		Fornecedor fornecedor = new Fornecedor(razao);
		fornecedor.setCnpj(cnpj);
		fornecedor.setCpf(cpf);
		fornecedor.setEmail(email);
		fornecedor.setEndereco(endereco);
		fornecedor.setTelefone(telefone);
		
		return fornecedor;
	}
	

}
