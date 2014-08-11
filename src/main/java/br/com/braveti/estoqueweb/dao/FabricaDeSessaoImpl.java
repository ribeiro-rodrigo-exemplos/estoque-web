package br.com.braveti.estoqueweb.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.braveti.estoqueweb.dominio.Categoria;
import br.com.braveti.estoqueweb.dominio.Estoque;
import br.com.braveti.estoqueweb.dominio.Fornecedor;
import br.com.braveti.estoqueweb.dominio.Movimentacao;
import br.com.braveti.estoqueweb.dominio.Produto;
import br.com.braveti.estoqueweb.dominio.TipoMovimentacao;

@Service
public class FabricaDeSessaoImpl implements FabricaDeSessao {

	private static SessionFactory fabrica; 
	private ConexaoBancoDeDados conexao; 
	
	@Autowired
	public FabricaDeSessaoImpl(DataSource dataSource){
		
		this.conexao = dataSource.getConexao(); 
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
