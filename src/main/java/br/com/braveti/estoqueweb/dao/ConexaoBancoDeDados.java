package br.com.braveti.estoqueweb.dao;

import java.util.Properties;

public class ConexaoBancoDeDados {

	private Properties configuracao;
	
	public ConexaoBancoDeDados(Properties configuracao){
		
		this.configuracao = configuracao; 
	}

	public Properties getConfiguracao() {
		return configuracao;
	} 
}
