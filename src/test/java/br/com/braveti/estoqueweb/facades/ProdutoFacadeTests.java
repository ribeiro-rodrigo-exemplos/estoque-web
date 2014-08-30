package br.com.braveti.estoqueweb.facades;

import br.com.braveti.estoqueweb.dao.*;
import br.com.braveti.estoqueweb.dominio.*;
import org.junit.Before;
import org.junit.Test;

import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.inove.estoqueweb.testdatabuilders.ProdutoDataBuilder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ProdutoFacadeTests {

    @Mock
	private ProdutoDAO produtoDAO;
    @Mock
	private CategoriaDAO categoriaDAO;
    @Mock
	private EstoqueDAO estoqueDAO;
    @Mock
	private FornecedorDAO fornecedorDAO;
    @Mock
	private MovimentacaoDAO movimentacaoDAO; 
	private ProdutoFacade facade; 
	private ProdutoDataBuilder produtoBuilder; 

	@Before
	public void antes(){
		
		MockitoAnnotations.initMocks(this);
		facade = new ProdutoFacade(produtoDAO,categoriaDAO,estoqueDAO,fornecedorDAO,movimentacaoDAO);
		produtoBuilder = new ProdutoDataBuilder(); 
	}
	

	@Test
	public void deveSalvarProduto()throws DAOException{
		
		Produto produto = produtoBuilder.daCategoria("roupas")
                .fornecidoPor("brave it solutions")
                .noEstoque("estoque01")
                .nome("camisa nike")
                .criar();

        Categoria categoriaPesquisada = new Categoria();
        Estoque estoquePesquisado = new Estoque();
        Fornecedor fornecedorPesquisado = new Fornecedor();

        when(categoriaDAO.buscar(Categoria.class,produto.getCategoria().getId())).thenReturn(categoriaPesquisada);
        when(estoqueDAO.buscar(Estoque.class,produto.getEstoqueAtual().getId())).thenReturn(estoquePesquisado);
        when(fornecedorDAO.buscar(Fornecedor.class,produto.getFornecedor().getId())).thenReturn(fornecedorPesquisado);

        doAnswer(new Answer<Void>(){

            public Void answer(InvocationOnMock invocationOnMock){

                Produto produto = (Produto) invocationOnMock.getArguments()[0];
                produto.setId(1L);

                return null;

            }

        }).when(produtoDAO).salvar(produto);

        Long id = facade.criarProduto(produto);

        InOrder ordem = inOrder(produtoDAO);

        ordem.verify(produtoDAO).iniciarTransacao();
        ordem.verify(produtoDAO).salvar(produto);
        ordem.verify(produtoDAO).finalizarTransacaoEFecharConexao();

        verify(categoriaDAO).fecharConexao();
        verify(fornecedorDAO).fecharConexao();

        assertEquals(id, produto.getId());
        assertEquals(categoriaPesquisada,produto.getCategoria());
        assertEquals(estoquePesquisado,produto.getEstoqueAtual());
        assertEquals(fornecedorPesquisado,produto.getFornecedor());
		
	}

    @Test(expected=DAOException.class)
    public void disparoDeExcecaoAoCadastrarProduto()throws DAOException{

        Produto produto = produtoBuilder.daCategoria("bermuda")
                .fornecidoPor("hbs")
                .noEstoque("estoque11")
                .nome("bermuda hbs azul")
                .criar();

        when(categoriaDAO.buscar(Categoria.class,produto.getCategoria().getId())).thenReturn(new Categoria());
        // Ao retornar estoque nulo, será disparada uma exceção ao salvar o objeto produto, pois a propriedade é obrigatoria
        when(estoqueDAO.buscar(Estoque.class,produto.getEstoqueAtual().getId())).thenReturn(null);
        when(fornecedorDAO.buscar(Fornecedor.class,produto.getFornecedor().getId())).thenReturn(new Fornecedor());

        doThrow(DAOException.class).when(produtoDAO).salvar(produto);

        facade.criarProduto(produto);
    }
	
	@Test
	public void deveRemoverProduto()throws DAOException{

        Produto produtoPesquisado = produtoBuilder.daCategoria("bermuda")
                .fornecidoPor("hbs")
                .noEstoque("estoque11")
                .nome("bermuda hbs azul")
                .criar();

        Produto produtoRemovido = new Produto();
        produtoRemovido.setId(1L);

        when(produtoDAO.buscar(Produto.class,produtoRemovido.getId())).thenReturn(produtoPesquisado);

        facade.removerProduto(produtoRemovido);

        InOrder ordem = inOrder(produtoDAO);

        ordem.verify(produtoDAO).iniciarTransacao();
        ordem.verify(produtoDAO).remover(produtoPesquisado);
        ordem.verify(produtoDAO).finalizarTransacao();
        ordem.verify(produtoDAO).fecharConexao();
	}

    @Test
    public void testRemocaoDeProdutoInexistente()throws DAOException{

        Produto produto = new Produto();
        produto.setId(1L);

        when(produtoDAO.buscar(Produto.class,produto.getId())).thenReturn(null);

        facade.removerProduto(produto);

        verify(produtoDAO, never()).iniciarTransacao();
        verify(produtoDAO,never()).remover(produto);
        verify(produtoDAO,never()).finalizarTransacao();
        verify(produtoDAO).fecharConexao();
    }

    @Test(expected=DAOException.class)
    public void disparoDeExcecaoNaRemocaoDoProduto()throws DAOException{

        Produto produto = new Produto();
        produto.setId(1L);

        when(produtoDAO.buscar(Produto.class,produto.getId())).thenReturn(new Produto());

        doThrow(DAOException.class).when(produtoDAO).finalizarTransacao();

        facade.removerProduto(produto);
    }
	
	@Test
	public void deveAlterarProduto()throws DAOException{

        Produto produtoPesquisado = mock(Produto.class);
        Categoria categoriaPesquisada = new Categoria();
        Fornecedor fornecedorPesquisado = new Fornecedor();
        Estoque estoquePesquisado = new Estoque();

        Produto produtoAlterado = produtoBuilder.daCategoria("bermuda")
                .fornecidoPor("hbs")
                .noEstoque("estoque11")
                .nome("bermuda hbs azul")
                .criar();

        when(produtoDAO.buscar(Produto.class,produtoAlterado.getId())).thenReturn(produtoPesquisado);
        when(categoriaDAO.buscar(Categoria.class,produtoAlterado.getCategoria().getId())).thenReturn(categoriaPesquisada);
        when(fornecedorDAO.buscar(Fornecedor.class,produtoAlterado.getFornecedor().getId())).thenReturn(fornecedorPesquisado);
        when(estoqueDAO.buscar(Estoque.class,produtoAlterado.getEstoqueAtual().getId())).thenReturn(estoquePesquisado);

        facade.alterarProduto(produtoAlterado);

        ArgumentCaptor<Categoria> categoria = ArgumentCaptor.forClass(Categoria.class);
        ArgumentCaptor<String> descricao = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Estoque> estoque = ArgumentCaptor.forClass(Estoque.class);
        ArgumentCaptor<Fornecedor> fornecedor = ArgumentCaptor.forClass(Fornecedor.class);
        ArgumentCaptor<String> nome = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> quantidadeMinima = ArgumentCaptor.forClass(Integer.class);

        verify(produtoPesquisado).setCategoria(categoria.capture());
        verify(produtoPesquisado).setDescricao(descricao.capture());
        verify(produtoPesquisado).setEstoqueAtual(estoque.capture());
        verify(produtoPesquisado).setFornecedor(fornecedor.capture());
        verify(produtoPesquisado).setNome(nome.capture());
        verify(produtoPesquisado).setQuantidadeMinima(quantidadeMinima.capture());

        InOrder ordem = inOrder(produtoDAO);

        ordem.verify(produtoDAO).iniciarTransacao();
        ordem.verify(produtoDAO).alterar(produtoPesquisado);
        ordem.verify(produtoDAO).finalizarTransacao();
        ordem.verify(produtoDAO).fecharConexao();

        verify(categoriaDAO).fecharConexao();
        verify(fornecedorDAO).fecharConexao();
        verify(produtoDAO).fecharConexao();
        verify(estoqueDAO).fecharConexao();

        assertEquals(categoriaPesquisada,categoria.getValue());
        assertEquals(produtoAlterado.getDescricao(),descricao.getValue());
        assertEquals(estoquePesquisado,estoque.getValue());
        assertEquals(fornecedorPesquisado,fornecedor.getValue());
        assertEquals(produtoAlterado.getNome(),nome.getValue());
        assertEquals(produtoAlterado.getQuantidadeMinima(),quantidadeMinima.getValue());
		
	}

    @Test
    public void testAlteracaoDeProdutoInexistente()throws DAOException{

        Produto produto = produtoBuilder.daCategoria("bermuda")
                .fornecidoPor("hbs")
                .noEstoque("estoque11")
                .nome("bermuda hbs azul")
                .criar();

        when(produtoDAO.buscar(Produto.class,produto.getId())).thenReturn(null);
        when(categoriaDAO.buscar(Categoria.class,produto.getCategoria().getId())).thenReturn(new Categoria());
        when(fornecedorDAO.buscar(Fornecedor.class,produto.getFornecedor().getId())).thenReturn(new Fornecedor());
        when(estoqueDAO.buscar(Estoque.class,produto.getEstoqueAtual().getId())).thenReturn(new Estoque());

        facade.alterarProduto(produto);

        verify(produtoDAO,never()).iniciarTransacao();
        verify(produtoDAO,never()).alterar(any(Produto.class));
        verify(produtoDAO,never()).finalizarTransacao();

        verify(categoriaDAO).fecharConexao();
        verify(produtoDAO).fecharConexao();
        verify(fornecedorDAO).fecharConexao();
        verify(estoqueDAO).fecharConexao();

    }

    @Test(expected=DAOException.class)
    public void disparoDeExcecaoAoAlterarProduto()throws DAOException{

        Produto produto = produtoBuilder.daCategoria("bermuda")
                .fornecidoPor("hbs")
                .noEstoque("estoque11")
                .nome("bermuda hbs azul")
                .criar();

        when(produtoDAO.buscar(Produto.class,produto.getId())).thenReturn(new Produto());
        when(categoriaDAO.buscar(Categoria.class,produto.getCategoria().getId())).thenReturn(new Categoria());
        when(fornecedorDAO.buscar(Fornecedor.class,produto.getFornecedor().getId())).thenReturn(new Fornecedor());
        when(estoqueDAO.buscar(Estoque.class,produto.getEstoqueAtual().getId())).thenReturn(new Estoque());

        doThrow(DAOException.class).when(produtoDAO).finalizarTransacao();

        facade.alterarProduto(produto);


    }

    @Test
    public void deveRegistrarEntradaDeProduto()throws DAOException{


        Produto produto = produtoBuilder.daCategoria("bermuda")
                .fornecidoPor("hbs")
                .noEstoque("estoque11")
                .nome("bermuda hbs azul")
                .criar();

        produto.setId(1L);

        produto.setQuantidadeAtual(10);

        when(produtoDAO.buscar(Produto.class, produto.getId())).thenReturn(produto);

        facade.registrarEntrada(produto.getId(),5);

        InOrder ordem = inOrder(produtoDAO,movimentacaoDAO);

        ordem.verify(produtoDAO).iniciarTransacao();
        ordem.verify(produtoDAO).salvar(produto);
        ordem.verify(produtoDAO).finalizarTransacao();
        ordem.verify(produtoDAO).fecharConexao();
        ordem.verify(movimentacaoDAO).iniciarTransacao();
        ordem.verify(movimentacaoDAO).salvar(any(Movimentacao.class));
        ordem.verify(movimentacaoDAO).finalizarTransacaoEFecharConexao();

        assertEquals(15,produto.getQuantidadeAtual());

    }

    @Test
    public void testRegistrarEntradaDeProdutoInexistente()throws DAOException{

        Produto produto = produtoBuilder.daCategoria("bermuda")
                .fornecidoPor("hbs")
                .noEstoque("estoque11")
                .nome("bermuda hbs azul")
                .criar();

        produto.setId(1L);

        produto.setQuantidadeAtual(10);

        when(produtoDAO.buscar(Produto.class, produto.getId())).thenReturn(null);

        facade.registrarEntrada(produto.getId(),5);

        verify(produtoDAO,never()).iniciarTransacao();
        verify(produtoDAO, never()).salvar(produto);
        verify(produtoDAO, never()).finalizarTransacao();
        verify(produtoDAO).fecharConexao();
        verify(movimentacaoDAO, never()).iniciarTransacao();
        verify(movimentacaoDAO, never()).salvar(any(Movimentacao.class));
        verify(movimentacaoDAO, never()).finalizarTransacaoEFecharConexao();
        

    }

    @Test(expected=DAOException.class)
    public void testDisparoExcecaoNoRegistroDeEntrada()throws DAOException{

        when(produtoDAO.buscar(Produto.class,1L)).thenReturn(new Produto());

        doThrow(DAOException.class).when(produtoDAO).finalizarTransacao();

        facade.registrarEntrada(1L,10);
    }

    @Test
    public void deveRegistrarSaidaDeProduto()throws DAOException{

        Produto produto = produtoBuilder.daCategoria("bermuda")
                .fornecidoPor("hbs")
                .noEstoque("estoque11")
                .nome("bermuda hbs azul")
                .criar();

        produto.setId(1L);
        produto.setQuantidadeAtual(5);

        when(produtoDAO.buscar(Produto.class, produto.getId())).thenReturn(produto);

        facade.registrarSaida(produto.getId(), 2);

        InOrder ordem = inOrder(produtoDAO);

        ordem.verify(produtoDAO).iniciarTransacao();
        ordem.verify(produtoDAO).salvar(produto);
        ordem.verify(produtoDAO).finalizarTransacao();
        ordem.verify(produtoDAO).fecharConexao();

        assertEquals(3,produto.getQuantidadeAtual());
    }

    @Test(expected=IllegalStateException.class)
    public void naoDeveRegistrarSaidaQuandoQuantidadeResultarEmValorNegativo()throws DAOException,IllegalStateException{


        Produto produto = produtoBuilder.daCategoria("bermuda")
                .fornecidoPor("hbs")
                .noEstoque("estoque11")
                .nome("bermuda hbs azul")
                .criar();

        produto.setId(1L);
        produto.setQuantidadeAtual(5);

        when(produtoDAO.buscar(Produto.class, produto.getId())).thenReturn(produto);

        facade.registrarSaida(produto.getId(),6);
    }

    @Test
    public void naoDeveRegistrarSaidaProdutoInexistente()throws DAOException{


        Produto produto = produtoBuilder.daCategoria("bermuda")
                .fornecidoPor("hbs")
                .noEstoque("estoque11")
                .nome("bermuda hbs azul")
                .criar();

        produto.setId(1L);
        produto.setQuantidadeAtual(5);

        when(produtoDAO.buscar(Produto.class, produto.getId())).thenReturn(null);

        facade.registrarSaida(produto.getId(),2);

        verify(produtoDAO, never()).iniciarTransacao();
        verify(produtoDAO, never()).salvar(produto);
        verify(produtoDAO, never()).finalizarTransacao();
        verify(produtoDAO).fecharConexao();
    }

    @Test(expected=DAOException.class)
    public void testDisparoDeExcecaoNaSaidaDoProduto()throws DAOException{

        Produto produto = produtoBuilder.daCategoria("bermuda")
                .fornecidoPor("hbs")
                .noEstoque("estoque11")
                .nome("bermuda hbs azul")
                .criar();

        produto.setId(1L);
        produto.setQuantidadeAtual(5);

        when(produtoDAO.buscar(Produto.class, produto.getId())).thenReturn(produto);

        doThrow(DAOException.class).when(produtoDAO).finalizarTransacao();

        facade.registrarSaida(produto.getId(),2);
    }


}
