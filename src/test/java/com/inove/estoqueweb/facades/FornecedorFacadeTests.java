package com.inove.estoqueweb.facades;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*; 
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.inove.estoqueweb.dao.ConexaoBancoDeDados;
import com.inove.estoqueweb.dao.DAOException;
import com.inove.estoqueweb.dao.FabricaDeSessao;
import com.inove.estoqueweb.dao.FabricaDeSessaoImpl;
import com.inove.estoqueweb.dao.FornecedorDAO;
import com.inove.estoqueweb.dominio.Fornecedor;

public class FornecedorFacadeTests {

	private FornecedorFacade fornecedorFacade; 
	private static FabricaDeSessao fabricaDeSessao; 
	private FornecedorDAO dao; 
	
	@BeforeClass
	public static void antesDeTodos(){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml"); 
		ConexaoBancoDeDados conexao = context.getBean("testeConexao",ConexaoBancoDeDados.class);
		fabricaDeSessao = new FabricaDeSessaoImpl(conexao);
	}
	
	@Before
	public void antes(){
		
		dao = new FornecedorDAO(fabricaDeSessao); 
		fornecedorFacade = new FornecedorFacade(dao);
		dao.getSession().beginTransaction(); 
		
	}
	
	@After
	public void depois(){
		
		dao.getSession().getTransaction().rollback();
		dao.getSession().close(); 
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
		
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setCnpj(cnpj);
		fornecedor.setCpf(cpf);
		fornecedor.setEmail(email);
		fornecedor.setEndereco(endereco);
		fornecedor.setRazaoSocial(razao);
		fornecedor.setTelefone(telefone);
		
		return fornecedor;
	}
	

}
