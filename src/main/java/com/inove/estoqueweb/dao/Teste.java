package com.inove.estoqueweb.dao;

import org.hibernate.Session;

import com.inove.estoqueweb.dominio.Categoria;



public class Teste {

	public static void main(String[] args)
	{
		FabricaDeSessao.utilizarBancoDeDados(FabricaDeSessao.DBConfig.HSQLDB); 
		Session session = FabricaDeSessao.getSession();
		
		session.beginTransaction();
		
		Categoria c = new Categoria(); 
		
		c.setNome("aa");
		
		session.persist(c); 
		
		session.flush();
		
		session.getTransaction().commit();
		
		Categoria cb = (Categoria) session.get(Categoria.class,1);
		
		System.out.println(cb.getNome()); 
	
		
	}
}
