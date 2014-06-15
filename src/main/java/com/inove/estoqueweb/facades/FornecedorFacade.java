package com.inove.estoqueweb.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inove.estoqueweb.dao.DAOException;
import com.inove.estoqueweb.dao.FornecedorDAO;
import com.inove.estoqueweb.dominio.Fornecedor;

@Component
public class FornecedorFacade {

	private FornecedorDAO fornecedorDAO; 
	
	@Autowired
	public FornecedorFacade(FornecedorDAO fornecedorDAO){
		
		this.fornecedorDAO = fornecedorDAO; 
	}
	
	public Long criarFornecedor(Fornecedor fornecedor)throws DAOException{
		
		fornecedorDAO.salvar(fornecedor);
		fornecedorDAO.getSession().flush();
		
		return fornecedor.getId();  
		
	}
	
	public void removerFornecedor(Fornecedor fornecedor)throws DAOException{
		
		removerFornecedor(fornecedor.getId());
	}
	
	public void removerFornecedor(Long id)throws DAOException{
		
		Fornecedor fornecedor = fornecedorDAO.buscar(Fornecedor.class, id); 
		
		if(fornecedor!=null)
			fornecedorDAO.remover(fornecedor);
		
		fornecedorDAO.getSession().flush();
		
	}
	
	public void alterarFornecedor(Fornecedor fornecedor)throws DAOException{
		
		Fornecedor fornecedorPesquisado = fornecedorDAO.buscar(Fornecedor.class,fornecedor.getId());
		
		if(fornecedorPesquisado==null)
			return; 
		
		fornecedorPesquisado.setCnpj(fornecedor.getCnpj());
		fornecedorPesquisado.setCpf(fornecedor.getCpf());
		fornecedorPesquisado.setEmail(fornecedor.getEmail());
		fornecedorPesquisado.setEndereco(fornecedor.getEndereco());
		fornecedorPesquisado.setRazaoSocial(fornecedor.getRazaoSocial());
		fornecedorPesquisado.setTelefone(fornecedor.getTelefone());
		
		fornecedorDAO.alterar(fornecedorPesquisado);
		fornecedorDAO.getSession().flush(); 
		
		
	}
	
}
