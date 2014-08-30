package br.com.braveti.estoqueweb.facades;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.mockito.*;

import br.com.braveti.estoqueweb.dao.CategoriaDAO;
import br.com.braveti.estoqueweb.dao.DAOException;
import br.com.braveti.estoqueweb.dominio.Categoria;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class CategoriaFacadeTests {

    @Mock
	private CategoriaDAO dao;

	private CategoriaFacade categoriaFacade; 

	@Before
	public void antes(){

        MockitoAnnotations.initMocks(this);
		categoriaFacade = new CategoriaFacade(dao);
	}

	@Test
	public void testDeveCadastrarCategoria()throws DAOException{
		
		Categoria categoria = gerarCategoria();

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {

                Categoria categoria = (Categoria) invocationOnMock.getArguments()[0];

                categoria.setId(1L);

                return null;
            }

        }).when(dao).salvar(categoria);

        Long id = categoriaFacade.criarCategoria(categoria);

        InOrder ordem = inOrder(dao);

        ordem.verify(dao).iniciarTransacao();
        ordem.verify(dao).salvar(categoria);
        ordem.verify(dao).finalizarTransacaoEFecharConexao();

		assertNotNull(id); 
		assertEquals(categoria.getId(),id);

	}

    @Test(expected=DAOException.class)
    public void disparaExcecaoAoCadastrarCategoriaComDadosInvalidos()throws DAOException{

        Categoria categoria = gerarCategoria();
        categoria.setNome(null);

        doThrow(DAOException.class).when(dao).salvar(categoria);

        categoriaFacade.criarCategoria(categoria);

    }

    @Test(expected=DAOException.class)
    public void disparaExcecaoAoRemoverCategoriasVinculadas()throws DAOException{

        Categoria categoria = gerarCategoria();

        when(dao.buscar(Categoria.class,categoria.getId())).thenReturn(categoria);

        doThrow(DAOException.class).when(dao).remover(categoria);

        categoriaFacade.removerCategoria(categoria);

    }

    @Test
    public void deveTratarCasosDeRemocaoDeCategoriasInexistentes()throws DAOException{

        Categoria categoria = gerarCategoria();
        categoria.setId(1L);

        when(dao.buscar(Categoria.class, categoria.getId())).thenReturn(null);

        categoriaFacade.removerCategoria(categoria);

        verify(dao,never()).iniciarTransacao();
        verify(dao,never()).remover(categoria);
        verify(dao,never()).finalizarTransacao();
        verify(dao).fecharConexao();
    }
	
	@Test
	public void testDeveRemoverCategoria()throws DAOException{

        Categoria categoria = gerarCategoria();
        categoria.setId(1L);

        when(dao.buscar(Categoria.class, categoria.getId())).thenReturn(categoria);

        categoriaFacade.removerCategoria(categoria);

        InOrder ordem = inOrder(dao);

        ordem.verify(dao).buscar(Categoria.class,1L);
        ordem.verify(dao).iniciarTransacao();
        ordem.verify(dao).remover(categoria);
        ordem.verify(dao).finalizarTransacao();
        ordem.verify(dao).fecharConexao();


	}
	
	@Test
	public void testDeveAlterarCategoria()throws DAOException{

        Categoria categoriaPesquisada = mock(Categoria.class);

        Categoria categoriaAlterada = new Categoria();
        categoriaAlterada.setId(1L);
        categoriaAlterada.setDescricao("categoria de roupas");
        categoriaAlterada.setNome("Roupa");

        when(dao.buscar(Categoria.class,categoriaAlterada.getId())).thenReturn(categoriaPesquisada);

        ArgumentCaptor<String> descricao = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> nome = ArgumentCaptor.forClass(String.class);

        categoriaFacade.alterarCategoria(categoriaAlterada);

        InOrder ordem = inOrder(dao);

        verify(categoriaPesquisada).setNome(nome.capture());
        verify(categoriaPesquisada).setDescricao(descricao.capture());

        ordem.verify(dao).buscar(Categoria.class,categoriaAlterada.getId());
        ordem.verify(dao).iniciarTransacao();
        ordem.verify(dao).alterar(categoriaPesquisada);
        ordem.verify(dao).finalizarTransacao();
        ordem.verify(dao).fecharConexao();

        assertEquals(categoriaAlterada.getNome(),nome.getValue());
        assertEquals(categoriaAlterada.getDescricao(),descricao.getValue());
		
	}

    @Test(expected=DAOException.class)
    public void deveDispararExcecaoAoAlterarCategoriaComDadosInvalidos()throws DAOException{

        Categoria categoria = gerarCategoria();
        categoria.setId(1L);

        when(dao.buscar(Categoria.class, categoria.getId())).thenReturn(categoria);
        doThrow(DAOException.class).when(dao).finalizarTransacao();

        categoriaFacade.alterarCategoria(categoria);

    } 
    @Test
    public void deveFecharConexaoCasoCategoriaAlteradaNaoExista()throws DAOException{

        Categoria categoria = gerarCategoria();

        categoriaFacade.alterarCategoria(categoria);

        verify(dao,never()).iniciarTransacao();
        verify(dao,never()).alterar(categoria);
        verify(dao,never()).finalizarTransacao();
        verify(dao).fecharConexao();

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
