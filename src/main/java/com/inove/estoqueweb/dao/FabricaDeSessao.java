package com.inove.estoqueweb.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.inove.estoqueweb.dominio.Categoria;
import com.inove.estoqueweb.dominio.Estoque;
import com.inove.estoqueweb.dominio.Fornecedor;
import com.inove.estoqueweb.dominio.Movimentacao;
import com.inove.estoqueweb.dominio.Produto;
import com.inove.estoqueweb.dominio.TipoMovimentacao;

public class FabricaDeSessao {

	private static SessionFactory fabrica; 
	private static Properties hsqldbConfig;
	private static Properties postgresqlConfig; 
	private static Properties bancoConfig; 
	
	enum DBConfig{
		
		HSQLDB,POSTGRESQL; 
	}
	
	private static DBConfig dbConfig; 

	private FabricaDeSessao(){
		
		hsqldbConfig = new Properties(); 
		postgresqlConfig = new Properties(); 
		
		hsqldbConfig.put("hibernate.connection.driver_class","org.hsqldb.jdbcDriver");
		hsqldbConfig.put("hibernate.connection.url","jdbc:hsqldb:estoque-web;shutdown=true");
		hsqldbConfig.put("hibernate.dialect","org.hibernate.dialect.HSQLDialect");
		hsqldbConfig.put("hibernate.connection.username", "sa");
		hsqldbConfig.put("hibernate.connection.password", "");
		
		postgresqlConfig.put("hibernate.connection.driver_class","org.postgresql.Driver");
		postgresqlConfig.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/estoque-web");
		postgresqlConfig.put("hibernate.connection.driver_class","org.postgresql.Driver");
		postgresqlConfig.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		postgresqlConfig.put("hibernate.connection.username", "desenvolvimento");
		postgresqlConfig.put("hibernate.connection.password", "desenvolvimento007");
		
		dbConfig = (dbConfig==null ? DBConfig.POSTGRESQL:dbConfig);
		
	}
	
	public static void utilizarBancoDeDados(DBConfig banco){
				
			dbConfig = banco; 	
	}

	public static Session getSession(){
		
		if(fabrica==null)
			
			synchronized(FabricaDeSessao.class){
				
				if(fabrica==null){
					
					new FabricaDeSessao(); 
					
					AnnotationConfiguration config = new AnnotationConfiguration()
								.addAnnotatedClass(Categoria.class)
								.addAnnotatedClass(Estoque.class)
								.addAnnotatedClass(Fornecedor.class)
								.addAnnotatedClass(Movimentacao.class)
								.addAnnotatedClass(Produto.class)
								.addAnnotatedClass(TipoMovimentacao.class); 
					
					Properties dbProperties; 
					
					if(dbConfig==DBConfig.HSQLDB)			
						dbProperties = hsqldbConfig; 
					else
						dbProperties = postgresqlConfig; 
								
								dbProperties.put("hibernate.hbm2ddl.auto","create"); 
								config.setProperties(dbProperties);
								//.setProperty("hibernate.hbm2ddl.auto", "create")
								fabrica = config.buildSessionFactory(); 
								
								
				}
			}
		
		return fabrica.openSession(); 
		
	}
	
}
