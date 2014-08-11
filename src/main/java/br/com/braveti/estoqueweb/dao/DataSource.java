package com.inove.estoqueweb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class DataSource {

	private ConexaoBancoDeDados conexao; 
	
	public DataSource(ConexaoBancoDeDados conexao){
		
		this.conexao = conexao; 
	}
	
	public ConexaoBancoDeDados getConexao() {
		return conexao;
	}

	public void setConexao(ConexaoBancoDeDados conexao) {
		this.conexao = conexao;
	}


}
