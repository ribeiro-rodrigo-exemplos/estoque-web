package com.inove.estoqueweb.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.inove.estoqueweb.dominio.Categoria;
import com.inove.estoqueweb.dominio.Estoque;
import com.inove.estoqueweb.dominio.Fornecedor;
import com.inove.estoqueweb.dominio.Movimentacao;
import com.inove.estoqueweb.dominio.Produto;
import com.inove.estoqueweb.dominio.TipoMovimentacao;

@Service
public class FabricaDeSessaoImpl implements FabricaDeSessao {

	private static SessionFactory fabrica; 
	private ConexaoBancoDeDados conexao; 
	
	@Autowired
	public FabricaDeSessaoImpl(@Qualifier("producaoConexao")ConexaoBancoDeDados conexao){
		
		this.conexao = conexao; 
		conexao.getConfiguracao().setProperty("hibernate.hbm2ddl.auto","create"); 
		
	}
	

	public Session getSession(){
		
		if(fabrica==null)
			
			synchronized(FabricaDeSessaoImpl.class){
				
				if(fabrica==null){
										
					AnnotationConfiguration config = new AnnotationConfiguration()
								.addAnnotatedClass(Categoria.class)
								.addAnnotatedClass(Estoque.class)
								.addAnnotatedClass(Fornecedor.class)
								.addAnnotatedClass(Movimentacao.class)
								.addAnnotatedClass(Produto.class)
								.addAnnotatedClass(TipoMovimentacao.class); 
					
								config.setProperties(conexao.getConfiguracao()); 

								fabrica = config.buildSessionFactory(); 						
								
				}
			}
		
		return fabrica.openSession(); 
		
	}
	
}
