package br.com.braveti.estoqueweb.facades;


import org.junit.Before;

import static org.junit.Assert.*; 

import org.junit.Test;
import static org.mockito.Mockito.*;
import org.mockito.*;
import org.mockito.MockitoAnnotations;

import br.com.braveti.estoqueweb.dao.DAOException;
import br.com.braveti.estoqueweb.dao.EstoqueDAO;
import br.com.braveti.estoqueweb.dominio.Estoque;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class EstoqueFacadeTest {

	private EstoqueFacade facade;

    @Mock
	private EstoqueDAO dao; 

	@Before
	public void antes(){
		
        MockitoAnnotations.initMocks(this);
		facade = new EstoqueFacade(dao);
	}
	

	@Test
	public void deveCadastrarEstoque()throws DAOException{

        Estoque estoque = gerarEstoque();

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {

                Estoque estoque = (Estoque) invocationOnMock.getArguments()[0];
                estoque.setId(1L);
                return null;
            }

        }).when(dao).salvar(estoque);

		Long id = facade.criarEstoque(estoque);

        InOrder ordem = inOrder(dao);

        ordem.verify(dao).iniciarTransacao();
        ordem.verify(dao).salvar(estoque);
        ordem.verify(dao).finalizarTransacaoEFecharConexao();
		
		assertNotNull(id); 
		assertEquals(1L,id);

	}

    @Test(expected=DAOException.class)
    public void testDisparoDeExcecaoNoCadastroDoEstoque()throws DAOException{

        Estoque estoque = gerarEstoque();

        doThrow(DAOException.class).when(dao).salvar(estoque);

        facade.criarEstoque(estoque);

    }
	
	@Test
	public void deveRemoverEstoque()throws DAOException{
		
		Estoque estoquePesquisado = gerarEstoque();
        Estoque estoqueRemovido = gerarEstoque();

        estoqueRemovido.setId(1L);

        when(dao.buscar(Estoque.class,estoqueRemovido.getId())).thenReturn(estoquePesquisado);

        facade.removerEstoque(estoqueRemovido);

        InOrder ordem = inOrder(dao);

        ordem.verify(dao).buscar(Estoque.class,estoqueRemovido.getId());
        ordem.verify(dao).iniciarTransacao();
        ordem.verify(dao).remover(estoquePesquisado);
        ordem.verify(dao).finalizarTransacao();
        ordem.verify(dao).fecharConexao();

	}

    @Test(expected=DAOException.class)
    public void testDisparoDeExcecaoNaRemocaoDoEstoque()throws DAOException{

        Estoque estoque = gerarEstoque();
        estoque.setId(1L);

        when(dao.buscar(Estoque.class,estoque.getId())).thenReturn(estoque);

        doThrow(DAOException.class).when(dao).finalizarTransacao();

        facade.removerEstoque(estoque);

    }
	
	@Test
	public void deveAlterarEstoque()throws DAOException{

        Estoque estoqueAlterado = gerarEstoque();
        Estoque estoquePesquisado = mock(Estoque.class);

        estoqueAlterado.setId(1L);

        when(dao.buscar(Estoque.class,estoqueAlterado.getId())).thenReturn(estoquePesquisado);

        facade.alterarEstoque(estoqueAlterado);

        ArgumentCaptor<String> nome = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> descricao = ArgumentCaptor.forClass(String.class);

        verify(estoquePesquisado).setNome(nome.capture());
        verify(estoquePesquisado).setDescricao(descricao.capture());

        InOrder ordem = inOrder(dao);

        ordem.verify(dao).iniciarTransacao();
        ordem.verify(dao).alterar(estoquePesquisado);
        ordem.verify(dao).finalizarTransacao();
        ordem.verify(dao).fecharConexao();

        assertEquals(estoqueAlterado.getNome(),nome.getValue());
        assertEquals(estoqueAlterado.getDescricao(),descricao.getValue());

		
	}

    @Test(expected=DAOException.class)
    public void testDisparoDeExcecaoNaAlteracaoDoEstoque()throws DAOException{

        Estoque estoquePesquisado = gerarEstoque();
        Estoque estoqueAlterado = gerarEstoque();
        estoqueAlterado.setId(1L);

        when(dao.buscar(Estoque.class,estoqueAlterado.getId())).thenReturn(estoquePesquisado);

        doThrow(DAOException.class).when(dao).finalizarTransacao();

        facade.removerEstoque(estoqueAlterado);


    }

    @Test
    public void testRemocaoDeEstoqueInexistente()throws DAOException{

        Estoque estoque = gerarEstoque();
        estoque.setId(1L);

        when(dao.buscar(Estoque.class,estoque.getId())).thenReturn(null);

        facade.removerEstoque(estoque);

        verify(dao,never()).iniciarTransacao();
        verify(dao,never()).remover(estoque);
        verify(dao,never()).finalizarTransacao();
        verify(dao).fecharConexao();
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
