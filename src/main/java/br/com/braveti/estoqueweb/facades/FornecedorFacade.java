package br.com.braveti.estoqueweb.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.braveti.estoqueweb.dao.DAOException;
import br.com.braveti.estoqueweb.dao.FornecedorDAO;
import br.com.braveti.estoqueweb.dominio.Fornecedor;

@Scope("prototype")
@Component
public class FornecedorFacade {

	private FornecedorDAO fornecedorDAO; 
	
	@Autowired
	public FornecedorFacade(FornecedorDAO fornecedorDAO){
		
		this.fornecedorDAO = fornecedorDAO; 
	}
	
	public Long criarFornecedor(Fornecedor fornecedor)throws DAOException{
		
		fornecedorDAO.iniciarTransacao();
		fornecedorDAO.salvar(fornecedor);
		fornecedorDAO.finalizarTransacaoEFecharConexao();
		
		return fornecedor.getId();  
		
	}
	
	public void removerFornecedor(Fornecedor fornecedor)throws DAOException{
		
		removerFornecedor(fornecedor.getId());
	}
	
	public void removerFornecedor(Long id)throws DAOException{
		
		Fornecedor fornecedor = fornecedorDAO.buscar(Fornecedor.class, id); 
		
		if(fornecedor!=null){
			
			fornecedorDAO.iniciarTransacao();
			fornecedorDAO.remover(fornecedor);
			fornecedorDAO.finalizarTransacao(); 
			
		}
		
		fornecedorDAO.fecharConexao();
		
		
	}
	
	public void alterarFornecedor(Fornecedor fornecedor)throws DAOException{
		
		Fornecedor fornecedorPesquisado = fornecedorDAO.buscar(Fornecedor.class,fornecedor.getId());
		
		if(fornecedorPesquisado!=null){
			 
			fornecedorPesquisado.setCnpj(fornecedor.getCnpj());
			fornecedorPesquisado.setCpf(fornecedor.getCpf());
			fornecedorPesquisado.setEmail(fornecedor.getEmail());
			fornecedorPesquisado.setEndereco(fornecedor.getEndereco());
			fornecedorPesquisado.setRazaoSocial(fornecedor.getRazaoSocial());
			fornecedorPesquisado.setTelefone(fornecedor.getTelefone());
		
			fornecedorDAO.iniciarTransacao();
			fornecedorDAO.alterar(fornecedorPesquisado);
			fornecedorDAO.finalizarTransacao();
		
		}
		
		fornecedorDAO.fecharConexao();
		
		
	}
	
}
