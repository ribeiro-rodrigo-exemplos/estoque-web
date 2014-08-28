package br.com.braveti.estoqueweb.facades;

import org.junit.Before;;
import org.junit.Test;

import static org.junit.Assert.*;

import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;
import  org.mockito.Mock;

import br.com.braveti.estoqueweb.dao.DAOException;
import br.com.braveti.estoqueweb.dao.FornecedorDAO;
import br.com.braveti.estoqueweb.dominio.Fornecedor;

public class FornecedorFacadeTests {

	private FornecedorFacade fornecedorFacade; 
    @Mock
	private FornecedorDAO dao; 

	@Before
	public void antes(){
		
		MockitoAnnotations.initMocks(this);
		fornecedorFacade = new FornecedorFacade(dao);

	}
	

	@Test
	public void deveCriarFornecedor()throws DAOException{
		
		Fornecedor fornecedor = gerarFornecedor();

        doAnswer(new Answer<Void>(){

            public Void answer(InvocationOnMock invocationOnMock){

                Fornecedor fornecedor = (Fornecedor) invocationOnMock.getArguments()[0];

                fornecedor.setId(1L);

                return null;
            }

        }).when(dao).salvar(fornecedor);
		
		Long id = fornecedorFacade.criarFornecedor(fornecedor);

        InOrder ordem = inOrder(dao);

        ordem.verify(dao).iniciarTransacao();
        ordem.verify(dao).salvar(fornecedor);
        ordem.verify(dao).finalizarTransacaoEFecharConexao();
		
		assertEquals(fornecedor.getId(), id);
	}

    @Test(expected=DAOException.class)
    public void testDisparoDeExcecaoNaCriacaoDoFornecedor()throws DAOException{

        Fornecedor fornecedor = gerarFornecedor();

        doThrow(DAOException.class).when(dao).salvar(fornecedor);

        fornecedorFacade.criarFornecedor(fornecedor);


    }

	
	@Test
	public void deveRemoverFornecedor()throws DAOException{
		
		Fornecedor fornecedor = gerarFornecedor();
        fornecedor.setId(1L);

        Fornecedor fornecedorPesquisado = gerarFornecedor();

        when(dao.buscar(Fornecedor.class,fornecedor.getId())).thenReturn(fornecedorPesquisado);

        fornecedorFacade.removerFornecedor(fornecedor);

        InOrder ordem = inOrder(dao);

        ordem.verify(dao).buscar(Fornecedor.class,fornecedor.getId());
        ordem.verify(dao).iniciarTransacao();
        ordem.verify(dao).remover(fornecedorPesquisado);
        ordem.verify(dao).finalizarTransacao();
        ordem.verify(dao).fecharConexao();
		
	}

    @Test(expected=DAOException.class)
    public void testDisparoDeExcecaoNaRemocaoDoFornecedor()throws DAOException{

        Fornecedor fornecedorRemovido = gerarFornecedor();
        fornecedorRemovido.setId(1L);
        Fornecedor fornecedorPesquisado = gerarFornecedor();

        when(dao.buscar(Fornecedor.class,fornecedorRemovido.getId())).thenReturn(fornecedorPesquisado);
        doThrow(DAOException.class).when(dao).finalizarTransacao();

        fornecedorFacade.removerFornecedor(fornecedorRemovido);

    }

    @Test
    public void deveTratarCasoDeRemocaoDeFornecedorInexistente()throws DAOException{

        Fornecedor fornecedor = gerarFornecedor();

        when(dao.buscar(Fornecedor.class,1L)).thenReturn(null);

        fornecedorFacade.removerFornecedor(fornecedor);

        verify(dao, never()).iniciarTransacao();
        verify(dao,never()).remover(any(Fornecedor.class));
        verify(dao,never()).finalizarTransacao();
        verify(dao).fecharConexao();

    }
	
	@Test
	public void deveAlterarFornecedor()throws DAOException{

        Fornecedor fornecedorPesquisado = mock(Fornecedor.class);
        Fornecedor fornecedorAlterado = gerarFornecedor();
        fornecedorAlterado.setId(1L);

        when(dao.buscar(Fornecedor.class,fornecedorAlterado.getId())).thenReturn(fornecedorPesquisado);

        fornecedorFacade.alterarFornecedor(fornecedorAlterado);

        ArgumentCaptor<String> cnpj = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> cpf = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> email = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> endereco = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> razaoSocial = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> telefone = ArgumentCaptor.forClass(String.class);

        verify(fornecedorPesquisado).setCnpj(cnpj.capture());
        verify(fornecedorPesquisado).setCpf(cpf.capture());
        verify(fornecedorPesquisado).setEmail(email.capture());
        verify(fornecedorPesquisado).setEndereco(endereco.capture());
        verify(fornecedorPesquisado).setRazaoSocial(razaoSocial.capture());
        verify(fornecedorPesquisado).setTelefone(telefone.capture());

        InOrder ordem = inOrder(dao);

        ordem.verify(dao).iniciarTransacao();
        ordem.verify(dao).alterar(fornecedorPesquisado);
        ordem.verify(dao).finalizarTransacao();
        ordem.verify(dao).fecharConexao();


	}

    @Test(expected=DAOException.class)
    public void testDisparoDeExcecaoNaAlteracaoDoFornecedor()throws DAOException{

        Fornecedor fornecedorAlterado = gerarFornecedor();
        fornecedorAlterado.setId(1L);

        Fornecedor fornecedorPesquisado = gerarFornecedor();

        when(dao.buscar(Fornecedor.class,fornecedorAlterado.getId())).thenReturn(fornecedorPesquisado);

        doThrow(DAOException.class).when(dao).finalizarTransacao();

        fornecedorFacade.alterarFornecedor(fornecedorAlterado);

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
